package com.quitee.baxia.sample;

import com.quitee.baxia.core.annotations.Fetch;
import com.quitee.baxia.core.annotations.Field;
import com.quitee.baxia.core.annotations.Space;

import java.util.HashMap;
import java.util.Map;

@Space
public class Student {
    @Field
    Integer id;

    String name;

    @Field
    String gender;

    @Field
    Integer schoolId;

    Map<String,String> exs = new HashMap<>();

//    @MethodField("EXS")
    public Object getExs(String key){
        return exs.get(key);
    }

    public void setExs(String ket,String value){
        exs.put(ket,value);
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                '}';
    }

    @Fetch(targetField="id",targetFieldValue="this.schoolId")
    public School getSchool(){
        return null;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }

    public Map<String, String> getExs() {
        return exs;
    }

    public void setExs(Map<String, String> exs) {
        this.exs = exs;
    }
}
