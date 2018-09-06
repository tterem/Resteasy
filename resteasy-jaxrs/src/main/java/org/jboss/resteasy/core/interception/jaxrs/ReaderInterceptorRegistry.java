package org.jboss.resteasy.core.interception.jaxrs;

import org.jboss.resteasy.spi.ResteasyProviderFactory;

import javax.ws.rs.ext.ReaderInterceptor;

/**
 * @author <a href="mailto:bill@burkecentral.com">Bill Burke</a>
 * @version $Revision: 1 $
 */
public class ReaderInterceptorRegistry extends JaxrsInterceptorRegistry<ReaderInterceptor> {
   public ReaderInterceptorRegistry(ResteasyProviderFactory providerFactory) {
      super(providerFactory, ReaderInterceptor.class);
   }

   public ReaderInterceptorRegistry clone(ResteasyProviderFactory factory) {
      ReaderInterceptorRegistry clone = new ReaderInterceptorRegistry(factory);
      clone.interceptors.addAll(interceptors);
      return clone;
   }
}
