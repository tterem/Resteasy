package org.jboss.resteasy.test;

import org.jboss.logging.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

/**
 * @author <a href="mailto:bill@burkecentral.com">Bill Burke</a>
 * @version $Revision: 1 $
 */
@Path("/")
public class SimpleResource {

   private static final Logger LOG = Logger.getLogger(SimpleResource.class);

   @GET
   @Path("basic")
   @Produces("text/plain")
   public String getBasic() {
      LOG.info("getBasic()");
      return "basic";
   }

   @PUT
   @Path("basic")
   @Consumes("text/plain")
   public void putBasic(String body) {
      LOG.info(body);
   }

   @GET
   @Path("queryParam")
   @Produces("text/plain")
   public String getQueryParam(@QueryParam("param") String param) {
      LOG.info("query param: " + param);
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
