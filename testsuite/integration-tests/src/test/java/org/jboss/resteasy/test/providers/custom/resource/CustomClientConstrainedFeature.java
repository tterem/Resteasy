package org.jboss.resteasy.test.providers.custom.resource;

import org.jboss.logging.Logger;

import javax.ws.rs.ConstrainedTo;
import javax.ws.rs.RuntimeType;
import javax.ws.rs.core.Feature;
import javax.ws.rs.core.FeatureContext;
import javax.ws.rs.ext.Provider;

/**
 * A feature constrained to the client runtime.
 * @author pjurak
 */
@Provider
@ConstrainedTo(RuntimeType.CLIENT)
public class CustomClientConstrainedFeature implements Feature{
   private static volatile boolean invoked=false;
   private Logger logger=Logger.getLogger(CustomClientConstrainedFeature.class);

   public static boolean wasInvoked(){
      return invoked;
   }

   public static void reset(){
      invoked=false;
   }

   @Override
   public boolean configure(FeatureContext context){
      logger.info("Configuring CustomClientConstrainedFeature");
      invoked=true;
      return true;
   }
}
