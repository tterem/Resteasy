package org.jboss.resteasy.spi;

import javax.ws.rs.core.Response;
import java.lang.reflect.Method;
import java.util.concurrent.CompletionStage;

/**
 * @author <a href="mailto:bill@burkecentral.com">Bill Burke</a>
 * @version $Revision: 1 $
 */
public interface ResourceInvoker{
   CompletionStage<? extends Response> invoke(HttpRequest request,HttpResponse response);

   CompletionStage<? extends Response> invoke(HttpRequest request,HttpResponse response,Object target);

   Method getMethod();
}
