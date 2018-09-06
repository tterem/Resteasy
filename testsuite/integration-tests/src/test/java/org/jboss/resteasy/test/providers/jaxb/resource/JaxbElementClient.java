package org.jboss.resteasy.test.providers.jaxb.resource;

import javax.ws.rs.*;
import javax.xml.bind.JAXBElement;

@Consumes("application/xml")
@Produces("application/xml")
public interface JaxbElementClient {

   @GET
   @Path("/{name}")
   JAXBElement<Parent> getParent(@PathParam("name") String name);

   @POST
   JAXBElement<Parent> postParent(JAXBElement<Parent> parent);

}
