package org.jboss.resteasy.test.providers.multipart.resource;

import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.test.providers.multipart.InputPartDefaultCharsetOverwriteTest;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

@Provider
public class InputPartDefaultCharsetOverwriteContentTypeNoCharsetUTF16 implements ContainerRequestFilter{

   @Override
   public void filter(ContainerRequestContext requestContext) throws IOException{
      requestContext.setProperty(InputPart.DEFAULT_CONTENT_TYPE_PROPERTY,InputPartDefaultCharsetOverwriteTest.TEXT_HTTP_WITH_CHARSET_UTF_16);
   }
}
