package org.jboss.resteasy.test.client.resource;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.io.InputStream;

@Path("/inputStream")
@Produces("text/plain")
@Consumes("text/plain")
public interface InputStreamResourceClient {

   @GET
   String getAsString();

   @GET
   Response getAsInputStream();

   @POST
   Response postInputStream(InputStream is);

   @POST
   Response postString(String s);
}
