package org.jboss.resteasy.test.resource.resource;

import org.junit.Assert;

import javax.ws.rs.core.Configurable;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.UriInfo;

public class ProgrammaticResource {

   public int counter;
   private UriInfo uriInfo;
   private HttpHeaders headers;

   private Configurable<?> configurable;

   public ProgrammaticResource() {
   }

   public ProgrammaticResource(Configurable<?> configurable) {
      this.configurable = configurable;
   }

   public void setHeaders(HttpHeaders headers) {
      this.headers = headers;
   }

   public String get(String param) {
      Assert.assertEquals("hello", param);
      uriInfo.getBaseUri();
      headers.getCookies();
      configurable.getConfiguration();
      counter++;
      return "hello";
   }

   public void put(String value) {
      Assert.assertEquals("hello", value);
   }
}
