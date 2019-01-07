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
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyStore;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Client;

import static org.jboss.resteasy.test.ContainerConstants.SSL_CONTAINER_PORT_OFFSET_2;
import static org.jboss.resteasy.test.ContainerConstants.SSL_CONTAINER_QUALIFIER_2;

/**
 * @tpSubChapter Security
 * @tpChapter Integration tests
 * @tpTestCaseDetails Test for HostnameVerificationPolicy - STRICT
 * @tpSince RESTEasy 3.6.3.Final
 */
@RunWith(Arquillian.class)
@RunAsClient
public class HostnameVerificationPolicyStrictExceptionTest extends SSLTestBase {

   private static final Logger LOG = Logger.getLogger(HostnameVerificationPolicyStrictExceptionTest.class.getName());

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
      resteasyClientBuilder.setIsTrustSelfSignedCertificates(false); // otherwise, all self-signed certificates would be trusted by default
   }

   /**
    * @tpTestDetails Client has truststore containing self-signed certificate.
    * Server/endpoint is secured with the same self-signed certificate but server actual host address is not included among 'subject alternative names' in the certificate.
    * HostnameVerificationPolicy is set to STRICT so exception should be thrown.
    * @tpSince RESTEasy 3.6.3.Final
    */
   @Test(expected = ProcessingException.class)
   public void test() {
      client = resteasyClientBuilder.trustStore(trustStore).build();
      client.target(generateHttpsURL("/secure/hello", SSL_CONTAINER_PORT_OFFSET_2)).request().get();
   }

   @AfterClass
   public static void after() {
      client.close();
   }

}
