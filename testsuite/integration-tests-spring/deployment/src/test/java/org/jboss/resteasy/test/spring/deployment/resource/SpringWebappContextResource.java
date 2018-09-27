package org.jboss.resteasy.test.spring.deployment.resource;

import org.jboss.logging.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

@Path("/echo")
@Produces(MediaType.TEXT_PLAIN)
public class SpringWebappContextResource {

   private static Logger logger = Logger.getLogger(SpringWebappContextResource.class);
   private UriInfo ui;
   private HttpHeaders headers;

   @Context
   public void setUriInfo(UriInfo ui) {
      this.ui = ui;
   }

   @Context
   public void setHttpHeaders(HttpHeaders headers) {
      this.headers = headers;
   }

   @GET
   @Path("/uri")
   public String echoURI() {
      sleep();
      logger.info("requestUribuilder: " + ui.getRequestUriBuilder().toString());
      logger.info("request uri: " + ui.getRequestUri());
      return (ui != null ? ui.getRequestUriBuilder().build().toString() : "null");
   }

   @GET
   @Path("/headers")
   public String echoHeaders() {
      sleep();
      logger.info("uri: " + ui.getRequestUri().toString());
      logger.info("builder: " + ui.getRequestUriBuilder().build().toString());
      String s = headers != null && headers.getRequestHeader(HttpHeaders.ACCEPT) != null ? (ui != null ? ui
            .getRequestUriBuilder().build().toString()
            : "null")
            + ":" + headers.getRequestHeader(HttpHeaders.ACCEPT).get(0)
            : "null";
      logger.info("return: " + s);
      return s;
   }

   private void sleep() {
      try {
         Thread.sleep((int) (Math.random() * 1000));
      } catch (Exception e) {
      }
   }
}
