package org.jboss.resteasy.test.resource.param.resource;

import javax.ws.rs.*;

@Path("/")
public class FormParamResource {
   @POST
   @Path("form")
   @Consumes("application/x-www-form-urlencoded")
   public String post(@Encoded @FormParam("param") String param) {
      return param;
   }
}
