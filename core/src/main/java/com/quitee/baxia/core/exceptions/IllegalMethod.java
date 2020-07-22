package com.quitee.baxia.core.exceptions;

public class IllegalMethod extends RuntimeException {
    Class aClass;
    String methodName;
    public IllegalMethod(Class aClass,String methodName) {
        super("@MethodField can not be a void method: "+ aClass.getName()+"::"+methodName);
        this.aClass = aClass;
        this.methodName = methodName;
    }
}