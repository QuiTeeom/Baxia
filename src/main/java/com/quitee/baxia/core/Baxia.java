package com.quitee.baxia.core;

import com.quitee.baxia.exceptions.NoSuchSpace;
import com.quitee.baxia.scan.BxScaner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Baxia {
    public static void scan(String scanPattern) {
        new BxScaner(aClass -> {
            if (aClass.getAnnotation(com.quitee.baxia.annotations.Space.class) != null)
                createSpace(aClass);
        }).scan(scanPattern);
    }

    private static Map<Class, Space> spaceMap = new HashMap<>();

    private static synchronized Space createSpace(Class aClass) {
        if (!spaceMap.containsKey(aClass)) {
            Space space = new SpaceImp(aClass);
            spaceMap.put(aClass, space);
        }
        return (spaceMap.get(aClass));
    }

    //
//    public static synchronized <T> T get(Class<T> tClass, String where, Object value,Object... parameters){
//        Space<T> space = getSpace(tClass.getName());
//        return  space.get(value,where,parameters);
//    }
//
//    public static synchronized <T> List<T> getList(Class<T> tClass) {
//        List<T> res;
//        ClassInfo classInfo = getClassInfo(tClass);
//        Space<T> space = classInfo.getSpace();
//        res = space.getList();
//        return res;
//    }
//

//    public static synchronized <T> T get(Class<T> tClass, String where, Integer value) {
//        return null;
//    }

    public static synchronized <T> T get(Class<T> tClass, String where, Object value, Object... parameters) {
        Space<T> space = getSpace(tClass);
        return space.get(value, where, parameters);
    }

    public static synchronized <T> List<T> getList(Class<T> tClass, String where, Object value,Object... parameters) {
        Space<T> space = getSpace(tClass);
        return space.getList(value, where, parameters);
    }

    public static synchronized <T> List<T> getList(Class<T> tClass, String where, List<Object> values, Object... parameters) {
        Space<T> space = getSpace(tClass);
        return space.getList(values, where, parameters);
    }

    public static synchronized void add(Object o) {
        getSpace(o.getClass()).add(o);
    }

    public static synchronized void remove(Object o) {
        getSpace(o.getClass()).remove(o);
    }

    private static synchronized Space getSpace(Class tClass) {
        Space space = spaceMap.get(tClass);
        if (space != null) {
            return space;
        } else
            throw new NoSuchSpace(tClass);
    }

}
