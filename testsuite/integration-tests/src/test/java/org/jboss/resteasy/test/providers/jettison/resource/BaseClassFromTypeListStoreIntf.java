package org.jboss.resteasy.test.providers.jettison.resource;

import org.jboss.resteasy.annotations.providers.jaxb.Wrapped;
import org.jboss.resteasy.annotations.providers.jaxb.json.BadgerFish;

import javax.ws.rs.*;
import java.util.List;

public interface BaseClassFromTypeListStoreIntf<T> {
   @GET
   @Path("/intf")
   @Produces("application/json")
   @BadgerFish
   @Wrapped
   List<T> list();

   @PUT
   @Path("/intf")
   @Consumes("application/json")
   void put(@Wrapped @BadgerFish List<T> list);
}
