package org.jboss.resteasy.test.cdi.extensions.resource;

import javax.enterprise.context.NormalScope;
import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE, METHOD, FIELD})
@Retention(RUNTIME)
@Documented
@NormalScope
@Inherited
public @interface ScopeExtensionPlannedObsolescenceScope {
   int value() default 1;
}

