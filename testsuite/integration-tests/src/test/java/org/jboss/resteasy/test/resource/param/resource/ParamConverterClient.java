package org.jboss.resteasy.test.resource.param.resource;

import javax.ws.rs.*;

@Path("/")
public interface ParamConverterClient {
   @Path("{pojo}")
   @PUT
   void put(@QueryParam("pojo") String q, @PathParam("pojo") String pp, @MatrixParam("pojo") String mp,
            @HeaderParam("pojo") String hp);
}
