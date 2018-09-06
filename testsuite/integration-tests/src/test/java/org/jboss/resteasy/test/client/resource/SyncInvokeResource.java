package org.jboss.resteasy.test.client.resource;

import org.jboss.resteasy.test.client.SyncInvokeTest;

import javax.ws.rs.*;

@Path("/test")
public class SyncInvokeResource {
   @GET
   @Produces("text/plain")
   public String get() {
      return "get";
   }

   @PUT
   @Consumes("text/plain")
   public String put(String str) {
      return "put " + str;
   }

   @POST
   @Consumes("text/plain")
   public String post(String str) {
      return "post " + str;
   }

   @DELETE
   @Produces("text/plain")
   public String delete() {
      return "delete";
   }

   @SyncInvokeTest.PATCH
   @Produces("text/plain")
   @Consumes("text/plain")
   public String patch(String str) {
      return "patch " + str;
   }
}
