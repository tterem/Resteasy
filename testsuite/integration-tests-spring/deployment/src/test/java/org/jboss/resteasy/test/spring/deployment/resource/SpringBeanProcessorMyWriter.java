package org.jboss.resteasy.test.spring.deployment.resource;

import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

@Provider
@Produces("foo/bar")
public class SpringBeanProcessorMyWriter implements MessageBodyWriter<SpringBeanProcessorCustomer> {
   public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations,
                               MediaType mediaType) {
      return SpringBeanProcessorCustomer.class.isAssignableFrom(type);
   }

   public long getSize(SpringBeanProcessorCustomer springBeanProcessorCustomer, Class<?> type, Type genericType,
                  Annotation[] annotations, MediaType mediaType) {
      return springBeanProcessorCustomer.getName().getBytes().length + 9;
   }

   public void writeTo(SpringBeanProcessorCustomer springBeanProcessorCustomer, Class<?> type, Type genericType,
                  Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> httpHeaders,
                  OutputStream entityStream) throws IOException, WebApplicationException {
      entityStream.write(("springBeanProcessorCustomer=" + springBeanProcessorCustomer.getName()).getBytes());
   }
}
