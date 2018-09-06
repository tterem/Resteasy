package org.jboss.resteasy.annotations.jaxrs;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Target({FIELD, METHOD, PARAMETER})
public @interface MatrixParam {
   String value() default "";
}
