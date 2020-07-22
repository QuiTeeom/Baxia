package com.quitee.baxia.core;

import com.quitee.baxia.core.exceptions.NoSuchSpace;
import com.quitee.baxia.core.exceptions.NotASpaceClass;
import com.quitee.baxia.core.exceptions.NotExtendsFromRefClass;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpaceGroupImp implements SpaceGroup{
    private Map<Class, Space> spaceMap = new HashMap<>();

    public synchronized Space createSpace(Class aClass,com.quitee.baxia.core.annotations.Space spaceA) {
        if (!spaceMap.containsKey(aClass)) {
            Class refClass = spaceA.ref();
            if(refClass.equals(com.quitee.baxia.core.annotations.Space.NullClass.class)){
                Space space = new SpaceImp(aClass);
                spaceMap.put(aClass, space);
            }else {
                if (refClass.isAssignableFrom(aClass)){
                    if (refClass.getAnnotation(com.quitee.baxia.core.annotations.Space.class) != null){
                        createSpace(refClass, (com.quitee.baxia.core.annotations.Space) refClass.getAnnotation(com.quitee.baxia.core.annotations.Space.class));
                        spaceMap.put(aClass,getSpace(refClass));
                    }else {
                        throw new NotASpaceClass(refClass);
                    }
                }else {
                    throw new NotExtendsFromRefClass(aClass,refClass);
                }
            }
        }
        return (spaceMap.get(aClass));
    }

    @Override
    public synchronized <T> T get(Class<T> tClass, String where, Object value, Object... parameters) {
        Space<T> space = getSpace(tClass);
        return space.get(value, where, parameters);
    }

    @Override
    public synchronized <T> List<T> getList(Class<T> tClass, String where, Object value, Object... parameters) {
        Space<T> space = getSpace(tClass);
        return space.getList(value, where, parameters);
    }

    @Override
    public synchronized <T> List<T> getList(Class<T> tClass, String where, List<Object> values, Object... parameters) {
        Space<T> space = getSpace(tClass);
        return space.getList(values, where, parameters);
    }

    @Override
    public synchronized <T> List<T> getList(Class<T> tClass) {
        Space<T> space = getSpace(tClass);
        return space.getList();
    }

    @Override
    public synchronized void add(Object o) {
        getSpace(o.getClass()).add(o);
    }

    @Override
    public synchronized void remove(Object o) {
        getSpace(o.getClass()).remove(o);
    }

    public synchronized Space getSpace(Class tClass) {
        Space space = spaceMap.get(tClass);
        if (space != null) {
            return space;
        } else
            throw new NoSuchSpace(tClass);
    }
}
