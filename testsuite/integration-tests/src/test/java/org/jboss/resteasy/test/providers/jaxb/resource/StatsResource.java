package org.jboss.resteasy.test.providers.jaxb.resource;

import javax.ws.rs.*;

@Path("/")
public class StatsResource {
   @Path("locator")
   public Object getLocator() {
      return null;
   }

   @Path("entry/{foo:.*}")
   @PUT
   @Produces("text/xml")
   @Consumes("application/json")
   public void put() {

   }

   @Path("entry/{foo:.*}")
   @POST
   @Produces("text/xml")
   @Consumes("application/json")
   public void post() {

   }

   @DELETE
   @Path("resource")
   public void delete() {
   }

   @HEAD
   @Path("resource")
   public void head() {
   }
}
