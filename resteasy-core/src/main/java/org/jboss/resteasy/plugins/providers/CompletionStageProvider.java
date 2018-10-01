package org.jboss.resteasy.plugins.providers;

import org.jboss.resteasy.spi.AsyncResponseProvider;

import java.util.concurrent.CompletionStage;

public class CompletionStageProvider implements AsyncResponseProvider<CompletionStage<?>>{

   @SuppressWarnings("rawtypes")
   @Override
   public CompletionStage toCompletionStage(CompletionStage<?> asyncResponse){
      return asyncResponse;
   }

}
