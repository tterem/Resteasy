package org.jboss.resteasy.test.client.proxy.resource;


import org.jboss.logging.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/")
public class ClientSmokeResource {
   private static Logger logger = Logger.getLogger(ClientSmokeResource.class);

   @GET
   @Path("basic")
   @Produces("text/plain")
   public String getBasic() {
      logger.info("getBasic()");
      return "basic";
   }

   @PUT
   @Path("basic")
   @Consumes("text/plain")
   public void putBasic(String body) {
      logger.info(body);
   }

   @GET
   @Path("queryParam")
   @Produces("text/plain")
   public String getQueryParam(@QueryParam("param") String param) {
      logger.info("query param: " + param);
      return param;
   }

   @GET
   @Path("matrixParam")
   @Produces("text/plain")
   public String getMatrixParam(@MatrixParam("param") String param) {
      return param;
   }

   @GET
   @Path("uriParam/{param}")
   @Produces("text/plain")
   public int getUriParam(@PathParam("param") int param) {
      return param;
   }

   @GET
   @Path("header")
   public Response getHeader() {
      return Response.ok().header("header", "headervalue").build();
   }
}
