package org.jboss.resteasy.test.cdi.extensions.resource;

import javax.inject.Qualifier;
import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Qualifier
@Target({TYPE,METHOD,FIELD,PARAMETER})
@Retention(RUNTIME)
@Documented
@Inherited
public @interface CDIExtensionsBoston{
}

