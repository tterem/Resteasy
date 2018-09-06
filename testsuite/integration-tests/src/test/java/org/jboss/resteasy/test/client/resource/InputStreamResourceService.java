package org.jboss.resteasy.test.client.resource;

import org.jboss.resteasy.util.ReadFromStream;

import javax.ws.rs.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Path("/inputStream")
public class InputStreamResourceService {
   static String result = "hello";

   @GET
   @Produces("text/plain")
   public InputStream get() {
      return new ByteArrayInputStream(result.getBytes());
   }

   @POST
   @Consumes("text/plain")
   public void post(InputStream is) throws IOException {
      result = new String(ReadFromStream.readFromStream(1024, is));
   }

}
