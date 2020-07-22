package com.quitee.baxia.core.exceptions;

public class NoSuchField extends RuntimeException {
    Class aClass;
    String field;
    public NoSuchField(Class aClass,String field) {
        super("can not find the field ["+field+"] in the space of class:"+aClass.getName());
        this.aClass = aClass;
        this.field = field;
    }
}
