package org.jboss.resteasy.test.providers.multipart.resource;

import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.test.providers.multipart.InputPartDefaultCharsetOverwriteTest;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

@Provider
public class InputPartDefaultCharsetOverwriteNoContentTypeCharsetUTF8 implements ContainerRequestFilter{

   @Override
   public void filter(ContainerRequestContext requestContext) throws IOException{
      requestContext.setProperty(InputPart.DEFAULT_CHARSET_PROPERTY,InputPartDefaultCharsetOverwriteTest.UTF_8);
   }
}
