package org.jboss.resteasy.test.security;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jboss.arquillian.container.test.api.ContainerController;
import org.jboss.arquillian.container.test.api.Deployer;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.resteasy.utils.PortProviderUtil;
import org.jboss.resteasy.utils.TestUtil;
import org.wildfly.extras.creaper.core.online.OnlineManagementClient;
import org.wildfly.extras.creaper.core.online.operations.admin.Administration;

import java.io.File;
import java.io.IOException;

import static org.jboss.resteasy.utils.PortProviderUtil.isIpv6;

/**
 * Base class for SSl tests. Contains utility methods used in all ssl tests.
 */
public abstract class SSLTestBase {

   private static final Logger LOG = LogManager.getLogger(SSLTestBase.class);

   protected static String RESOURCES = "src/test/resources/org/jboss/resteasy/test/security/";
   protected static final String PASSWORD = "123456";
   protected static final String DEPLOYMENT_NAME = "ssl-war";

   private static final String GENERATE_CERTIFICATES_SCRIPT_UNIX = RESOURCES + "generate.key.sh";
   private static final String GENERATE_CERTIFICATES_SCRIPT_WINDOWS = RESOURCES + "generate.key.bat";
   protected static final String HOSTNAME = PortProviderUtil.getHost();
   private static final String WILDCARD_HOSTNAME = createWildcard(HOSTNAME);

   @ArquillianResource
   protected static ContainerController containerController;

   @ArquillianResource
   protected static Deployer deployer;

   /**
    * Generate a https URL with port, hostname
    *
    * @param path the path
    * @return a full https URL
    */
   protected static String generateHttpsURL(String path, int offset) {
      // ipv4
      if (!isIpv6()) {
         return String.format("https://%s:%d/%s%s", HOSTNAME, 8443 + offset, DEPLOYMENT_NAME, path);
      }
      // ipv6
      return String.format("https://[%s]:%d/%s%s", HOSTNAME, 8443 + offset, DEPLOYMENT_NAME, path);
   }

   /**
    * Set up ssl in jboss-cli so https endpoint can be accessed only if client trusts certificates in the server keystore.
    * @throws Exception
    */
   protected static void secureServer(String serverKeystorePath, int portOffset) throws Exception {
      File file = new File(serverKeystorePath);
      serverKeystorePath = file.getAbsolutePath();

      if (TestUtil.isWindows()) {
         serverKeystorePath = serverKeystorePath.replace("\\","\\\\");
      }

      OnlineManagementClient client = TestUtil.clientInit(portOffset);

      TestUtil.runCmd(client,"/core-service=management/security-realm=ApplicationRealm/server-identity=ssl:remove(keystore-path, keystore-password, alias)");
      TestUtil.runCmd(client, String.format("/core-service=management/security-realm=ApplicationRealm/server-identity=ssl:add(keystore-path=%s, keystore-password=%s, alias=\"server\")", serverKeystorePath, PASSWORD));

      // above changes request reload to take effect
      Administration admin = new Administration(client, 240);
      admin.reload();

      client.close();
   }

   /**
    * Run bash/batch script that generates private/public keys, certificates, keystores and truststores for both client and server.
    */
   protected static void generateCertificates() {
      File file = new File(RESOURCES);
      RESOURCES = file.getAbsolutePath();
      try {
         Process procBuildScript;
         if (TestUtil.isWindows()) {
            RESOURCES = RESOURCES.replace("\\","\\\\");
            procBuildScript = new ProcessBuilder(GENERATE_CERTIFICATES_SCRIPT_WINDOWS, RESOURCES, HOSTNAME).start();
         } else {
            procBuildScript = new ProcessBuilder(GENERATE_CERTIFICATES_SCRIPT_UNIX , RESOURCES, HOSTNAME).start();
         }
         procBuildScript.waitFor();
      } catch (IOException | InterruptedException e) {
         LOG.error("Certificate generation failed.", e);
      }
   }

   private static String createWildcard(String hostname) {
      String splitSymbol = PortProviderUtil.isIpv6() ? ":" : "\\.";
      String joinSymbol = PortProviderUtil.isIpv6() ? ":" : ".";
      String[] parts = hostname.split(splitSymbol);
      parts[1] = "*";
      String wildcard = String.join(joinSymbol, parts);
      return wildcard;
   }

}
