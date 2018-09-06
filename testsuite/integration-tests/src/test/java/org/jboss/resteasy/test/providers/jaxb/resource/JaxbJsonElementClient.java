package org.jboss.resteasy.test.providers.jaxb.resource;

import javax.ws.rs.*;
import javax.xml.bind.JAXBElement;

@Consumes("application/json")
@Produces("application/json")
public interface JaxbJsonElementClient {

   @GET
   @Path("/{name}")
   JAXBElement<Parent> getParent(@PathParam("name") String name);

   @POST
   JAXBElement<Parent> postParent(JAXBElement<Parent> parent);

}
