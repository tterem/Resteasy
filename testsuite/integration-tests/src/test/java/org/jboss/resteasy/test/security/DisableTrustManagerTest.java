package org.jboss.resteasy.test.security;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.container.test.api.TargetsContainer;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.logging.Logger;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.internal.ResteasyClientBuilderImpl;
import org.jboss.resteasy.test.security.resource.SslTestsResource;
import org.jboss.resteasy.utils.TestUtil;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyStore;
import javax.ws.rs.client.Client;
import javax.ws.rs.core.Response;

import static org.jboss.resteasy.test.ContainerConstants.SSL_CONTAINER_QUALIFIER;
import static org.jboss.resteasy.test.ContainerConstants.SSL_CONTAINER_PORT_OFFSET;

/**
 * @tpSubChapter Security
 * @tpChapter Integration tests
 * @tpTestCaseDetails Test for disableTrustManager() method
 * @tpSince RESTEasy 3.6.3.Final
 */
@RunWith(Arquillian.class)
@RunAsClient
public class DisableTrustManagerTest extends SSLTestBase {

   private static final Logger LOG = Logger.getLogger(DisableTrustManagerTest.class.getName());

   private static Client client;
   private static ResteasyClientBuilder resteasyClientBuilder = new ResteasyClientBuilderImpl();
   private static KeyStore trustStore;

   private static final String SERVER_KEYSTORE_PATH = RESOURCES + "/server.keystore";
   private static final String CLIENT_TRUSTSTORE_PATH = RESOURCES + "/client-different-cert.truststore";

   @TargetsContainer(SSL_CONTAINER_QUALIFIER)
   @Deployment(managed=false, name=DEPLOYMENT_NAME)
   public static Archive<?> createDeployment() {
      WebArchive war = TestUtil.prepareArchive(DEPLOYMENT_NAME);
      return TestUtil.finishContainerPrepare(war, null, SslTestsResource.class);
   }

   @Before
   public void startContainer() throws Exception {
      if (!containerController.isStarted(SSL_CONTAINER_QUALIFIER)) {
         containerController.start(SSL_CONTAINER_QUALIFIER);
         generateCertificates();
         secureServer(SERVER_KEYSTORE_PATH, SSL_CONTAINER_PORT_OFFSET);
      }
      deployer.deploy(DEPLOYMENT_NAME);

      trustStore = KeyStore.getInstance("jks");
      try (InputStream in = new FileInputStream(CLIENT_TRUSTSTORE_PATH)) {
         trustStore.load(in, PASSWORD.toCharArray());
      }
      resteasyClientBuilder.setIsTrustSelfSignedCertificates(false); // otherwise, all self-signed certificates would be trusted by default
   }

   /**
    * @tpTestDetails Client has truststore containing self-signed certificate.
    * Server/endpoint is secured with different self-signed certificate.
    * However, disableTrustManager is used so client should trust this certificate (and all others).
    * @tpSince RESTEasy 3.6.3.Final
    */
   @Test
   public void test() {
      resteasyClientBuilder = resteasyClientBuilder.disableTrustManager();

      client = resteasyClientBuilder.trustStore(trustStore).build();
      Response response = client.target(generateHttpsURL("/secure/hello", SSL_CONTAINER_PORT_OFFSET)).request().get();
      Assert.assertEquals("Hello World!", response.readEntity(String.class));
      Assert.assertEquals(200, response.getStatus());
   }

   @AfterClass
   public static void after() {
      client.close();
   }

}
