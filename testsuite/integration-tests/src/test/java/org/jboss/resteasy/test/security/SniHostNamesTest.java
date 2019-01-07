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

import static org.jboss.resteasy.test.ContainerConstants.SSL_CONTAINER_PORT_OFFSET_2;
import static org.jboss.resteasy.test.ContainerConstants.SSL_CONTAINER_QUALIFIER_2;

/**
 * @tpSubChapter Security
 * @tpChapter Integration tests
 * @tpTestCaseDetails Test for sniHostNames - method which sets trustworthy sni host names in ResteasyClientBuilder
 * @tpSince RESTEasy 3.6.3.Final
 */
@RunWith(Arquillian.class)
@RunAsClient
public class SniHostNamesTest extends SSLTestBase { // still failing

   private static final Logger LOG = Logger.getLogger(SniHostNamesTest.class.getName());

   private static Client client;
   private static ResteasyClientBuilder resteasyClientBuilder = new ResteasyClientBuilderImpl();
   private static KeyStore trustStore;

   private static final String SERVER_KEYSTORE_PATH = RESOURCES + "/server-wrong-hostname.keystore";
   private static final String CLIENT_TRUSTSTORE_PATH = RESOURCES + "/client-wrong-hostname.truststore";

   @TargetsContainer(SSL_CONTAINER_QUALIFIER_2)
   @Deployment(managed=false, name=DEPLOYMENT_NAME)
   public static Archive<?> createDeployment() {
      WebArchive war = TestUtil.prepareArchive(DEPLOYMENT_NAME);
      return TestUtil.finishContainerPrepare(war, null, SslTestsResource.class);
   }

   @Before
   public void startContainer() throws Exception {
      if (!containerController.isStarted(SSL_CONTAINER_QUALIFIER_2)) {
         containerController.start(SSL_CONTAINER_QUALIFIER_2);
         generateCertificates();
         secureServer(SERVER_KEYSTORE_PATH, SSL_CONTAINER_PORT_OFFSET_2);
      }
      deployer.deploy(DEPLOYMENT_NAME);

      trustStore = KeyStore.getInstance("jks");
      try (InputStream in = new FileInputStream(CLIENT_TRUSTSTORE_PATH)) {
         trustStore.load(in, PASSWORD.toCharArray());
      }
      resteasyClientBuilder.hostnameVerification(ResteasyClientBuilder.HostnameVerificationPolicy.STRICT);
   }

   /**
    * @tpTestDetails Client has truststore containing self-signed certificate.
    * Server/endpoint is secured with the same self-signed certificate but server actual host address is not included among 'subject alternative names' in the certificate.
    * Instead it was generated for random address: 199.111.68.7.
    * However, sniHostNames method is used so client should trust this certificate.
    * @tpSince RESTEasy 3.6.3.Final
    */
   @Test
   public void test() { // still failing
      resteasyClientBuilder = resteasyClientBuilder.sniHostNames("abcd", "efgh"); // not sure what argument to put here and if it has some effect at all

      client = resteasyClientBuilder.trustStore(trustStore).build();
      Response response = client.target(generateHttpsURL("/secure/hello", SSL_CONTAINER_PORT_OFFSET_2)).request().get();
      Assert.assertEquals("Hello World!", response.readEntity(String.class));
      Assert.assertEquals(200, response.getStatus());
   }

   @AfterClass
   public static void after() {
      client.close();
   }

}
