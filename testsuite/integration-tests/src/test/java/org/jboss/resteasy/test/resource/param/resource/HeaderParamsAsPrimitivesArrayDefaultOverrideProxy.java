package org.jboss.resteasy.test.resource.param.resource;

import javax.ws.rs.*;

@Path("/array/default/override")
public interface HeaderParamsAsPrimitivesArrayDefaultOverrideProxy {
   @GET
   @Produces("application/boolean")
   String doGetBoolean(@HeaderParam("boolean") @DefaultValue("false") boolean[] v);

   @GET
   @Produces("application/short")
   String doGetShort(@HeaderParam("int") @DefaultValue("0") short[] v);
}
