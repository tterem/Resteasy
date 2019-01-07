package org.jboss.resteasy.test;

/**
 * Various contants for containers defined in arquillian.xml. Keep this file in sync with the values there.
 */
public class ContainerConstants {

   public static final String DEFAULT_CONTAINER_QUALIFIER = "jbossas-managed";

   public static final String GZIP_CONTAINER_QUALIFIER = "jbossas-manual-gzip";

   public static final int GZIP_CONTAINER_PORT_OFFSET = 1000;

   public static final String TRACING_CONTAINER_QUALIFIER = "jbossas-manual-tracing";

   public static final int TRACING_CONTAINER_PORT_OFFSET = 2000;

   public static final String SSL_CONTAINER_QUALIFIER = "jbossas-manual-ssl";

   public static final int SSL_CONTAINER_PORT_OFFSET = 3000;

   public static final String SSL_CONTAINER_QUALIFIER_2 = "jbossas-manual-ssl2";

   public static final int SSL_CONTAINER_PORT_OFFSET_2 = 4000;

}
