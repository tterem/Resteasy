package org.jboss.resteasy.annotations.providers.jaxb.json;

import java.lang.annotation.*;

/**
 * A JSONConfig.
 *
 * @author <a href="ryan@damnhandy.com">Ryan J. McDonough</a>
 * @version $Revision:$
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(
        {ElementType.METHOD, ElementType.TYPE, ElementType.PARAMETER})
public @interface Mapped {
   /**
    * List of JSON attributes that should be regarded as Elements
    *
    * @return
    */
   String[] attributesAsElements() default {};

   /**
    * Map the XML namespace to a JSON namespace
    *
    * @return
    */
   XmlNsMap[] namespaceMap() default {};


}