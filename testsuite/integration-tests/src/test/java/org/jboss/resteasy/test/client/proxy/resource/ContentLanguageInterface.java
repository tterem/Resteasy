package org.jboss.resteasy.test.client.proxy.resource;

import javax.ws.rs.*;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

@Path("/api")
public interface ContentLanguageInterface {

   @POST
   @Produces(MediaType.TEXT_PLAIN)
   @Consumes(MediaType.TEXT_PLAIN)
   String contentLang1(@HeaderParam(HttpHeaders.CONTENT_LANGUAGE) String contentLanguage, String subject);

   @Path("/second")
   @POST
   @Produces(MediaType.TEXT_PLAIN)
   @Consumes(MediaType.TEXT_PLAIN)
   String contentLang2(String subject, @HeaderParam(HttpHeaders.CONTENT_LANGUAGE) String contentLanguage);

}
