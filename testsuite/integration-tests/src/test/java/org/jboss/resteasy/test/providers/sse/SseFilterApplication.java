package org.jboss.resteasy.test.providers.sse;

import javax.ws.rs.core.Application;
import javax.ws.rs.ext.Provider;
import java.util.HashSet;
import java.util.Set;

@Provider
public class SseFilterApplication extends Application {
   private Set<Object> singletons = new HashSet<Object>();

   public Set<Object> getSingletons() {
      if (singletons.isEmpty()) {
         singletons.add(new SseResource());
         singletons.add(new CORSFilter());
      }
      return singletons;
   }

}
