package org.jboss.resteasy.test.providers.custom.resource;

import javax.ws.rs.*;

@Path("/test")
public class SingletonCustomProviderResource {

   @POST
   @Consumes("application/octet-stream")
   public void testConsume(SingletonCustomProviderObject foo) {
   }


   @GET
   @Produces("application/octet-stream")
   public SingletonCustomProviderObject testProduce() {
      return new SingletonCustomProviderObject();
   }

}
