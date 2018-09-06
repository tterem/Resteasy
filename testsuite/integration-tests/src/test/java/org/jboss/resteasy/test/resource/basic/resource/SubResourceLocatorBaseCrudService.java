package org.jboss.resteasy.test.resource.basic.resource;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

public interface SubResourceLocatorBaseCrudService<T> {

   @GET
   @Path("/content/{id}")
   @Produces(MediaType.APPLICATION_JSON)
   T getContent(
           @PathParam("id")
                   String id);

   @POST
   @Path("/add")
   @Produces(MediaType.APPLICATION_JSON)
   @Consumes(MediaType.APPLICATION_JSON)
   T add(T object);

   @GET
   @Path("/all")
   @Produces(MediaType.APPLICATION_JSON)
   List<T> get();

   @PUT
   @Path("/update")
   @Produces(MediaType.APPLICATION_JSON)
   @Consumes(MediaType.APPLICATION_JSON)
   T update(T object);

   @DELETE
   @Path("/delete/{id}")
   @Produces(MediaType.TEXT_PLAIN)
   Boolean delete(
           @PathParam("id")
                   String id);
}
