package org.jboss.resteasy.test.spring.deployment.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

@Path("/")
public class TestResource {

   public static final String TEST_PATH = "test";

   public static final String TEST_RESPONSE = "test passed";

   public static final String LOAD_CLASS_PATH = "loadClass";

   public static final String CLASSNAME_PARAM = "className";

   @GET
   @Path(TEST_PATH)
   @Produces("text/plain")
   public String getBasic() {
      return TEST_RESPONSE;
   }

   @GET
   @Path(TestResource.LOAD_CLASS_PATH)
   @Produces("text/plain")
   public String loadClass(@QueryParam(CLASSNAME_PARAM) String className) {
      try {
         return getClass().getClassLoader().loadClass(className).getName();
      } catch (ClassNotFoundException e) {
         return null;
      }
   }

}
