package com.quitee.baxia.core;

import com.quitee.baxia.exceptions.NoSuchSpace;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Transaction implements SpaceGroup {
    private Map<Class, Space> spaceMap = new HashMap<>();
    private Map<Class, List> removeListMap = new HashMap<>();

    @Override
    public <T> T get(Class<T> tClass, String where, Object value, Object... parameters) {
        T res;

        res = Baxia.get(tClass,where,value,parameters);
        T t = (T) getSpace(tClass).get(value,where,parameters);

        if(t!=null){
            res = t;
        }

        if(getRemoveList(tClass).contains(res)){
            res = null;
        }

        return res;
    }

    @Override
    public <T> List<T> getList(Class<T> tClass, String where, Object value, Object... parameters) {
        List res = new ArrayList();
        List<T> oldTs = Baxia.getList(tClass,where,value,parameters);
        List<T> ts = getSpace(tClass).getList(value,where,parameters);
        res.addAll(ts);
        res.addAll(oldTs);
        removeBfromA(res,getRemoveList(tClass));
        return res;
    }

    @Override
    public <T> List<T> getList(Class<T> tClass, String where, List<Object> values, Object... parameters) {
        List res = new ArrayList();
        List<T> ts = getSpace(tClass).getList(values,where,parameters);
        List<T> oldTs = Baxia.getList(tClass,where,values,parameters);
        res.addAll(ts);
        res.addAll(oldTs);
        removeBfromA(res,getRemoveList(tClass));
        return res;
    }

    @Override
    public <T> List<T> getList(Class<T> tClass) {
        List res = new ArrayList();
        List<T> ts = getSpace(tClass).getList();
        List<T> oldTs = Baxia.getList(tClass);

        res.addAll(ts);
        res.addAll(oldTs);
        removeBfromA(res,getRemoveList(tClass));
        return res;
    }

    @Override
    public void add(Object o) {
        getSpace(o.getClass()).add(o);
    }

    @Override
    public void remove(Object o) {
        Space space = getSpace(o.getClass());
        if (space.contains(o))
            space.remove(o);
        else
            getRemoveList(o.getClass()).add(o);
    }

    public void commit(){
        for(Class tClass:new ArrayList<>(spaceMap.keySet())){
            List l = spaceMap.get(tClass).getList();
            for(Object o:l){
                Baxia.add(o);
            }
            for(Object o:getRemoveList(tClass)){
                Baxia.remove(o);
            }
        }
    }

    public void destroy(){
        Baxia.destroyTransaction();
    }

    private synchronized Space getSpace(Class tClass) {
        Space space = spaceMap.get(tClass);
        if (space == null){
            space = Baxia.copySpace(tClass);
            spaceMap.put(tClass,space);
        }
        return space;
    }

    private synchronized List getRemoveList(Class tClass){
        List l = removeListMap.get(tClass);
        if(l==null){
            l = new ArrayList();
            removeListMap.put(tClass,l);
        }
        return l;
    }

    private void removeBfromA(List A,List B){
        if(A==null||B==null){
            return;
        }
        for(Object o:B){
            A.remove(o);
        }
    }
}
