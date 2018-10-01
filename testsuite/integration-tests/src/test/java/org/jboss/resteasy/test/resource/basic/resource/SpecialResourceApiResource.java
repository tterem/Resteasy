package org.jboss.resteasy.test.resource.basic.resource;

import org.jboss.logging.Logger;
import org.junit.Assert;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;

@Path("/{api:(?i:api)}")
public class SpecialResourceApiResource{
   private static Logger logger=Logger.getLogger(SpecialResourceApiResource.class);

   @Path("/{func:(?i:func)}")
   @GET
   @Produces("text/plain")
   public String func(){
      return "hello";
   }

   @PUT
   public void put(@Context HttpHeaders headers,String val){
      logger.info(headers.getMediaType());
      Assert.assertEquals("Wrong request content",val,"hello");
   }
}
