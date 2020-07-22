package com.quitee.baxia.core;

import java.util.List;

public interface SpaceGroup {
    <T> T get(Class<T> tClass, String where, Object value, Object... parameters);

    <T> List<T> getList(Class<T> tClass, String where, Object value, Object... parameters);

    <T> List<T> getList(Class<T> tClass, String where, List<Object> values, Object... parameters);

    <T> List<T> getList(Class<T> tClass);

    void add(Object o);

    void remove(Object o);
}
