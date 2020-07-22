package com.quitee.baxia.sample;

import com.quitee.baxia.core.Baxia;
import com.sun.tools.attach.AgentInitializationException;
import com.sun.tools.attach.AgentLoadException;
import com.sun.tools.attach.AttachNotSupportedException;
import com.sun.tools.attach.VirtualMachine;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.List;

/**
 * @author quitee
 * @date 2020/7/22
 */
public class SampleB {
    public static void main(String[] args) throws InterruptedException, IOException, AttachNotSupportedException, AgentLoadException, AgentInitializationException {
        Baxia.scan("com.quitee.baxia.sample");
        System.out.println("==============================================");

        School school = new School();
        school.setId(1);
        school.setName("shu");
        Baxia.add(school);

        Student student = new Student();
        student.setSchoolId(1);

//        VirtualMachine.attach()

        System.out.println("-----sample:1-----");
        System.out.println("当前进程号："+getProcessID());

        new Thread(()->{
            while (true){
                try {
                    System.out.println(student.getSchool());
                }catch (Exception e){
                    e.printStackTrace();
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        Thread.sleep(5000);

        VirtualMachine.attach(getProcessID()).loadAgent("/Users/quitee/Documents/workspace/learn/javaagent/baxia/agent/target/baxia.jar");

    }
    public static final String getProcessID() {
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        return runtimeMXBean.getName().split("@")[0];
    }
}
