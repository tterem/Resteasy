package org.jboss.resteasy.test.core.basic.resource;

import javax.ws.rs.*;
import java.util.Collection;

public interface AnnotationInheritanceGenericsInterface<ENTITY_TYPE, ID_TYPE> {

   @GET
   Collection<ENTITY_TYPE> get();

   @GET
   @Path("{id}")
   ENTITY_TYPE get(@PathParam("id") ID_TYPE id);

   @POST
   ENTITY_TYPE post(ENTITY_TYPE entity);

   @PUT
   @Path("{id}")
   ENTITY_TYPE put(@PathParam("id") ID_TYPE id, ENTITY_TYPE entity);

}
