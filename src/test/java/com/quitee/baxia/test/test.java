package com.quitee.baxia.test;

import com.quitee.baxia.core.Baxia;

public class test {
    public void a(){

    }
    public static void main(String[] args) throws ClassNotFoundException {
        Baxia.scan("com.quitee.baxia");

        System.out.println("----------------------------------------------");

        MyClass myClass = new MyClass();
        myClass.setId(1);
        myClass.setName("class 4-4");
        Baxia.add(myClass);

        Student student = new Student();
        student.setName("q1");
        student.setAge(12);
        student.setGender("m");
        student.setExs("hair","black");
        student.setClassId(1);
        Baxia.add(student);

        Student student2 = new Student();
        student2.setName("q2");
        student2.setAge(12);
        student2.setGender("m");
        student2.setExs("hair","white");
        student2.setClassId(1);
        Baxia.add(student2);

        System.out.println(student.getMyClass());
        System.out.println(myClass.getStudents());
        System.out.println( Baxia.get(Student.class,"EXS","black","hair"));
    }
}
