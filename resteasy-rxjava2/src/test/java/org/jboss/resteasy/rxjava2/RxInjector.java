package org.jboss.resteasy.rxjava2;

import io.reactivex.Single;
import org.jboss.resteasy.spi.ContextInjector;

import javax.ws.rs.ext.Provider;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;


@Provider
public class RxInjector implements ContextInjector<Single<Integer>,Integer>{

   @Override
   public Single<Integer> resolve(Class<? extends Single<Integer>> rawType,Type genericType,
                                  Annotation[] annotations){
      boolean async=false;
      for(Annotation annotation : annotations){
         if(annotation.annotationType()==Async.class)
            async=true;
      }
      if(!async)
         return Single.just(42);
      return Single.create(emitter->{
         new Thread(()->{
            try{
               Thread.sleep(1000);
            }catch(InterruptedException e){
               emitter.onError(e);
               return;
            }
            emitter.onSuccess(42);
         }).start();
      });
   }

}
