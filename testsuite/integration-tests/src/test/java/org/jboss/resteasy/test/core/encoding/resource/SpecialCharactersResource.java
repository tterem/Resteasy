package org.jboss.resteasy.test.core.encoding.resource;

import org.junit.Assert;

import javax.annotation.Resource;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

@Resource(name = "test")
@Path(value = "/sayhello")
public class SpecialCharactersResource {
   private static final String QUERY = "select p from VirtualMachineEntity p where guest.guestId = :id";

   @Context
   UriInfo info;

   @GET
   @Path("/en/{in}")
   @Produces("text/plain")
   public String echo(@PathParam(value = "in") String in) {
      Assert.assertEquals("something something", in);
      return in;
   }


   @POST
   @Path("/compile")
   public String compile(@QueryParam("query") String queryText) {
      Assert.assertEquals(queryText, QUERY);
      return queryText;
   }

   @Path("/widget/{date}")
   @GET
   @Produces("text/plain")
   public String get(@PathParam("date") String date) {
      return date;
   }

   @Path("/plus/{plus}")
   @GET
   @Produces("text/plain")
   public String getPlus(@PathParam("plus") String p) {
      Assert.assertEquals("foo+bar", p);
      return p;
   }
}
