package org.jboss.resteasy.test.i18n;

import org.jboss.logging.Logger;
import org.jboss.resteasy.plugins.servlet.i18n.Messages;
import org.junit.Assert;
import org.junit.Test;

import java.util.Locale;

/**
 * @author <a href="ron.sigal@jboss.com">Ron Sigal</a>
 * @version $Revision: 1.1 $
 * <p>
 * Copyright Aug 29, 2015
 */
abstract public class TestMessagesAbstract extends TestMessagesParent {
   protected static final String BASE = String.format("0%5s", Messages.BASE).substring(0, 4);
   private static final Logger LOG = Logger.getLogger(TestMessagesAbstract.class);

   @Test
   public void testLocale() throws Exception {
      Locale locale = getLocale();
      String filename = "org/jboss/resteasy/plugins/servlet/i18n/Messages.i18n_" + locale.toString() + ".properties";
      if (!before(locale, filename)) {
         LOG.info(getClass() + ": " + filename + " not found.");
         return;
      }

      Assert.assertEquals(getExpected(BASE + "00", "defaultApplicationNotImplemented"), Messages.MESSAGES.defaultApplicationNotImplemented());
   }

   @Override
   protected int getExpectedNumberOfMethods() {
      return Messages.class.getDeclaredMethods().length;
   }

   abstract protected Locale getLocale();
}
