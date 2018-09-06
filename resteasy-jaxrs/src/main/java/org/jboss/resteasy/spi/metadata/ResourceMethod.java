package org.jboss.resteasy.spi.metadata;

import javax.ws.rs.core.MediaType;
import java.util.Set;

/**
 * @author Christian Kaltepoth
 */
public interface ResourceMethod extends ResourceLocator {
   Set<String> getHttpMethods();

   MediaType[] getProduces();

   MediaType[] getConsumes();

   boolean isAsynchronous();

   void markAsynchronous();
}
