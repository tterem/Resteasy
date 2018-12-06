package org.jboss.resteasy.test.security;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.logging.Logger;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.internal.ResteasyClientBuilderImpl;
import org.jboss.resteasy.test.security.resource.IsTrustSelfSignedCertificatesResource;
import org.jboss.resteasy.utils.PortProviderUtil;
import org.jboss.resteasy.utils.TestUtil;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.wildfly.extras.creaper.core.online.OnlineManagementClient;
import org.wildfly.extras.creaper.core.online.operations.admin.Administration;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import javax.net.ssl.HostnameVerifier;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.security.cert.CertificateException;


/**
 * @tpSubChapter Security
 * @tpChapter Integration tests
 * @tpTestCaseDetails Test for https://issues.jboss.org/browse/RESTEASY-2065.
 * @tpSince RESTEasy 3.6.3.Final
 */
@RunWith(Arquillian.class)
@RunAsClient
public class IsTrustSelfSignedCertificatesTest {

   protected static final Logger LOG = Logger.getLogger(IsTrustSelfSignedCertificatesTest.class.getName());

   private static Client client;
   private static ResteasyClientBuilder resteasyClientBuilder;
   private static KeyStore keyStore;

   private static String SERVER_UNTRUSTED_KEYSTORE_PATH = "src/test/resources/org/jboss/resteasy/test/security/untrusted.keystore";
   private static final String CLIENT_TRUSTSTORE_PATH = "src/test/resources/org/jboss/resteasy/test/security/client.truststore";
   private static final String PASSWORD = "123456";

   @Deployment
   public static Archive<?> deployDefault() throws Exception {
      secureServer();
      WebArchive war = TestUtil.prepareArchive("war_default");
      war.addClass(IsTrustSelfSignedCertificatesResource.class);
      return TestUtil.finishContainerPrepare(war, null, IsTrustSelfSignedCertificatesResource.class);
   }

   @BeforeClass
   public static void init() throws KeyStoreException, NoSuchAlgorithmException, IOException, CertificateException{
      backupDefaultConfiguration();
      keyStore = KeyStore.getInstance("jks");
      try (InputStream in = new FileInputStream(CLIENT_TRUSTSTORE_PATH)) {
         keyStore.load(in, PASSWORD.toCharArray());
      }
      resteasyClientBuilder = new ResteasyClientBuilderImpl();

      // jenkins nodes have different IPs/hostnames so accept all to prevent:
      // javax.net.ssl.SSLPeerUnverifiedException: Certificate for <hostname> doesn't match any of the subject alternative names: <names>
      HostnameVerifier acceptAll = (hostname, session) -> true;
      resteasyClientBuilder.hostnameVerifier(acceptAll);
   }

   @AfterClass
   public static void after() throws Exception {
      resetConfiguration();
      client.close();
   }


   /**
    * @tpTestDetails Client has truststore containing self-signed certificate.
    * Server/endpoint is secured with DIFFERENT self-signed certificate, but by default, client should trust all self-signed certificates.
    * @tpSince RESTEasy 3.6.3.Final
    */
   @Test
   public void testDefault() {
      client = resteasyClientBuilder.trustStore(keyStore).build();

      Response response = client.target("https://" + PortProviderUtil.getHost() + ":8443/war_default/certificate/hello").request().get();

      Assert.assertEquals("Hello World!", response.readEntity(String.class));
      Assert.assertEquals(200, response.getStatus());
   }

   /**
    * @tpTestDetails Client has truststore containing self-signed certificate.
    * Server/endpoint is secured with DIFFERENT self-signed certificate, but client should trust that certificate if trustSelfSignedCertificates is true.
    * @tpSince RESTEasy 3.6.3.Final
    */
   @Test
   public void testTrue() {
      resteasyClientBuilder.setIsTrustSelfSignedCertificates(true);
      client = resteasyClientBuilder.trustStore(keyStore).build();

      Response response = client.target("https://" + PortProviderUtil.getHost() + ":8443/war_default/certificate/hello").request().get();

      Assert.assertEquals("Hello World!", response.readEntity(String.class));
      Assert.assertEquals(200, response.getStatus());
   }

   /**
    * @tpTestDetails Client has truststore containing self-signed certificate.
    * Server/endpoint is secured with DIFFERENT self-signed certificate and client should not trust that certificate if trustSelfSignedCertificates is false.
    * @tpSince RESTEasy 3.6.3.Final
    */
   @Test(expected = ProcessingException.class)
   public void testFalse() {
      resteasyClientBuilder.setIsTrustSelfSignedCertificates(false);
      client = resteasyClientBuilder.trustStore(keyStore).build();

      client.target("https://" + PortProviderUtil.getHost() + ":8443/war_default/certificate/hello").request().get();
   }

   /**
    * Set up ssl in jboss-cli so https endpoint can be accessed only if client trusts certificates in the server keystore.
    * @throws Exception
    */
   private static void secureServer() throws Exception {
      File file = new File(SERVER_UNTRUSTED_KEYSTORE_PATH);
      SERVER_UNTRUSTED_KEYSTORE_PATH = file.getAbsolutePath();

      OnlineManagementClient client = TestUtil.clientInit();

      TestUtil.runCmd(client,"/core-service=management/security-realm=ApplicationRealm/server-identity=ssl:remove(keystore-path, keystore-password, alias)");
      TestUtil.runCmd(client, String.format("/core-service=management/security-realm=ApplicationRealm/server-identity=ssl:add(keystore-path=%s, keystore-password=%s, alias=\"cn=untrusted\")", SERVER_UNTRUSTED_KEYSTORE_PATH, PASSWORD));

      // above changes request reload to take effect
      Administration admin = new Administration(client, 240);
      admin.reload();

      client.close();
   }

   private static void backupDefaultConfiguration() {
      Path source = Paths.get(TestUtil.getJbossHome(), "standalone", "configuration", "standalone.xml");
      Path destination = Paths.get(TestUtil.getJbossHome(), "standalone", "configuration", "backup.xml");
      if (Files.exists(source)) {
            try {
               Files.copy(source, destination);
            } catch (IOException e) {
               LOG.error("Error:", e);
            }
      } else {
         LOG.error("Standalone.xml not found!");
      }

   }

   private static void resetConfiguration() throws Exception {
      Path source = Paths.get(TestUtil.getJbossHome(), "standalone", "configuration", "backup.xml");
      Path destination = Paths.get(TestUtil.getJbossHome(), "standalone", "configuration", "standalone.xml");
      if (Files.exists(source)) {
         try {
            Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);
            Files.delete(source);
         } catch (IOException e) {
            LOG.error("Error:", e);
         }
      } else {
         LOG.error("Backup.xml not found!");
      }

      // reload server
      OnlineManagementClient client = TestUtil.clientInit();
      Administration admin = new Administration(client, 240);
      admin.reload();
      client.close();
   }
}
