package org.jboss.resteasy.test.resource.path.resource;

import javax.ws.rs.*;

public class PathParamMissingDefaultValueBeanParamEntity {

   @CookieParam("bpeCookie")
   public String bpeCookie;

   @FormParam("bpeForm")
   public String bpeForm;

   @HeaderParam("bpeHeader")
   public String bpeHeader;

   @MatrixParam("bpeMatrix")
   public String bpeMatrix;

   @PathParam("bpePath")
   public String bpePath;

   @QueryParam("bpeQuery")
   public String bpeQuery;

   public String toString() {
      return bpeCookie + bpeForm + bpeHeader + bpeMatrix + bpePath + bpeQuery;
   }
}
