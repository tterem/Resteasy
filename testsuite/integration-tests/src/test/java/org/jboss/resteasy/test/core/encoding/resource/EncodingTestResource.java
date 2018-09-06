package org.jboss.resteasy.test.core.encoding.resource;

import javax.ws.rs.*;

@Path("/test")
public class EncodingTestResource {
   @GET
   @Produces("text/plain")
   @Path("/path-param/{pathParam}")
   public String getPathParam(@PathParam("pathParam") String pathParam) {
      return pathParam;
   }


   @GET
   @Produces("text/plain")
   @Path("/query-param")
   public String getQueryParam(@QueryParam("queryParam") String queryParam) {
      return queryParam;
   }
}
