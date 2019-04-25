package org.jboss.resteasy.test.interceptor.resource;

import org.jboss.logging.Logger;
import org.jboss.resteasy.test.interceptor.DynamicFeatureTest;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.ext.ReaderInterceptor;
import javax.ws.rs.ext.ReaderInterceptorContext;
import javax.ws.rs.ext.WriterInterceptor;
import javax.ws.rs.ext.WriterInterceptorContext;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MakeItDoubleInterceptor implements ReaderInterceptor,
      WriterInterceptor {

   protected static final Logger LOG = Logger.getLogger(DynamicFeatureTest.class.getName());

   @Override
   public void aroundWriteTo(WriterInterceptorContext context)
         throws IOException, WebApplicationException {
      String entity = (String) context.getEntity();
//      if (entity != null) {
//         context.setEntity(entity + entity); // hello -> hellohello
//      }

      if (entity != null) { //when unhandled exception, status 500
         //does not have entity
         Integer i = Integer.parseInt(entity);
         entity = String.valueOf(i + 5);
         context.setEntity(entity);
      }
      LOG.error("This happens aroundWriteTo");
      context.proceed();
   }

   @Override
   public Object aroundReadFrom(ReaderInterceptorContext context)
         throws IOException, WebApplicationException {
      InputStream inputStream = context.getInputStream();
      InputStreamReader reader = new InputStreamReader(inputStream);
      BufferedReader br = new BufferedReader(reader);
      String entity = br.readLine();
      br.close();

      if (entity != null) {
         // entity += entity;

         Integer i = Integer.parseInt(entity);
         entity = String.valueOf(i + 5);

         context.setInputStream(new ByteArrayInputStream(entity.getBytes()));
      }
      LOG.error("This happens aroundReadFrom");
      return context.proceed();
   }

}
