package org.jboss.resteasy.test.core.encoding.resource;

import org.jboss.resteasy.test.core.encoding.EncodedParamsTest;
import org.junit.Assert;

import javax.ws.rs.*;

@Path("/encodedMethod")
public class EncodedParamsSimpleResource {
   @GET
   @Encoded
   public String get(@QueryParam("stuff") String stuff) {
      Assert.assertEquals(EncodedParamsTest.ERROR_MESSAGE, "hello%20world", stuff);
      return "HELLO";
   }

   @GET
   @Encoded
   @Path("/{param}")
   public String goodbye(@PathParam("param") String stuff) {
      Assert.assertEquals(EncodedParamsTest.ERROR_MESSAGE, "hello%20world", stuff);
      return "GOODBYE";
   }
}
