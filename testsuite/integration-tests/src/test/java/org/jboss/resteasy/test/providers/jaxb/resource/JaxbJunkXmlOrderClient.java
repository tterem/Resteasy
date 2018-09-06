package org.jboss.resteasy.test.providers.jaxb.resource;

import javax.ws.rs.*;

@Consumes({"application/junk+xml"})
@Produces({"application/junk+xml"})
public interface JaxbJunkXmlOrderClient {

   @GET
   @Path("/{name}")
   Parent getParent(@PathParam("name")
                            String name);

   @POST
   Parent postParent(Parent parent);

}
