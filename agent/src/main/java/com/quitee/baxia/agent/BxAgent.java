package com.quitee.baxia.agent;

import com.quitee.baxia.core.BaxiaClassLoader;

import java.lang.instrument.Instrumentation;

public class BxAgent {
    /**
     * agent 启动入口
     * @param args
     * @param instrumentation
     */
    public static void premain(String args, Instrumentation instrumentation){
        System.out.println("Baxia Agent Start");
        instrumentation.addTransformer(new BxClassTransformer(),true);
    }

    public static void agentmain(String args, Instrumentation instrumentation){
        System.out.println("Baxia Agent Start -- attach");
        instrumentation.addTransformer(new BxClassTransformer(),true);
        new BxClassTransformer().transform(instrumentation);
    }
}
