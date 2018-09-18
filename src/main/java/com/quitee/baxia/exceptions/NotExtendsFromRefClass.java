package com.quitee.baxia.exceptions;

public class NotExtendsFromRefClass extends RuntimeException{
    Class aClass;

    public NotExtendsFromRefClass(Class aClass) {
        super("the class does not extends from:"+aClass);
        this.aClass = aClass;
    }
}
