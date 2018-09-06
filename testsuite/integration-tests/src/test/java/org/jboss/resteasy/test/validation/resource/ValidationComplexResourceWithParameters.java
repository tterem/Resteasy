package org.jboss.resteasy.test.validation.resource;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import javax.ws.rs.*;

@Path("/")
public class ValidationComplexResourceWithParameters {
   @POST
   @Path("/native")
   public void postNative(@Valid ValidationComplexFoo foo) {
   }

   @POST
   @Path("/imposed")
   public void postImposed(@ValidationComplexFooConstraint(min = 3, max = 5) ValidationComplexFoo foo) {
   }

   @POST
   @Path("nativeAndImposed")
   public void postNativeAndImposed(@Valid @ValidationComplexFooConstraint(min = 3, max = 5) ValidationComplexFoo foo) {
   }

   @POST
   @Path("other/{p}")
   public void postOther(@Size(min = 2, max = 3) @PathParam("p") String p,
                         @Size(min = 2, max = 3) @MatrixParam("m") String m,
                         @Size(min = 2, max = 3) @QueryParam("q") String q,
                         @Size(min = 2, max = 3) @FormParam("f") String f,
                         @Size(min = 2, max = 3) @HeaderParam("h") String h,
                         @Size(min = 2, max = 3) @CookieParam("c") String c
   ) {
   }
}
