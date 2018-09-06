//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.06.18 at 10:10:02 PM CST 
//


package org.jboss.resteasy.wadl.jaxb;

import org.w3c.dom.Element;

import javax.xml.bind.annotation.*;
import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * <p>Java class for anonymous complex type.
 * <p>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;any processContents='lax' namespace='##other' maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="title" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute ref="{http://www.w3.org/XML/1998/namespace}lang"/&gt;
 *       &lt;anyAttribute processContents='lax' namespace='##other'/&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "content"
})
@XmlRootElement(name = "doc")
public class Doc {

   @XmlMixed
   @XmlAnyElement(lax = true)
   protected List<Object> content;
   @XmlAttribute(name = "title")
   protected String title;
   @XmlAttribute(name = "lang", namespace = "http://www.w3.org/XML/1998/namespace")
   protected String lang;
   @XmlAnyAttribute
   private Map<QName, String> otherAttributes = new HashMap<QName, String>();

   /**
    * Gets the value of the content property.
    * <p>
    * <p>
    * This accessor method returns a reference to the live list,
    * not a snapshot. Therefore any modification you make to the
    * returned list will be present inside the JAXB object.
    * This is why there is not a <CODE>set</CODE> method for the content property.
    * <p>
    * <p>
    * For example, to add a new item, do as follows:
    * <pre>
    *    getContent().add(newItem);
    * </pre>
    * <p>
    * <p>
    * <p>
    * Objects of the following type(s) are allowed in the list
    * {@link Element }
    * {@link String }
    * {@link Object }
    */
   public List<Object> getContent() {
      if (content == null) {
         content = new ArrayList<Object>();
      }
      return this.content;
   }

   /**
    * Gets the value of the title property.
    *
    * @return possible object is
    * {@link String }
    */
   public String getTitle() {
      return title;
   }

   /**
    * Sets the value of the title property.
    *
    * @param value allowed object is
    *              {@link String }
    */
   public void setTitle(String value) {
      this.title = value;
   }

   /**
    * Gets the value of the lang property.
    *
    * @return possible object is
    * {@link String }
    */
   public String getLang() {
      return lang;
   }

   /**
    * Sets the value of the lang property.
    *
    * @param value allowed object is
    *              {@link String }
    */
   public void setLang(String value) {
      this.lang = value;
   }

   /**
    * Gets a map that contains attributes that aren't bound to any typed property on this class.
    * <p>
    * <p>
    * the map is keyed by the name of the attribute and
    * the value is the string value of the attribute.
    * <p>
    * the map returned by this method is live, and you can add new attribute
    * by updating the map directly. Because of this design, there's no setter.
    *
    * @return always non-null
    */
   public Map<QName, String> getOtherAttributes() {
      return otherAttributes;
   }

}
