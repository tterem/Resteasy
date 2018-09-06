package org.jboss.resteasy.rxjava2;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Observable;
import org.jboss.resteasy.spi.AsyncStreamProvider;
import org.reactivestreams.Publisher;

import javax.ws.rs.ext.Provider;

@Provider
public class ObservableProvider implements AsyncStreamProvider<Observable<?>> {
   @Override
   public Publisher<?> toAsyncStream(Observable<?> asyncResponse) {
      return asyncResponse.toFlowable(BackpressureStrategy.BUFFER);
   }

}
