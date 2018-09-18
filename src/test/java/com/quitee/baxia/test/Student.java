package com.quitee.baxia.test;


import com.quitee.baxia.annotations.Fetch;
import com.quitee.baxia.annotations.Field;
import com.quitee.baxia.annotations.MethodField;
import com.quitee.baxia.annotations.Space;

import java.util.HashMap;
import java.util.Map;

@Space
public class Student {

    @Field("id")
    Integer id;

    String name;
    int age;

    @Field
    String gender;
    @Field
    Integer classId;

    Map<String,String> exs = new HashMap<>();

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @MethodField("EXS")
    public Object getExs(String key){
        return exs.get(key);
    }

    public void setExs(String ket,String value){
        exs.put(ket,value);
    }

    @Fetch(targetField="id",targetFieldValue="this.classId")
    public MyClass getMyClass(){
        return null;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                ", classId=" + classId +
                ", exs=" + exs +
                '}';
    }
}
