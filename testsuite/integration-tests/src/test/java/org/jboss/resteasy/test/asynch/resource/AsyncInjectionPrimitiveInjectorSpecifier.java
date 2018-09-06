package org.jboss.resteasy.test.asynch.resource;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


@Retention(RUNTIME)
@Target({FIELD, METHOD, PARAMETER})
public @interface AsyncInjectionPrimitiveInjectorSpecifier {
   Type value() default Type.VALUE;

   enum Type {
      VALUE, NULL, NO_RESULT;
   }
}