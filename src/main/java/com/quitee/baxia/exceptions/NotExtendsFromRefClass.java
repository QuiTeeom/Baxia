package com.quitee.baxia.exceptions;

public class NotExtendsFromRefClass extends RuntimeException{
    Class aClass;
    Class refClass;
    public NotExtendsFromRefClass(Class aClass,Class refClass) {
        super("["+aClass.getName()+"] does not extends from:["+refClass.getName()+"]");
        this.aClass = aClass;
        this.refClass = refClass;
    }
}
