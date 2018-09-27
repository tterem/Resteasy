package org.jboss.resteasy.test.spring.deployment.resource;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Collection;

@XmlRootElement
public class Contacts {

   private Collection<Contact> contacts;

   public Contacts() {
      this.contacts = new ArrayList<Contact>();
   }

   public Contacts(final Collection<Contact> contacts) {
      this.contacts = contacts;
   }

   @XmlElement(name = "contacts")
   public Collection<Contact> getContacts() {
      return contacts;
   }

   public void setContacts(Collection<Contact> contact) {
      this.contacts = contact;
   }


}
