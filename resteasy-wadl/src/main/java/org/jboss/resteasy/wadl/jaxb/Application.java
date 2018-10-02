//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.06.18 at 10:10:02 PM CST 
//


package org.jboss.resteasy.wadl.jaxb;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.w3c.dom.Element;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{http://wadl.dev.java.net/2009/02}doc" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{http://wadl.dev.java.net/2009/02}grammars" minOccurs="0"/&gt;
 *         &lt;element ref="{http://wadl.dev.java.net/2009/02}resources" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;choice maxOccurs="unbounded" minOccurs="0"&gt;
 *           &lt;element ref="{http://wadl.dev.java.net/2009/02}resource_type"/&gt;
 *           &lt;element ref="{http://wadl.dev.java.net/2009/02}method"/&gt;
 *           &lt;element ref="{http://wadl.dev.java.net/2009/02}representation"/&gt;
 *           &lt;element ref="{http://wadl.dev.java.net/2009/02}param"/&gt;
 *         &lt;/choice&gt;
 *         &lt;any processContents='lax' namespace='##other' maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
   "doc",
   "grammars",
   "resources",
   "resourceTypeOrMethodOrRepresentation",
   "any"
})
@XmlRootElement(name = "application")
public class Application {

   protected List<Doc> doc;
   protected Grammars grammars;
   protected List<Resources> resources;
   @XmlElements({
      @XmlElement(name = "resource_type", type = ResourceType.class),
      @XmlElement(name = "method", type = Method.class),
      @XmlElement(name = "representation", type = Representation.class),
      @XmlElement(name = "param", type = Param.class)
   })
   protected List<Object> resourceTypeOrMethodOrRepresentation;
   @XmlAnyElement(lax = true)
   protected List<Object> any;

   /**
    * Gets the value of the doc property.
    * 
    * <p>
    * This accessor method returns a reference to the live list,
    * not a snapshot. Therefore any modification you make to the
    * returned list will be present inside the JAXB object.
    * This is why there is not a <CODE>set</CODE> method for the doc property.
    * 
    * <p>
    * For example, to add a new item, do as follows:
    * <pre>
    *    getDoc().add(newItem);
    * </pre>
    * 
    * 
    * <p>
    * Objects of the following type(s) are allowed in the list
    * {@link Doc }
    * 
    * 
    */
   public List<Doc> getDoc() {
      if (doc == null) {
         doc = new ArrayList<Doc>();
      }
      return this.doc;
   }

   /**
    * Gets the value of the grammars property.
    * 
    * @return
    *     possible object is
    *     {@link Grammars }
    *     
    */
   public Grammars getGrammars() {
      return grammars;
   }

   /**
    * Sets the value of the grammars property.
    * 
    * @param value
    *     allowed object is
    *     {@link Grammars }
    *     
    */
   public void setGrammars(Grammars value) {
      this.grammars = value;
   }

   /**
    * Gets the value of the resources property.
    * 
    * <p>
    * This accessor method returns a reference to the live list,
    * not a snapshot. Therefore any modification you make to the
    * returned list will be present inside the JAXB object.
    * This is why there is not a <CODE>set</CODE> method for the resources property.
    * 
    * <p>
    * For example, to add a new item, do as follows:
    * <pre>
    *    getResources().add(newItem);
    * </pre>
    * 
    * 
    * <p>
    * Objects of the following type(s) are allowed in the list
    * {@link Resources }
    * 
    * 
    */
   public List<Resources> getResources() {
      if (resources == null) {
         resources = new ArrayList<Resources>();
      }
      return this.resources;
   }

   /**
    * Gets the value of the resourceTypeOrMethodOrRepresentation property.
    * 
    * <p>
    * This accessor method returns a reference to the live list,
    * not a snapshot. Therefore any modification you make to the
    * returned list will be present inside the JAXB object.
    * This is why there is not a <CODE>set</CODE> method for the resourceTypeOrMethodOrRepresentation property.
    * 
    * <p>
    * For example, to add a new item, do as follows:
    * <pre>
    *    getResourceTypeOrMethodOrRepresentation().add(newItem);
    * </pre>
    * 
    * 
    * <p>
    * Objects of the following type(s) are allowed in the list
    * {@link ResourceType }
    * {@link Method }
    * {@link Representation }
    * {@link Param }
    * 
    * 
    */
   public List<Object> getResourceTypeOrMethodOrRepresentation() {
      if (resourceTypeOrMethodOrRepresentation == null) {
         resourceTypeOrMethodOrRepresentation = new ArrayList<Object>();
      }
      return this.resourceTypeOrMethodOrRepresentation;
   }

   /**
    * Gets the value of the any property.
    * 
    * <p>
    * This accessor method returns a reference to the live list,
    * not a snapshot. Therefore any modification you make to the
    * returned list will be present inside the JAXB object.
    * This is why there is not a <CODE>set</CODE> method for the any property.
    * 
    * <p>
    * For example, to add a new item, do as follows:
    * <pre>
    *    getAny().add(newItem);
    * </pre>
    * 
    * 
    * <p>
    * Objects of the following type(s) are allowed in the list
    * {@link Element }
    * {@link Object }
    * 
    * 
    */
   public List<Object> getAny() {
      if (any == null) {
         any = new ArrayList<Object>();
      }
      return this.any;
   }

}
