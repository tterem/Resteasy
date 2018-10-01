package org.jboss.resteasy.rxjava2;

import io.reactivex.Flowable;
import org.jboss.resteasy.spi.AsyncStreamProvider;
import org.reactivestreams.Publisher;

import javax.ws.rs.ext.Provider;

@Provider
public class FlowableProvider implements AsyncStreamProvider<Flowable<?>>{
   @Override
   public Publisher<?> toAsyncStream(Flowable<?> asyncResponse){
      return asyncResponse;
   }

}
