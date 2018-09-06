package org.jboss.resteasy.test.cdi.injection.resource;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import org.jboss.resteasy.spi.HttpRequest;
import org.jboss.resteasy.spi.HttpResponse;
import org.jboss.resteasy.spi.ResourceFactory;
import org.jboss.resteasy.spi.ResteasyProviderFactory;

public class LazyInitUriInfoInjectionSingletonResource implements ResourceFactory {
   private Class clazz;
   private Object obj;

   public LazyInitUriInfoInjectionSingletonResource(final Class clazz) {
      this.clazz = clazz;
   }

   @Override
   public void registered(ResteasyProviderFactory factory) {

   }

   public CompletionStage<Object> createResource(HttpRequest request, HttpResponse response, ResteasyProviderFactory factory) {
      if (obj == null) {
         try {
            obj = clazz.newInstance();
         } catch (InstantiationException e) {
            throw new RuntimeException(e);
         } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
         }
         return factory.getInjectorFactory().createPropertyInjector(clazz, factory).inject(obj, true)
                  .thenApply(v -> obj);
      }
      return CompletableFuture.completedFuture(obj);
   }

   public void unregistered() {
   }

   public Class<?> getScannableClass() {
      return clazz;
   }

   public void requestFinished(HttpRequest request, HttpResponse response, Object resource) {
   }
}
