package org.jboss.resteasy.rxjava2;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;

import javax.ws.rs.client.RxInvoker;

public interface FlowableRxInvoker extends RxInvoker<Flowable<?>> {
   BackpressureStrategy getBackpressureStrategy();

   void setBackpressureStrategy(BackpressureStrategy backpressureStrategy);
}
