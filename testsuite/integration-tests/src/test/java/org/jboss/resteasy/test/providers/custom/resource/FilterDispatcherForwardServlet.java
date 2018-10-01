package org.jboss.resteasy.test.providers.custom.resource;

import org.jboss.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import java.io.IOException;

public class FilterDispatcherForwardServlet extends HttpServlet{
   private static final long serialVersionUID=1L;
   private static Logger logger=Logger.getLogger(FilterDispatcherForwardServlet.class);

   public void service(ServletRequest req,ServletResponse res) throws ServletException, IOException{
      logger.info("enterng ForwardServlet.service()");
      res.getOutputStream().write("forward".getBytes());
   }
}
