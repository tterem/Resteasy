package org.jboss.resteasy.core.interception.jaxrs;

import org.jboss.resteasy.spi.ResteasyProviderFactory;

import javax.ws.rs.ext.WriterInterceptor;

/**
 * @author <a href="mailto:bill@burkecentral.com">Bill Burke</a>
 * @version $Revision: 1 $
 */
public class WriterInterceptorRegistryImpl extends JaxrsInterceptorRegistryImpl<WriterInterceptor>{
   public WriterInterceptorRegistryImpl(ResteasyProviderFactory providerFactory){
      super(providerFactory,WriterInterceptor.class);
   }

   public WriterInterceptorRegistryImpl clone(ResteasyProviderFactory factory){
      WriterInterceptorRegistryImpl clone=new WriterInterceptorRegistryImpl(factory);
      clone.interceptors.addAll(interceptors);
      return clone;
   }
}
