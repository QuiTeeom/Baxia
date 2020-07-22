package com.quitee.baxia.core.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Fetch {
    String targetField() default "";
    String targetFieldValue() default "";
    String targetFieldParameters() default "";
}
