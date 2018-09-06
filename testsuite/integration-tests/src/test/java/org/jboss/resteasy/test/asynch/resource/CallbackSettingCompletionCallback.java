package org.jboss.resteasy.test.asynch.resource;

import javax.ws.rs.container.CompletionCallback;

public class CallbackSettingCompletionCallback implements CompletionCallback {

   public static final String NULL = "NULL";
   public static final String NONAME = "No name has been set yet";
   private static String throwableName;

   public static final String getLastThrowableName() {
      return throwableName;
   }

   public static final void resetLastThrowableName() {
      throwableName = NONAME;
   }

   @Override
   public void onComplete(Throwable throwable) {
      throwableName = throwable == null ? NULL : throwable.getClass()
              .getName();
   }

}
