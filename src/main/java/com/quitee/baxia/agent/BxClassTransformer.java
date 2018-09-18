package com.quitee.baxia.agent;

import com.quitee.baxia.annotations.Fetch;
import com.quitee.baxia.annotations.Space;
import com.quitee.baxia.core.BaxiaStringFormatter;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;

import java.io.ByteArrayInputStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

public class BxClassTransformer implements ClassFileTransformer {

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        try {
            ClassPool classPool = ClassPool.getDefault();
            CtClass ctClass = classPool.makeClass(new ByteArrayInputStream(classfileBuffer));
            Space space = (Space) ctClass.getAnnotation(Space.class);
            if (space==null) {
                return classfileBuffer;
            }

            System.out.println("find Baxia Space:"+ctClass.getName());
            CtMethod[] methods = ctClass.getMethods();
            for(CtMethod method:methods){
                Fetch fetch = (Fetch) method.getAnnotation(Fetch.class);
                if(fetch!=null){
                    resetFetchMethodBody(method,fetch);
                }
            }
            ctClass.writeFile("C:\\Users\\QuiTee\\Desktop\\param");
            return ctClass.toBytecode();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return classfileBuffer;
    }

    private void resetFetchMethodBody(CtMethod ctMethod,Fetch fetch){
        try {
            String returnType = getReturnType(ctMethod);
            boolean isList = false;
            if(returnType.startsWith("list ")){
                isList = true;
                returnType = returnType.substring("list ".length());
            }

            String java = new BaxiaStringFormatter(returnType).getList(isList).setField(fetch.targetField(),fetch.targetFieldValue()).build();
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
