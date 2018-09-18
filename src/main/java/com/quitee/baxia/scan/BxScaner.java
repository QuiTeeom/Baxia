package com.quitee.baxia.scan;

import java.io.File;
import java.util.function.Consumer;

public class BxScaner {
    Consumer<Class> callBack;
    String classRoot = BxScaner.class.getClassLoader().getResource("").getPath();
    public BxScaner(Consumer<Class> callBack) {
        this.callBack = callBack;
    }

    //com.quitee.a,com.quitee.b
    public void scan(String pattern){
        String[] packages = pattern.split(",");
        if(packages.length>1)
            for(String packageName:packages){
                scan(packageName);
            }
        else
            scanPackage(pattern);
    }

    private void scanPackage(String packageName){
        String pathName = packageName.replaceAll("\\.","/");
        System.out.println(classRoot+pathName);
        File file = new File(classRoot+pathName);
        if(file.exists()){
            scanFile(file);
        }
    }

    private void scanFile(File file){
        if(file.isDirectory()){
            for(File childFile:file.listFiles()){
                scanFile(childFile);
            }
        }else if(file.getName().endsWith(".class")){
            dealClassFile(file);
        }
    }

    private void dealClassFile(File file){
        String path = file.getPath();
        String classPath = null;
        if (!path.startsWith("/"))
            classPath = path.substring(classRoot.length()-1);
        else
            classPath = path.substring(classRoot.length());
        String className = classPath.replaceAll("\\\\","\\.").replaceAll("/","\\.").substring(0,classPath.indexOf(".class"));
        try {
            Class aClass = BxScaner.class.getClassLoader().loadClass(className);
            callBack.accept(aClass);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }






}
