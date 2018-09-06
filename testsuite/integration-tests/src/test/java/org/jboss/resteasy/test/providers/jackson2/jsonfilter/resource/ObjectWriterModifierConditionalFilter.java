package org.jboss.resteasy.test.providers.jackson2.jsonfilter.resource;

import org.jboss.resteasy.plugins.providers.jackson.ResteasyObjectWriterInjector;

import javax.servlet.*;
import java.io.IOException;

public class ObjectWriterModifierConditionalFilter implements Filter {
   private static ObjectFilterModifierConditional modifier = new ObjectFilterModifierConditional();

   @Override
   public void init(FilterConfig filterConfig) throws ServletException {
   }

   @Override
   public void doFilter(ServletRequest request, ServletResponse response,
                        FilterChain chain) throws IOException, ServletException {
      ResteasyObjectWriterInjector.set(Thread.currentThread().getContextClassLoader(), modifier);
      chain.doFilter(request, response);
   }

   @Override
   public void destroy() {
   }
}
