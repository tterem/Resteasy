package org.jboss.resteasy.test.providers.priority.resource;

import javax.annotation.Priority;
import javax.ws.rs.ext.ParamConverter;
import javax.ws.rs.ext.ParamConverterProvider;
import javax.ws.rs.ext.Provider;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

@Provider
@Priority(20)
public class ProviderPriorityFooParamConverterProviderBBB implements ParamConverterProvider {

   @SuppressWarnings("unchecked")
   @Override
   public <T> ParamConverter<T> getConverter(Class<T> rawType, Type genericType, Annotation[] annotations) {
      return (ParamConverter<T>) new ProviderPriorityFooParamConverter("BBB");
   }
}
