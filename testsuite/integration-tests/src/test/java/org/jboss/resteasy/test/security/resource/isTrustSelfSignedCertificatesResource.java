package org.jboss.resteasy.test.security.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/certificate")
public class isTrustSelfSignedCertificatesResource {

   @Path("/hello")
   @GET
   public String hello() {
      return "Hello World!";
   }
}
