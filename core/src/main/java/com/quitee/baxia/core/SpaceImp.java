package com.quitee.baxia.core;

import com.quitee.baxia.core.exceptions.DuplicateField;
import com.quitee.baxia.core.exceptions.NoSuchField;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpaceImp<T> implements Space<T>{
    Class<T> tClass;
    List<T> ts = new ArrayList<>();
    Map<String,ValueGetter> valueGetterMap = new HashMap<>();

    public SpaceImp(Class tClass) {
        this.tClass = tClass;
        Class clazz = tClass;
        while (clazz!=null){
            Field fields[] = clazz.getDeclaredFields();
            for(Field field:fields){
                com.quitee.baxia.core.annotations.Field anno = field.getAnnotation(com.quitee.baxia.core.annotations.Field.class);
                if(anno!=null){
                    String fieldName = field.getName();
                    String value = anno.value();
                    if(value!=null&&value.trim().length()>0){
                        fieldName = value;
                    }
                    if(valueGetterMap.containsKey(fieldName))
                        throw new DuplicateField(clazz,fieldName);
                    else {
                        field.setAccessible(true);
                        valueGetterMap.put(fieldName, new FieldValueGetter(field));
                    }
                }
            }

//            Method[] methods = clazz.getDeclaredMethods();
//            for(Method method:methods){
//                MethodField methodField = method.getAnnotation(MethodField.class);
//                if(methodField!=null){
//                    if(method.getReturnType().getName().equalsIgnoreCase("void")){
//                        throw new IllegalMethod(clazz,method.getName());
//                    }
//
//                    String fieldName = method.getName();
//                    String value = methodField.value();
//                    if(value!=null&&value.length()>0){
//                        fieldName = value;
//                    }
//                    if(valueGetterMap.containsKey(fieldName))
//                        throw new DuplicateField(clazz,fieldName);
//                    else {
//                        method.setAccessible(true);
//                        valueGetterMap.put(fieldName, new MethodValueGetter(method));
//                    }
//                }
//            }
            clazz = clazz.getSuperclass();
        }

    }

    private SpaceImp(){}

    @Override
    public synchronized T get(Object values,String field,Object...parameters){
        ValueGetter valueGetter = getValueGetter(field);
        for(T t:ts){
            try {
                Object v = valueGetter.get(t,parameters);
                if(equals(v,values)){
                    return t;
                }
            }catch (Exception e){
                throw new RuntimeException(e.getMessage());
            }
        }
        return null;
    }

    @Override
    public synchronized List<T> getList() {
        return new ArrayList<>(ts);
    }

    @Override
    public synchronized List<T> getList(List<Object> values,String field,Object...parameters){
        ValueGetter valueGetter = getValueGetter(field);
        List<T> res = new ArrayList<>();
        for(T t:ts){
            try {
                Object v = valueGetter.get(t,parameters);
                if(values.contains(v)){
                    res.add(t);
                }
            }catch (Exception e){
                throw new RuntimeException(e.getMessage());
            }
        }
        return res;
    }

    @Override
    public Space copy() {
        SpaceImp spaceImp = new SpaceImp();
        spaceImp.tClass = this.tClass;
        spaceImp.valueGetterMap = new HashMap(valueGetterMap);
        return spaceImp;
    }

    @Override
    public boolean contains(T t) {
        return ts.contains(t);
    }

    @Override
    public synchronized List<T> getList(Object value,String field,Object... parameters) {
        ValueGetter valueGetter = getValueGetter(field);
        List<T> res = new ArrayList<>();
        for(T t:ts){
            try {
                    Object v = valueGetter.get(t,parameters);
                    if(equals(v,value)){
                        res.add(t);
                    }
            }catch (Exception e){
                throw new RuntimeException(e.getMessage());
            }
        }
        return res;
    }

    @Override
    public synchronized void add(T t) {
        if (!ts.contains(t)) {
            ts.add(t);
        }
    }

    @Override
    public synchronized void remove(T t) {
        ts.remove(t);
    }

    private boolean equals(Object a,Object b){
        if(a==b){
            return true;
        }
        if(b==null||a!=null&&a.equals(b)){
            return true;
        }
        return false;
    }

    private synchronized ValueGetter getValueGetter(String field){
        ValueGetter valueGetter = valueGetterMap.get(field);
        if(valueGetter!=null)
            return valueGetter;
        else
            throw new NoSuchField(tClass,field);
    }

    /**
     * 获取Value
     * @param <T>
     */
    private interface ValueGetter<T>{
        Object get(T t,Object...parameters) throws Exception;
    }

    private static class FieldValueGetter implements ValueGetter{
        Field field;

        public FieldValueGetter(Field field) {
            this.field = field;
        }

        @Override
        public Object get(Object o, Object... parameters) throws Exception {
            return field.get(o);
        }
    }

    private static class MethodValueGetter implements ValueGetter{
        Method method;

        public MethodValueGetter(Method method) {
            this.method = method;
        }

        @Override
        public Object get(Object o, Object... parameters) throws Exception {
            return method.invoke(o,parameters);
        }
    }
}
