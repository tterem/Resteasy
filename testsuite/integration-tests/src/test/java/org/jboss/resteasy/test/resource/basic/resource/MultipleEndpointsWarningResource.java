package org.jboss.resteasy.test.resource.basic.resource;

import org.jboss.resteasy.resteasy_jaxrs.i18n.LogMessages;

import javax.ws.rs.*;
import java.util.logging.LogManager;

@Path("/")
public class MultipleEndpointsWarningResource {

   @SuppressWarnings("unused")
   private static String MESSAGE_CODE = "RESTEASY002142";
   private LogHandler logHandler = new LogHandler();

   @Path("setup")
   @GET
   public void setup() {
      LogManager.getLogManager().getLogger(LogMessages.LOGGER.getClass().getPackage().getName()).addHandler(logHandler);
   }

   @Path("teardown")
   @GET
   public void teardown() {
      LogManager.getLogManager().getLogger(LogMessages.LOGGER.getClass().getPackage().getName()).removeHandler(logHandler);
   }

   @Path("unique")
   @GET
   @Produces("text/plain")
   public int unique() throws Exception {
      return logHandler.getMessagesLogged();
   }

   @Path("verbs")
   @GET
   @Produces("text/plain")
   public int getVerb() throws Exception {
      return logHandler.getMessagesLogged();
   }

   @Path("verbs")
   @POST
   @Produces("text/plain")
   public int postVerb() throws Exception {
      return logHandler.getMessagesLogged();
   }

   @Path("verbs")
   @PUT
   @Produces("text/plain")
   public int putVerb() throws Exception {
      return logHandler.getMessagesLogged();
   }

   @Path("duplicate")
   @GET
   public int duplicate1() throws Exception {
      return logHandler.getMessagesLogged();
   }

   @Path("duplicate")
   @GET
   public int duplicate2() throws Exception {
      return logHandler.getMessagesLogged();
   }

   @GET
   @Path("{id}")
   public int withId(@PathParam("id") Integer id) {
      return logHandler.getMessagesLogged();
   }
}
