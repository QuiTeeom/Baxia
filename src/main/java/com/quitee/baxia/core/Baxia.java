package com.quitee.baxia.core;

import com.quitee.baxia.scan.BxScaner;

import java.util.List;

public class Baxia {
    private static final SpaceGroupImp spaceGroup = new SpaceGroupImp();

    public static void scan(String scanPattern) {
        new BxScaner(aClass -> {
            if (aClass.getAnnotation(com.quitee.baxia.annotations.Space.class) != null){
                spaceGroup.createSpace(aClass, (com.quitee.baxia.annotations.Space) aClass.getAnnotation(com.quitee.baxia.annotations.Space.class));
            }

        }).scan(scanPattern);
    }

    public static synchronized <T> T get(Class<T> tClass, String where, Object value, Object... parameters) {
        return spaceGroup.get(tClass,where,value , parameters);
    }

    public static synchronized <T> List<T> getList(Class<T> tClass, String where, Object value,Object... parameters) {
        return spaceGroup.getList(tClass,where,value, parameters);
    }

    public static synchronized <T> List<T> getList(Class<T> tClass, String where, List<Object> values, Object... parameters) {
        return spaceGroup.getList(tClass,where,values,parameters);
    }

    public static synchronized <T> List<T> getList(Class<T> tClass) {
        return spaceGroup.getList(tClass);
    }

    public static synchronized void add(Object o) {
        spaceGroup.add(o);
    }

    public static synchronized void remove(Object o) {
        spaceGroup.remove(o);
    }

    private static ThreadLocal<Transaction> transactionThreadLocal = new ThreadLocal<>();

    public static Transaction createTransaction(){
        Transaction transaction = new Transaction();
        transactionThreadLocal.set(transaction);
        return transaction;
    }

    public static void destroyTransaction(){
        Transaction transaction = transactionThreadLocal.get();
        if(transaction!=null)
            transactionThreadLocal.remove();
    }

    public static Transaction getCurrentTransaction(){
        return transactionThreadLocal.get();
    }

    public static Space copySpace(Class clazz){
        Space space = spaceGroup.getSpace(clazz);
        return space.copy();
    }
}
