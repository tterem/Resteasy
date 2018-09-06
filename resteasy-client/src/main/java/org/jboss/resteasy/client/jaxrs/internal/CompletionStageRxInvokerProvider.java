package org.jboss.resteasy.client.jaxrs.internal;

import javax.ws.rs.client.CompletionStageRxInvoker;
import javax.ws.rs.client.RxInvokerProvider;
import javax.ws.rs.client.SyncInvoker;
import java.util.concurrent.ExecutorService;

public class CompletionStageRxInvokerProvider implements RxInvokerProvider<CompletionStageRxInvoker> {
   @Override
   public boolean isProviderFor(Class<?> clazz) {
      return CompletionStageRxInvoker.class.equals(clazz);
   }

   @Override
   public CompletionStageRxInvoker getRxInvoker(SyncInvoker syncInvoker, ExecutorService executorService) {
      return new CompletionStageRxInvokerImpl(syncInvoker, executorService);
   }
}
