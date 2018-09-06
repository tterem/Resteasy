package org.jboss.resteasy.test.client.exception.resource;

import javax.ws.rs.*;

@Path("/")
public class ClientErrorResource {
   @Consumes("application/bar")
   @Produces("application/foo")
   @POST
   public String doPost(String entity) {
      return "content";
   }

   @Produces("text/plain")
   @GET
   @Path("complex/match")
   public String get() {
      return "content";
   }

   @Produces("text/xml")
   @GET
   @Path("complex/{uriparam: [^/]+}")
   public String getXml(@PathParam("uriparam") String param) {
      return "<" + param + "/>";
   }

   @DELETE
   public void delete() {

   }

   @Path("/nocontent")
   @POST
   public void noreturn(String entity) {

   }
}
