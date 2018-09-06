package org.jboss.resteasy.test.providers.jaxb.resource;

import org.jboss.resteasy.core.ExceptionAdapter;

import javax.xml.stream.*;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * A XmlStreamFactory.
 *
 * @author <a href="ryan@damnhandy.com">Ryan J. McDonough</a>
 * @version $Revision:$
 */
public final class XmlStreamFactory {

   private XmlStreamFactory() {

   }

   public static XMLStreamWriter getXMLStreamWriter(OutputStream out) {
      try {
         return XMLOutputFactory.newInstance().createXMLStreamWriter(out);
      } catch (XMLStreamException e) {
         throw new ExceptionAdapter(e);
      }
   }

   static XMLStreamReader getXMLStreamReader(InputStream entityStream) {
      InputStream in = new BufferedInputStream(entityStream, 2048);
      try {
         XMLInputFactory factory = XMLInputFactory.newInstance();
         return factory.createXMLStreamReader(in);
      } catch (XMLStreamException e) {
         throw new ExceptionAdapter(e);
      }
   }
}
