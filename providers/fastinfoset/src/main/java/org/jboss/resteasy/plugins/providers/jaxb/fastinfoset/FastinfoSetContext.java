package org.jboss.resteasy.plugins.providers.jaxb.fastinfoset;

import javax.xml.bind.*;

/**
 * @author <a href="mailto:bill@burkecentral.com">Bill Burke</a>
 * @version $Revision: 1 $
 */
@SuppressWarnings("deprecation")
public class FastinfoSetContext extends JAXBContext {
   private JAXBContext context;

   public FastinfoSetContext(Class... clazz) {
      try {
         context = JAXBContext.newInstance(clazz);
      } catch (JAXBException e) {
         throw new RuntimeException(e);
      }
   }

   public FastinfoSetContext(String contextPath) {
      try {
         context = JAXBContext.newInstance(contextPath);
      } catch (JAXBException e) {
         throw new RuntimeException(e);
      }
   }

   public Unmarshaller createUnmarshaller() throws JAXBException {
      return new FastinfoSetUnmarshaller(context);
   }

   public Marshaller createMarshaller() throws JAXBException {
      return new FastinfoSetMarshaller(context);
   }

   public Validator createValidator() throws JAXBException {
      return context.createValidator();
   }


}