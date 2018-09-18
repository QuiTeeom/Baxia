package com.quitee.baxia.agent;

import java.lang.instrument.Instrumentation;

public class BxAgent {

    public static void premain(String args, Instrumentation instrumentation){
        System.out.println("Baxia Agent Start");
        instrumentation.addTransformer(new BxClassTransformer());
    }
}
