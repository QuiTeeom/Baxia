package com.quitee.baxia.core;

import java.util.List;

public interface Space<T> {
    void add(T t);
    void remove(T t);
    T get(Object value,String field,Object... methodFieldParameters);
    List<T> getList();
    List<T> getList(Object value,String field,Object... methodFieldParameters);
    List<T> getList(List<Object> values,String field,Object... methodFieldParameters);
    Space copy();
    boolean contains(T t);
}
