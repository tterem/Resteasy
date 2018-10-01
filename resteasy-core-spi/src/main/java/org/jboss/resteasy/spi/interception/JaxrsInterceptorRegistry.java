package org.jboss.resteasy.spi.interception;

import org.jboss.resteasy.spi.ResteasyProviderFactory;

import java.lang.reflect.AccessibleObject;
import java.util.Comparator;
import java.util.List;

public interface JaxrsInterceptorRegistry<T>{
   JaxrsInterceptorRegistry<T> clone(ResteasyProviderFactory factory);

   Class<T> getIntf();

   List<JaxrsInterceptorRegistryListener> getListeners();

   T[] preMatch();

   T[] postMatch(@SuppressWarnings("rawtypes") Class declaring,AccessibleObject target);

   void register(InterceptorFactory factory);

   void registerClass(Class<? extends T> declaring);

   void registerClass(Class<? extends T> declaring,int priority);

   void registerSingleton(T interceptor);

   void registerSingleton(T interceptor,int priority);

   public interface InterceptorFactory{
      Match preMatch();

      Match postMatch(@SuppressWarnings("rawtypes") Class declaring,AccessibleObject target);
   }

   class Match{
      public final Object interceptor;
      public final int order;

      public Match(final Object interceptor,final int order){
         this.interceptor=interceptor;
         this.order=order;
      }
   }

   class AscendingPrecedenceComparator implements Comparator<Match>{
      public int compare(Match match,Match match2){
         if(match.order<match2.order){
            return -1;
         }
         if(match.order==match2.order){
            return 0;
         }
         return 1;
      }
   }

   class DescendingPrecedenceComparator implements Comparator<Match>{
      public int compare(Match match,Match match2){
         if(match2.order<match.order){
            return -1;
         }
         if(match2.order==match.order){
            return 0;
         }
         return 1;
      }
   }
}
