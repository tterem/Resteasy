package org.jboss.resteasy.test.response.resource;

import javax.ws.rs.*;

@Path("/foo")
public class HttpRequestParameterInjectorResource {
   @GET
   @POST
   @Produces("text/plain")
   public String get(@HttpRequestParameterInjectorClassicParam("param") String param,
                     @QueryParam("param") @DefaultValue("") String query,
                     @FormParam("param") @DefaultValue("") String form) {
      return String.format("%s, %s, %s", param, query, form);
   }
}
