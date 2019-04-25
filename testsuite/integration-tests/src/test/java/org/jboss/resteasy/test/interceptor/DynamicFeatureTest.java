package org.jboss.resteasy.test.interceptor;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.logging.Logger;
import org.jboss.resteasy.test.interceptor.resource.AddDynamicFeature;
import org.jboss.resteasy.test.interceptor.resource.DynamicFeatureApplication;
import org.jboss.resteasy.test.interceptor.resource.DynamicFeatureResource;
import org.jboss.resteasy.test.interceptor.resource.MakeItDoubleInterceptor;
import org.jboss.resteasy.utils.PermissionUtil;
import org.jboss.resteasy.utils.PortProviderUtil;
import org.jboss.resteasy.utils.TestUtil;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import java.lang.reflect.ReflectPermission;
import java.util.PropertyPermission;

/**
 * @tpSubChapter Resteasy-client
 * @tpChapter Integration tests
 * @tpTestCaseDetails Regression test for RESTEASY-1083
 * @tpSince RESTEasy 3.0.16
 */
@RunWith(Arquillian.class)
@RunAsClient
public class DynamicFeatureTest {

   protected static final Logger LOG = Logger.getLogger(DynamicFeatureTest.class.getName());

   static Client client;

   /**
    * Test needs to be run on deployment.
    * @return
    */
   @Deployment
   public static Archive<?> deploy() {
      WebArchive war = TestUtil.prepareArchive(DynamicFeatureTest.class.getSimpleName());
      war.addAsWebInfResource(DynamicFeatureTest.class.getPackage(), "DynamicFeatureWeb.xml", "web.xml");
      war.addClasses(AddDynamicFeature.class,
            DynamicFeatureApplication.class,
            MakeItDoubleInterceptor.class);
      // Arquillian in the deployment
      war.addAsManifestResource(PermissionUtil.createPermissionsXmlAsset(
            new ReflectPermission("suppressAccessChecks"),
            new RuntimePermission("accessDeclaredMembers"),
            new PropertyPermission("arquillian.*", "read")), "permissions.xml");
      return TestUtil.finishContainerPrepare(war, null, DynamicFeatureResource.class);
   }

   @BeforeClass
   public static void setup() {
      client = ClientBuilder.newClient();
   }

   @AfterClass
   public static void cleanup() {
      client.close();
   }

   /**
    * @tpTestDetails Check dynamic feature.
    * @tpSince RESTEasy 3.7
    */
   @Test
   public void testDynamicFeature() {
      WebTarget target = client.target(PortProviderUtil.generateURL("/dynamic/feature/hello", DynamicFeatureTest.class.getSimpleName()));
      Entity<String> entity = Entity.entity("0", MediaType.TEXT_PLAIN);
      String responseText = target.request().post(entity, String.class);
      LOG.info("Response: " + responseText);
   }
}
