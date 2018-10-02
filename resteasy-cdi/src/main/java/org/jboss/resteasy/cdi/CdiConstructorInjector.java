package org.jboss.resteasy.cdi;

import org.jboss.resteasy.cdi.i18n.LogMessages;
import org.jboss.resteasy.cdi.i18n.Messages;
import org.jboss.resteasy.spi.ApplicationException;
import org.jboss.resteasy.spi.ConstructorInjector;
import org.jboss.resteasy.spi.Failure;
import org.jboss.resteasy.spi.HttpRequest;
import org.jboss.resteasy.spi.HttpResponse;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.ws.rs.WebApplicationException;

import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * This ConstructorInjector implementation uses CDI's BeanManager to obtain
 * a contextual instance of a bean.
 * 
 * @author Jozef Hartinger
 *
 */
public class CdiConstructorInjector implements ConstructorInjector
{
   private BeanManager manager;
   private Type type;

   public CdiConstructorInjector(Type type, BeanManager manager)
   {
      this.type = type;
      this.manager = manager;
   }

   @Override
   public CompletionStage<Object> construct(boolean unwrapAsync)
   {
      Set<Bean<?>> beans = manager.getBeans(type);
      
      if (beans.size() > 1)
      {
         Set<Bean<?>> modifiableBeans = new HashSet<Bean<?>>();
         modifiableBeans.addAll(beans);
         // Ambiguous dependency may occur if a resource has subclasses
         // Therefore we remove those beans
         for (Iterator<Bean<?>> iterator = modifiableBeans.iterator(); iterator.hasNext();)
         {
            Bean<?> bean = iterator.next();
            if (!bean.getBeanClass().equals(type) && !bean.isAlternative())
            {
               // remove Beans that have clazz in their type closure but not as a base class
               iterator.remove(); 
            }
         }
         beans = modifiableBeans;
      }
      
      if (LogMessages.LOGGER.isDebugEnabled()) //keep this check for performance reasons, as toString() is expensive on CDI Bean
      {
         LogMessages.LOGGER.debug(Messages.MESSAGES.beansFound(type, beans));
      }
      
      Bean<?> bean = manager.resolve(beans);
      CreationalContext<?> context = manager.createCreationalContext(bean);
      return CompletableFuture.completedFuture(manager.getReference(bean, type, context));
   }

   @Override
   public CompletionStage<Object> construct(HttpRequest request, HttpResponse response, boolean unwrapAsync) throws Failure, WebApplicationException, ApplicationException
   {
      return construct(unwrapAsync);
   }

   @Override
   public CompletionStage<Object[]> injectableArguments(boolean unwrapAsync)
   {
      return CompletableFuture.completedFuture(new Object[0]);
   }

   @Override
   public CompletionStage<Object[]> injectableArguments(HttpRequest request, HttpResponse response, boolean unwrapAsync) throws Failure
   {
      return injectableArguments(unwrapAsync);
   }
}
