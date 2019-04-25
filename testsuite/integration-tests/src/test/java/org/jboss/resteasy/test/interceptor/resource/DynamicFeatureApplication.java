package org.jboss.resteasy.test.interceptor.resource;

import org.jboss.logging.Logger;
import org.jboss.resteasy.test.interceptor.DynamicFeatureTest;

import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

public class DynamicFeatureApplication extends Application {

   protected static final Logger LOG = Logger.getLogger(DynamicFeatureTest.class.getName());

   public java.util.Set<java.lang.Class<?>> getClasses() {
      Set<Class<?>> resources = new HashSet<>();
      resources.add(DynamicFeatureResource.class);
      resources.add(AddDynamicFeature.class);
      LOG.error("here we are");
      return resources;
   }
}
