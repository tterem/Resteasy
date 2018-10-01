package org.jboss.resteasy.test.response;

import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import org.jboss.resteasy.spi.AsyncResponseProvider;

import javax.ws.rs.ext.Provider;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@Provider
public class SingleProvider implements AsyncResponseProvider<Single<?>>{

   @Override
   public CompletionStage toCompletionStage(Single<?> asyncResponse){
      return new SingleAdaptor<>(asyncResponse);
   }

   private static class SingleAdaptor<T> extends CompletableFuture<T>{
      private Disposable subscription;

      SingleAdaptor(Single<T> observable){
         this.subscription=observable.subscribe(this::complete,this::completeExceptionally);
      }

      @Override
      public boolean cancel(boolean mayInterruptIfRunning){
         subscription.dispose();
         return super.cancel(mayInterruptIfRunning);
      }
   }

}
