package org.jboss.resteasy.plugins.guice;

import javax.inject.Scope;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Provides an instance-per-request.
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestScoped {
}
