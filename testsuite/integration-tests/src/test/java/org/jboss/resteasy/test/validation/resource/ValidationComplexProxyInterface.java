package org.jboss.resteasy.test.validation.resource;

import javax.validation.constraints.Size;
import javax.ws.rs.*;

@Path("proxy")
public interface ValidationComplexProxyInterface {
   @GET
   @Produces("text/plain")
   @Size(min = 2, max = 4)
   String g();

   @POST
   @Path("{s}")
   void s(@PathParam("s") String s);
}
