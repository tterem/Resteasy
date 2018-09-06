package org.resteasy.reactivecontext;

import io.reactiverse.reactivecontexts.core.Context;
import org.jboss.resteasy.plugins.server.servlet.Cleanables;
import org.jboss.resteasy.spi.ResteasyProviderFactory;

import javax.annotation.Priority;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

@Priority(-1000)
@PreMatching
@Provider
public class ContextFilter implements ContainerRequestFilter {

   @Override
   public void filter(ContainerRequestContext requestContext) throws IOException {
      Context context = ResteasyProviderFactory.getContextData(Context.class);
      Cleanables cleanables = ResteasyProviderFactory.getContextData(Cleanables.class);
      Context.setThreadInstance(context);
      cleanables.addCleanable(() -> Context.clearThreadInstance());
   }

}
