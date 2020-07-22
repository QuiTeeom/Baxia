package com.quitee.baxia.core.exceptions;

public class DuplicateField extends RuntimeException {
    Class aClass;
    String fieldName;
    public DuplicateField(Class aClass,String fieldName) {
        super("duplicate field ["+fieldName+"] in class:"+aClass.getName());
        this.aClass = aClass;
        this.fieldName = fieldName;
    }
}
