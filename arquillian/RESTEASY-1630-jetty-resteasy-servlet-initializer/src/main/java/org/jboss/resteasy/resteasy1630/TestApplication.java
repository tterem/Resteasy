package org.jboss.resteasy.resteasy1630;

import javax.ws.rs.core.Application;
import javax.ws.rs.ext.Provider;
import java.util.HashSet;
import java.util.Set;

@Provider
public class TestApplication extends Application{
   public Set<Class<?>> getClasses(){
      HashSet<Class<?>> classes=new HashSet<Class<?>>();
      classes.add(TestResource.class);
      return classes;
   }
}
