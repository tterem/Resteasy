package org.jboss.resteasy.plugins.providers.jaxb.fastinfoset;

import com.sun.xml.fastinfoset.stax.StAXDocumentParser;
import org.jboss.resteasy.plugins.providers.fastinfoset.i18n.Messages;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

import javax.xml.bind.*;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.bind.attachment.AttachmentUnmarshaller;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import java.io.*;
import java.net.URL;

/**
 * @author <a href="mailto:bill@burkecentral.com">Bill Burke</a>
 * @version $Revision: 1 $
 */
public class FastinfoSetUnmarshaller implements Unmarshaller {
   private Unmarshaller unmarshaller;

   public FastinfoSetUnmarshaller(JAXBContext context) throws JAXBException {
      unmarshaller = context.createUnmarshaller();
   }

   protected static XMLStreamReader getFastinfoSetXMLStreamReader(InputStream entityStream) {
      InputStream in = new BufferedInputStream(entityStream, 2048);
      XMLStreamReader streamReader = new StAXDocumentParser(in);
      return streamReader;
   }

   public Object unmarshal(File file)
           throws JAXBException {
      try {
         return unmarshal(new FileInputStream(file));
      } catch (FileNotFoundException e) {
         throw new JAXBException(e);
      }
   }

   public Object unmarshal(InputStream inputStream)
           throws JAXBException {
      return unmarshal(getFastinfoSetXMLStreamReader(inputStream));
   }

   public Object unmarshal(Reader reader)
           throws JAXBException {
      throw new UnsupportedOperationException();
   }

   public Object unmarshal(URL url)
           throws JAXBException {
      try {
         return unmarshal(url.openStream());
      } catch (IOException e) {
         throw new JAXBException(e);
      }
   }

   public Object unmarshal(InputSource inputSource)
           throws JAXBException {
      throw new UnsupportedOperationException();
   }

   public Object unmarshal(Node node)
           throws JAXBException {
      throw new UnsupportedOperationException();
   }

   public <T> JAXBElement<T> unmarshal(Node node, Class<T> tClass)
           throws JAXBException {
      throw new UnsupportedOperationException();
   }

   public Object unmarshal(Source source)
           throws JAXBException {
      if (!(source instanceof StreamSource))
         throw new UnsupportedOperationException(Messages.MESSAGES.expectingStreamSource());
      StreamSource stream = (StreamSource) source;
      XMLStreamReader reader = getFastinfoSetXMLStreamReader(stream.getInputStream());
      return unmarshal(reader);
   }

   public <T> JAXBElement<T> unmarshal(Source source, Class<T> tClass)
           throws JAXBException {
      if (!(source instanceof StreamSource))
         throw new UnsupportedOperationException(Messages.MESSAGES.expectingStreamSource());
      StreamSource stream = (StreamSource) source;
      XMLStreamReader reader = getFastinfoSetXMLStreamReader(stream.getInputStream());
      return unmarshal(reader, tClass);
   }

   public Object unmarshal(XMLStreamReader xmlStreamReader)
           throws JAXBException {
      return unmarshaller.unmarshal(xmlStreamReader);
   }

   public <T> JAXBElement<T> unmarshal(XMLStreamReader xmlStreamReader, Class<T> tClass)
           throws JAXBException {
      return unmarshaller.unmarshal(xmlStreamReader, tClass);
   }

   public Object unmarshal(XMLEventReader xmlEventReader)
           throws JAXBException {
      throw new UnsupportedOperationException();
   }

   public <T> JAXBElement<T> unmarshal(XMLEventReader xmlEventReader, Class<T> tClass)
           throws JAXBException {
      throw new UnsupportedOperationException();
   }

   public UnmarshallerHandler getUnmarshallerHandler() {
      throw new UnsupportedOperationException();
   }

   @SuppressWarnings("deprecation")
   public boolean isValidating()
           throws JAXBException {
      return unmarshaller.isValidating();
   }

   @SuppressWarnings("deprecation")
   public void setValidating(boolean b)
           throws JAXBException {
      unmarshaller.setValidating(b);
   }

   public ValidationEventHandler getEventHandler()
           throws JAXBException {
      return unmarshaller.getEventHandler();
   }

   public void setEventHandler(ValidationEventHandler validationEventHandler)
           throws JAXBException {
      unmarshaller.setEventHandler(validationEventHandler);
   }

   public void setProperty(String s, Object o)
           throws PropertyException {
      unmarshaller.setProperty(s, o);
   }

   public Object getProperty(String s)
           throws PropertyException {
      return unmarshaller.getProperty(s);
   }

   public Schema getSchema() {
      return unmarshaller.getSchema();
   }

   public void setSchema(Schema schema) {
      unmarshaller.setSchema(schema);
   }

   public void setAdapter(XmlAdapter xmlAdapter) {
      unmarshaller.setAdapter(xmlAdapter);
   }

   public <A extends XmlAdapter> void setAdapter(Class<A> aClass, A a) {
      unmarshaller.setAdapter(aClass, a);
   }

   public <A extends XmlAdapter> A getAdapter(Class<A> aClass) {
      return unmarshaller.getAdapter(aClass);
   }

   public AttachmentUnmarshaller getAttachmentUnmarshaller() {
      return unmarshaller.getAttachmentUnmarshaller();
   }

   public void setAttachmentUnmarshaller(AttachmentUnmarshaller attachmentUnmarshaller) {
      unmarshaller.setAttachmentUnmarshaller(attachmentUnmarshaller);
   }

   public Listener getListener() {
      return unmarshaller.getListener();
   }

   public void setListener(Listener listener) {
      unmarshaller.setListener(listener);
   }
}