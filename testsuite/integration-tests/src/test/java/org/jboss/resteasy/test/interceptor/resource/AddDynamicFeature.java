package org.jboss.resteasy.test.interceptor.resource;

import javax.ws.rs.container.DynamicFeature;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.FeatureContext;
import javax.ws.rs.ext.Provider;

@Provider
public class AddDynamicFeature implements DynamicFeature {

   @Override
   public void configure(ResourceInfo resourceInfo, FeatureContext context) {
      if (resourceInfo.getResourceMethod().getName().equals("hello")) {
         context.register(MakeItDoubleInterceptor.class);
      }
   }

}
