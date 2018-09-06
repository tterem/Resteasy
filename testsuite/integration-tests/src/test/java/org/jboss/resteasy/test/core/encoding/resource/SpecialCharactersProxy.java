package org.jboss.resteasy.test.core.encoding.resource;

import javax.ws.rs.*;

@Path(value = "/sayhello")
public interface SpecialCharactersProxy {

   @GET
   @Path("/en/{in}")
   @Produces("text/plain")
   String sayHi(@PathParam(value = "in") String in);

   @POST
   @Path("/compile")
   String compile(@QueryParam("query") String queryText);


}
