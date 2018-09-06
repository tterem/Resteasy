package org.jboss.resteasy.test.spring.inmodule.resource;

import org.jboss.logging.Logger;

import javax.ws.rs.*;

@Path("/")
public class SpringLocatingSimpleResource {

   private static Logger logger = Logger.getLogger(SpringLocatingSimpleResource.class.getName());

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


}
