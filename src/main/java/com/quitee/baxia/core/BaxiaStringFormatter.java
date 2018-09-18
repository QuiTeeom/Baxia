package com.quitee.baxia.core;

public class BaxiaStringFormatter {
    String get = "get";
    String className;

    String field;
    String fieldValue;

    boolean isList = false;

    public BaxiaStringFormatter(String className) {
        this.className = className;
    }

    public BaxiaStringFormatter getList(boolean isList){
        get = isList?"getList":"get";
        this.isList = isList;
        return this;
    }

    public BaxiaStringFormatter setField(String field,String fieldValue){
        this.field = field;
        this.fieldValue = fieldValue;
        return this;
    }


    public String build(){
        String res = "{";

        res += "Object o = com.quitee.baxia.core.Baxia";
        res += "." + get +"(" + className +".class" ;
        if(field!=null&&fieldValue!=null){
            res += ",\""+field+"\","+fieldValue;
        }
        res += ",new Object[0]);";

        if (this.isList){
            res += "return (java.util.List)o;}";
        }else
            res += "return ("+className+")o;}";
        return res;
    }
}
