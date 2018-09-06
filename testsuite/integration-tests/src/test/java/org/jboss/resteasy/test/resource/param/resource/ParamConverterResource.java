package org.jboss.resteasy.test.resource.param.resource;

import org.junit.Assert;

import javax.ws.rs.*;

@Path("/")
public class ParamConverterResource {
   @Path("{pojo}")
   @PUT
   public void put(@QueryParam("pojo") ParamConverterPOJO q, @PathParam("pojo") ParamConverterPOJO pp, @MatrixParam("pojo") ParamConverterPOJO mp,
                   @HeaderParam("pojo") ParamConverterPOJO hp) {
      Assert.assertEquals(q.getName(), "pojo");
      Assert.assertEquals(pp.getName(), "pojo");
      Assert.assertEquals(mp.getName(), "pojo");
      Assert.assertEquals(hp.getName(), "pojo");
   }
}
