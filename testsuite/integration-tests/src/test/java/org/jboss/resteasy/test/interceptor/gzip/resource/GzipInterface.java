package org.jboss.resteasy.test.interceptor.gzip.resource;

import org.jboss.resteasy.annotations.GZIP;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/gzip")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface GzipInterface {

   @GET
   @Path("/process")
   @GZIP
   String process(@QueryParam("name") String message);

}
