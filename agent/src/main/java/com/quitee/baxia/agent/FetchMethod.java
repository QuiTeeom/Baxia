package com.quitee.baxia.agent;

import com.quitee.baxia.core.annotations.Fetch;
import javassist.CtMethod;

/**
 * @author quitee
 * @date 2020/7/19
 */
public class FetchMethod {
    Fetch fetch;
    CtMethod ctMethod;

    public FetchMethod(Fetch fetch, CtMethod ctMethod) {
        this.fetch = fetch;
        this.ctMethod = ctMethod;
    }

    public Fetch getFetch() {
        return fetch;
    }

    public CtMethod getCtMethod() {
        return ctMethod;
    }
}
