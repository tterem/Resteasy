package org.jboss.resteasy.rxjava2;

import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import org.jboss.resteasy.spi.AsyncClientResponseProvider;
import org.jboss.resteasy.spi.AsyncResponseProvider;

import javax.ws.rs.ext.Provider;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@Provider
public class SingleProvider implements AsyncResponseProvider<Single<?>>, AsyncClientResponseProvider<Single<?>>{
   @Override
   public CompletionStage<?> toCompletionStage(Single<?> asyncResponse){
      return new SingleAdaptor<>(asyncResponse);
   }

   @Override
   public Single<?> fromCompletionStage(CompletionStage<?> completionStage){
      return Single.fromFuture(completionStage.toCompletableFuture());
   }

   private static class SingleAdaptor<T> extends CompletableFuture<T>{
      private Disposable subscription;

      SingleAdaptor(Single<T> single){
         this.subscription=single.subscribe(this::complete,this::completeExceptionally);
      }

      @Override
      public boolean cancel(boolean mayInterruptIfRunning){
         subscription.dispose();
         return super.cancel(mayInterruptIfRunning);
      }
   }
}
