package org.jboss.resteasy.test.providers.jaxb.resource;

import javax.ws.rs.*;

@Consumes("application/xml")
@Produces("application/xml")
public interface JaxbXmlRootElementClient {

   @GET
   @Path("/{name}")
   Parent getParent(@PathParam("name") String name);

   @POST
   Parent postParent(Parent parent);

}
