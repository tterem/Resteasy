package org.jboss.resteasy.plugins.guice;

import com.google.inject.Provider;
import org.jboss.resteasy.spi.*;

import java.util.concurrent.CompletionStage;

public class GuiceResourceFactory implements ResourceFactory {

   private final Provider provider;
   private final Class<?> scannableClass;
   private PropertyInjector propertyInjector;

   public GuiceResourceFactory(final Provider provider, final Class<?> scannableClass) {
      this.provider = provider;
      this.scannableClass = scannableClass;
   }

   public Class<?> getScannableClass() {
      return scannableClass;
   }

   public void registered(ResteasyProviderFactory factory) {
      propertyInjector = factory.getInjectorFactory().createPropertyInjector(scannableClass, factory);
   }

   @Override
   public CompletionStage<Object> createResource(final HttpRequest request, final HttpResponse response, final ResteasyProviderFactory factory) {
      final Object resource = provider.get();
      return propertyInjector.inject(request, response, resource, true)
              .thenApply(v -> resource);
   }

   public void requestFinished(final HttpRequest request, final HttpResponse response, final Object resource) {
   }

   public void unregistered() {
   }
}
