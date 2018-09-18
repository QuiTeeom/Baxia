package com.quitee.baxia.exceptions;

public class NoSuchSpace extends RuntimeException {
    Class aClass;

    public NoSuchSpace(Class aClass) {
        super("can not find the space of class:"+aClass);
        this.aClass = aClass;
    }
}
