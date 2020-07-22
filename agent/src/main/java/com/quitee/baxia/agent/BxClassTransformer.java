package com.quitee.baxia.agent;

import com.quitee.baxia.core.annotations.Fetch;
import com.quitee.baxia.core.annotations.Space;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;
import java.security.ProtectionDomain;
import java.util.Arrays;
import java.util.stream.Stream;

public class BxClassTransformer implements ClassFileTransformer {
    Logger logger = LoggerFactory.getLogger(BxClassTransformer.class);

    public void transform(Instrumentation instrumentation){
        Class[] classes = instrumentation.getAllLoadedClasses();

        Arrays.stream(classes).forEach(clazz->{
            try {
                Space space = (Space) clazz.getDeclaredAnnotation(Space.class);
                if (space==null) {
                    return;
                }
                instrumentation.retransformClasses(clazz);

            } catch (UnmodifiableClassException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        try {
            //判断是否是需要修改的class
            ClassPool classPool = ClassPool.getDefault();
            CtClass ctClass = classPool.makeClass(new ByteArrayInputStream(classfileBuffer));
            Space space = (Space) ctClass.getAnnotation(Space.class);
            if (space==null) {
                return classfileBuffer;
            }
            if (logger.isDebugEnabled()){
                logger.debug("find Baxia space:{}",ctClass.getName());
            }

            findMethod(ctClass).forEach(this::resetFetchMethodBody);
//            ctClass.writeFile("C:\\Users\\QuiTee\\Desktop\\param");
            return ctClass.toBytecode();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return classfileBuffer;
    }


    /**
     * 查询类中是否含有满足的方法
     * @param ctClass
     * @return
     */
    private Stream<FetchMethod> findMethod(CtClass ctClass){
        CtMethod[] methods = ctClass.getDeclaredMethods();
        return Arrays.stream(methods).filter(ctMethod -> {
            try {
                return ctMethod.getAnnotation(Fetch.class)!=null;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                return false;
            }
        }).map(ctMethod -> {
            try {
                return new FetchMethod((Fetch) ctMethod.getAnnotation(Fetch.class),ctMethod);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                return null;
            }
        });
    }


    private void resetFetchMethodBody(FetchMethod fetchMethod){
        CtMethod ctMethod = fetchMethod.getCtMethod();
        Fetch fetch = fetchMethod.getFetch();
        try {
            String returnType = getReturnType(ctMethod);
            boolean isList = false;
            if(returnType.startsWith("list ")){
                isList = true;
                returnType = returnType.substring("list ".length());
            }

            String java = new BxStringFormatter(returnType).getList(isList).setField(fetch.targetField(),fetch.targetFieldValue()).build();
            ctMethod.setBody(java);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getReturnType(CtMethod ctMethod) throws NotFoundException {
        String description = ctMethod.getGenericSignature();
        if(description==null){
            return ctMethod.getReturnType().getName();
        }
        String returnDescription = description.substring(description.lastIndexOf(")")+1).replaceAll("/","\\.").replaceAll(";","").replaceAll("^L","");
        if(returnDescription.startsWith("java.util.List")){
            returnDescription = returnDescription.substring("java.util.List<".length()).replaceAll(">$","").replaceAll("^L","");
            return "list "+returnDescription;
        }else {
            return null;
        }
    }

}
