package org.jboss.resteasy.test.crypto.resource;

import org.jboss.resteasy.annotations.security.doseta.Signed;
import org.jboss.resteasy.annotations.security.doseta.Verify;

import javax.ws.rs.*;

@Path("/signed")
public interface SigningProxy {
   @GET
   @Verify
   @Produces("text/plain")
   @Path("bad-signature")
   String bad();

   @GET
   @Verify
   @Produces("text/plain")
   String hello();

   @POST
   @Consumes("text/plain")
   @Signed(selector = "test", domain = "samplezone.org")
   void postSimple(String input);
}
