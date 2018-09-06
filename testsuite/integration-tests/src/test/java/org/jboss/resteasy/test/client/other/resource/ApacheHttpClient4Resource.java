package org.jboss.resteasy.test.client.other.resource;

import javax.ws.rs.*;

@Path("/test")
public interface ApacheHttpClient4Resource {
   @GET
   @Produces("text/plain")
   String get();

   @GET
   @Path("error")
   @Produces("text/plain")
   String error();

   @POST
   @Path("data")
   @Produces("text/plain")
   @Consumes("text/plain")
   String getData(String data);
}
