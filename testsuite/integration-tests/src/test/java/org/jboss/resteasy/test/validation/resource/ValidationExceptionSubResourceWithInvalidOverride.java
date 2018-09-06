package org.jboss.resteasy.test.validation.resource;

import javax.validation.ConstraintDeclarationException;
import javax.validation.constraints.Size;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.ext.Provider;

@Path("/")
public class ValidationExceptionSubResourceWithInvalidOverride extends ValidationExceptionSuperResource {

   @POST
   public void test(@Size(max = 3) String s) {
   }

   @Provider
   public static class ConstraintDeclarationExceptionMapper extends ValidationExceptionMapper<ConstraintDeclarationException> {
   }
}
