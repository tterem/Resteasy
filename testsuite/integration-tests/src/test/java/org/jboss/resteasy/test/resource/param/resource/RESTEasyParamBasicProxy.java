package org.jboss.resteasy.test.resource.param.resource;

import org.jboss.resteasy.annotations.jaxrs.*;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/proxy")
public interface RESTEasyParamBasicProxy {
   @POST
   @Path("a/{pathParam3}")
   Response post(
           @CookieParam String cookieParam3,
           @FormParam String formParam3,
           @HeaderParam String headerParam3,
           @MatrixParam String matrixParam3,
           @PathParam String pathParam3,
           @QueryParam String queryParam3);
}
