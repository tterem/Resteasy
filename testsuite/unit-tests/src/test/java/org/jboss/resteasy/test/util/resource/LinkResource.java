package org.jboss.resteasy.test.util.resource;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("resource")
public class LinkResource {

   @GET
   @Path("get")
   public String get() {
      return "GET";
   }

   @DELETE
   @Path("delete")
   public String delete() {
      return "DELETE";
   }

   @GET
   @Produces(MediaType.APPLICATION_SVG_XML)
   @Path("producessvgxml")
   public String producesSvgXml() {
      return MediaType.APPLICATION_SVG_XML;
   }

   @POST
   @Consumes(MediaType.APPLICATION_JSON)
   @Path("consumesappjson")
   public String consumesAppJson() {
      return MediaType.APPLICATION_JSON;
   }

   @POST
   @Produces({MediaType.APPLICATION_XHTML_XML,
           MediaType.APPLICATION_ATOM_XML, MediaType.APPLICATION_SVG_XML})
   @Path("producesxml")
   public String producesXml() {
      return MediaType.APPLICATION_XHTML_XML;
   }

   @POST
   @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
   @Path("consumesform")
   public String consumesForm() {
      return MediaType.APPLICATION_FORM_URLENCODED;
   }

}
