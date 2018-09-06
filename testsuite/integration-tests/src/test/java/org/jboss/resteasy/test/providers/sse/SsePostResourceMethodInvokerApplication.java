package org.jboss.resteasy.test.providers.sse;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.Collections;
import java.util.Set;

/**
 * @author Nicolas NESMON
 */
@ApplicationPath("/")
public class SsePostResourceMethodInvokerApplication extends Application {

   @Override
   public Set<Object> getSingletons() {
      return Collections.singleton(new SsePostResourceMethodInvokerTestResource());
   }

}
