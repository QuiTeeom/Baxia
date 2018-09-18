package com.quitee.baxia.core;

import java.io.*;

public class BaxiaClassLoader extends ClassLoader {


    public Class baxiaDefineClass(String name,byte[] bytes,int off,int length){
        return defineClass(name,bytes,0,length);
    }

    @Override
    public Class<?> findClass(String name) throws ClassNotFoundException {

        Class loadedClass = findLoadedClass(name);
        if(loadedClass!=null) {
            System.out.println(loadedClass.getClassLoader().getClass().getName());
            return loadedClass;
        }

        String path = this.getClass().getClassLoader().getResource("").getPath();
        File file = new File(path+name.replaceAll("\\.","/")+".class");

        InputStream in = null;
        //使用byteArrayOutputStream保存类文件。然后转化为byte数组
        ByteArrayOutputStream out = null;
        try {
            in = new FileInputStream(file);
            out = new ByteArrayOutputStream();
            int i = 0;
            while ( (i = in.read()) != -1){
                out.write(i);
            }
        }catch (Exception e){

        }finally {
            try {
                out.close();
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        byte[] bytes = out.toByteArray();

        Class c = defineClass(name,bytes,0,bytes.length);
        return c;
    }

}
