package org.jboss.resteasy.test.interceptor;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.logging.Logger;
import org.jboss.resteasy.test.interceptor.resource.AddDynamicFeature;
import org.jboss.resteasy.test.interceptor.resource.DynamicFeatureResource;
import org.jboss.resteasy.test.interceptor.resource.GreetingInterceptor;
import org.jboss.resteasy.utils.PortProviderUtil;
import org.jboss.resteasy.utils.TestUtil;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

/**
 * @tpSubChapter Resteasy-client
 * @tpChapter Integration tests
 * @tpTestCaseDetails Test for DynamicFeature
 * @tpSince RESTEasy 3.8.0
 */
@RunWith(Arquillian.class)
@RunAsClient
public class DynamicFeatureTest {

   private static final Logger LOG = Logger.getLogger(DynamicFeatureTest.class.getName());

   static Client client;

   @Deployment
   public static Archive<?> deploy() {
      WebArchive war = TestUtil.prepareArchive(DynamicFeatureTest.class.getSimpleName());
      war.addClasses(GreetingInterceptor.class);
      return TestUtil.finishContainerPrepare(war, null, DynamicFeatureResource.class, AddDynamicFeature.class);
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
    * @tpTestDetails Test dynamic feature.
    * @tpSince RESTEasy 3.8.0
    */
   @Test
   public void testDynamicFeature() {
      WebTarget target = client.target(PortProviderUtil.generateURL("/dynamic-feature/hello", DynamicFeatureTest.class.getSimpleName()));
      Entity<String> entity = Entity.entity( "Tomas", MediaType.TEXT_PLAIN);
      String response = target.request().post(entity, String.class);
      Assert.assertEquals("Hello Tomas !", response);
      LOG.info("Response: " + response);
   }
}
