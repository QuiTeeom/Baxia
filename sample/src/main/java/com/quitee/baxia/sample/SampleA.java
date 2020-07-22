package com.quitee.baxia.sample;

import com.quitee.baxia.core.Baxia;

import java.util.List;

/**
 * @author quitee
 * @date 2020/7/22
 */
public class SampleA {
    public static void main(String[] args) {
        Baxia.scan("com.quitee.baxia.sample");
        System.out.println("==============================================");

        School school = new School();
        school.setId(1);
        school.setName("shu");
        Baxia.add(school);

        System.out.println("-----sample:1-----");
        Student student = new Student();
        student.setSchoolId(1);
        System.out.println(student.getSchool());



        for(int i = 0;i<=5;i++){
            Student add = new Student();
            add.setSchoolId(i%2+1);
            add.setId(i);
            add.setName("quitee-"+i);
            add.setGender(i%2+"");
            Baxia.add(add);
        }

        System.out.println("-----sample:2-----");
        printObjects(school.getStudents());
        System.out.println("-----sample:3-----");
        School fxzx = new School();
        fxzx.setId(2);
        fxzx.setName("fxzx");
        printObjects(fxzx.getStudents());



//        Baxia.add(student);

//        Student student2 = new Student();
//        student2.setName("q2");
//        student2.setGender("m");
//        student2.setExs("hair","white");
//        student2.setSchool(1);
//        Baxia.add(student2);
//
//        System.out.println(student.getMyClass());
//        System.out.println(school.getStudents());
//        System.out.println(Baxia.get(Student.class,"EXS","black","hair"));
//
//
//        Student student3 = new Student();
//        student3.setName("q3");
//        student3.setGender("f");
//        student3.setExs("hair","yellow");
//        student3.setSchool(1);
//
//        Transaction transaction = Baxia.createTransaction();
//        transaction.add(student3);
//        transaction.remove(student2);
//        transaction.commit();
//        transaction.destroy();
//
//        System.out.println(school.getStudents());
    }

    private static void printObjects(List objs){
        objs.forEach(System.out::println);
    }
}
