package org.jboss.resteasy.test.spring.deployment.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.net.URISyntaxException;

@Controller
@Path(ContactsResource.CONTACTS_URL)
public class ContactsResource {
   public static final String CONTACTS_URL = "/contacts";
   @Autowired
   ContactService service;

   @GET
   @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
   @Path("data")
   public Contacts getAll() {
      return service.getAll();
   }

   @POST
   @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
   @Path("data")
   public Response saveContact(@Context UriInfo uri, Contact contact)
           throws URISyntaxException {
      service.save(contact);
      URI newURI = UriBuilder.fromUri(uri.getPath()).path(contact.getLastName()).build();
      return Response.created(newURI).build();
   }

   @GET
   @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
   @Path("data/{lastName}")
   public Contact get(@PathParam("lastName") String lastName) {
      return service.getContact(lastName);
   }
}
