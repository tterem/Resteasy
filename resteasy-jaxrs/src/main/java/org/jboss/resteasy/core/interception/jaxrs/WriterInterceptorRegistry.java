package org.jboss.resteasy.core.interception.jaxrs;

import org.jboss.resteasy.spi.ResteasyProviderFactory;

import javax.ws.rs.ext.WriterInterceptor;

/**
 * @author <a href="mailto:bill@burkecentral.com">Bill Burke</a>
 * @version $Revision: 1 $
 */
public class WriterInterceptorRegistry extends JaxrsInterceptorRegistry<WriterInterceptor> {
   public WriterInterceptorRegistry(ResteasyProviderFactory providerFactory) {
      super(providerFactory, WriterInterceptor.class);
   }

   public WriterInterceptorRegistry clone(ResteasyProviderFactory factory) {
      WriterInterceptorRegistry clone = new WriterInterceptorRegistry(factory);
      clone.interceptors.addAll(interceptors);
      return clone;
   }
}
