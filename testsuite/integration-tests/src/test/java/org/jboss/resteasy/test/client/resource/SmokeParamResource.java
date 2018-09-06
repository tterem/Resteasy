package org.jboss.resteasy.test.client.resource;

import javax.ws.rs.*;

@Path("/foo")
public class SmokeParamResource {

   @POST
   @Produces("text/plain")
   @Consumes("text/plain")
   public String create(String cust) {
      return cust;
   }

   @GET
   @Produces("text/plain")
   public String get(@HeaderParam("a") String a, @QueryParam("b") String b) {
      return a + " " + b;
   }

}
