package com.quitee.baxia.sample;


import com.quitee.baxia.core.annotations.Fetch;
import com.quitee.baxia.core.annotations.Field;
import com.quitee.baxia.core.annotations.Space;

import java.util.List;

@Space
public class School {
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

    @Fetch(targetField = "schoolId",targetFieldValue = "this.id")
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
