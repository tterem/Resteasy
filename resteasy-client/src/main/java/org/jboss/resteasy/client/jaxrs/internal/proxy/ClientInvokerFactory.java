package org.jboss.resteasy.client.jaxrs.internal.proxy;

import org.jboss.resteasy.client.jaxrs.ProxyConfig;

import java.lang.reflect.Method;

public interface ClientInvokerFactory {
   ClientInvoker createClientInvoker(Class<?> clazz, Method method, ProxyConfig config);
}
