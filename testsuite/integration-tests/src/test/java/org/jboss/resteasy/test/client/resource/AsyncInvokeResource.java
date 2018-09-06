package org.jboss.resteasy.test.client.resource;

import org.jboss.resteasy.test.client.AsyncInvokeTest;

import javax.ws.rs.*;


@Path("/test")
public class AsyncInvokeResource {
   @GET
   @Produces("text/plain")
   public String get() throws Exception {
      Thread.sleep(100);
      return "get";
   }

   @PUT
   @Consumes("text/plain")
   public String put(String str) throws Exception {
      Thread.sleep(100);
      return "put " + str;
   }

   @POST
   @Consumes("text/plain")
   public String post(String str) throws Exception {
      Thread.sleep(100);
      return "post " + str;
   }

   @DELETE
   @Produces("text/plain")
   public String delete() throws Exception {
      Thread.sleep(100);
      return "delete";
   }

   @AsyncInvokeTest.PATCH
   @Produces("text/plain")
   @Consumes("text/plain")
   public String patch(String str) throws Exception {
      Thread.sleep(100);
      return "patch " + str;
   }
}
