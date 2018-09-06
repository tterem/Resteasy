package org.jboss.resteasy.test.providers.jaxb.resource;

import javax.ws.rs.*;

@Consumes("application/json")
@Produces("application/json")
public interface JaxbJsonXmlRootElementClient {

   @GET
   @Path("/{name}")
   Parent getParent(@PathParam("name") String name);

   @GET
   @Path("/{name}")
   String getParentString(@PathParam("name") String name);

   @POST
   Parent postParent(Parent parent);

}
