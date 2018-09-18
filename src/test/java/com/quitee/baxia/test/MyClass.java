package com.quitee.baxia.test;

import com.quitee.baxia.annotations.Fetch;
import com.quitee.baxia.annotations.Field;
import com.quitee.baxia.annotations.Space;

import java.util.List;

@Space
public class MyClass {
    @Field
    Integer id;
    String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Fetch(targetField = "classId",targetFieldValue = "this.id")
    public List<Student> getStudents(){
        return null;
    }

    @Override
    public String toString() {
        return "MyClass{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
