package org.jboss.resteasy.core.interception.jaxrs;

import org.jboss.resteasy.spi.ResteasyProviderFactory;

import javax.ws.rs.container.ContainerRequestFilter;
import java.lang.reflect.AccessibleObject;

/**
 * @author <a href="mailto:bill@burkecentral.com">Bill Burke</a>
 * @version $Revision: 1 $
 */
public class ContainerRequestFilterRegistry extends JaxrsInterceptorRegistry<ContainerRequestFilter> {
   public ContainerRequestFilterRegistry(ResteasyProviderFactory providerFactory) {
      super(providerFactory, ContainerRequestFilter.class);
   }

   public ContainerRequestFilterRegistry clone(ResteasyProviderFactory factory) {
      ContainerRequestFilterRegistry clone = new ContainerRequestFilterRegistry(factory);
      clone.interceptors.addAll(interceptors);
      return clone;
   }

   @Override
   public ContainerRequestFilter[] postMatch(Class declaring, AccessibleObject target) {
      return super.postMatch(declaring, target);
   }
}
