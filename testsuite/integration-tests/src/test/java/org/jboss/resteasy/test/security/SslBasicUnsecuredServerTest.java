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
import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.core.Response;

import static org.jboss.resteasy.test.ContainerConstants.DEFAULT_CONTAINER_QUALIFIER;

/**
 * @tpSubChapter Security
 * @tpChapter Integration tests
 * @tpTestCaseDetails Test for SSL - unsecured server
 * @tpSince RESTEasy 3.6.3.Final
 */
@RunWith(Arquillian.class)
@RunAsClient
public class SslBasicUnsecuredServerTest extends SSLTestBase {

   private static final Logger LOG = Logger.getLogger(SslBasicUnsecuredServerTest.class.getName());

   private static Client client;
   private static ResteasyClientBuilder resteasyClientBuilder = new ResteasyClientBuilderImpl();
   private static KeyStore trustStore;

   private static final String CLIENT_TRUSTSTORE_PATH = RESOURCES + "/client.truststore";

   @TargetsContainer(DEFAULT_CONTAINER_QUALIFIER)
   @Deployment(managed=false, name=DEPLOYMENT_NAME)
   public static Archive<?> createDeployment() {
      WebArchive war = TestUtil.prepareArchive(DEPLOYMENT_NAME);
      return TestUtil.finishContainerPrepare(war, null, SslTestsResource.class);
   }

   @Before
   public void startContainer() throws Exception {
      if (!containerController.isStarted(DEFAULT_CONTAINER_QUALIFIER)) {
         containerController.start(DEFAULT_CONTAINER_QUALIFIER);
         generateCertificates();
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
    * Server/endpoint is not secured at all. Client should not trust the unsecured server.
    * @tpSince RESTEasy 3.6.3.Final
    */
   @Test(expected = ProcessingException.class)
   public void test() {
      client = resteasyClientBuilder.trustStore(trustStore).build();
      Response response = client.target(generateHttpsURL("/secure/hello", 0)).request().get();
      Assert.assertEquals("Hello World!", response.readEntity(String.class));
      Assert.assertEquals(200, response.getStatus());
   }

   @AfterClass
   public static void after() {
      client.close();
   }

}
