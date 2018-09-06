package org.jboss.resteasy.client.microprofile;

import javax.ws.rs.RuntimeType;
import javax.ws.rs.core.Configuration;
import javax.ws.rs.core.Feature;
import java.util.*;

class ConfigurationWrapper implements Configuration {

   private final Configuration delegate;
   protected Map<Class<?>, Map<Class<?>, Integer>> localClassContracts = new HashMap<>();

   ConfigurationWrapper(Configuration delegate) {
      this.delegate = delegate;
   }

   @Override
   public RuntimeType getRuntimeType() {
      return delegate.getRuntimeType();
   }

   @Override
   public Map<String, Object> getProperties() {
      return delegate.getProperties();
   }

   @Override
   public Object getProperty(String name) {
      return delegate.getProperty(name);
   }

   @Override
   public Collection<String> getPropertyNames() {
      return delegate.getPropertyNames();
   }

   @Override
   public boolean isEnabled(Feature feature) {
      return delegate.isEnabled(feature);
   }

   @Override
   public boolean isEnabled(Class<? extends Feature> featureClass) {
      return delegate.isEnabled(featureClass);
   }

   @Override
   public boolean isRegistered(Object component) {
      return delegate.isRegistered(component);
   }

   @Override
   public boolean isRegistered(Class<?> componentClass) {
      return delegate.isRegistered(componentClass);
   }

   @Override
   public Map<Class<?>, Integer> getContracts(Class<?> componentClass) {
      Map<Class<?>, Integer> contracts = new HashMap<>();
      contracts.putAll(getLocalContracts(componentClass));
      contracts.putAll(delegate.getContracts(componentClass));
      return contracts;
   }

   private Map<Class<?>, ? extends Integer> getLocalContracts(Class<?> componentClass) {
      if (localClassContracts.containsKey(componentClass)) {
         return localClassContracts.get(componentClass);
      } else {
         return Collections.emptyMap();
      }
   }

   @Override
   public Set<Class<?>> getClasses() {
      return delegate.getClasses();
   }

   @Override
   public Set<Object> getInstances() {
      return delegate.getInstances();
   }

   void registerLocalContract(Class<?> provider, Map<Class<?>, Integer> contracts) {
      localClassContracts.put(provider, contracts);
   }
}
