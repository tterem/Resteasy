package org.jboss.resteasy.test.security;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Hashtable;

import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.as.arquillian.api.ServerSetup;
import org.jboss.resteasy.category.NotForForwardCompatibility;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.engines.ApacheHttpClientEngine;
import org.jboss.resteasy.setup.AbstractUsersRolesSecurityDomainSetup;
import org.jboss.resteasy.spi.HttpResponseCodes;
import org.jboss.resteasy.test.security.resource.BasicAuthBaseProxy;
import org.jboss.resteasy.test.security.resource.BasicAuthBaseResource;
import org.jboss.resteasy.test.security.resource.BasicAuthBaseResourceAnybody;
import org.jboss.resteasy.test.security.resource.BasicAuthBaseResourceMoreSecured;
import org.jboss.resteasy.utils.PortProviderUtil;
import org.jboss.resteasy.utils.TestUtil;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;

/**
 * @tpSubChapter Security
 * @tpChapter Integration tests
 * @tpTestCaseDetails Basic test for RESTEasy authentication.
 * @tpSince RESTEasy 3.0.16
 */
@ServerSetup({BasicAuthTest.SecurityDomainSetup.class})
@RunWith(Arquillian.class)
@RunAsClient
public class BasicAuthTest {

   private static final String WRONG_RESPONSE = "Wrong response content.";
   private static final String ACCESS_FORBIDDEN_MESSAGE = "Access forbidden: role not allowed";

   private static ResteasyClient authorizedClient;
   private static ResteasyClient unauthorizedClient;
   private static ResteasyClient noAutorizationClient;

   @BeforeClass
   public static void init() {
      // authorizedClient
      {
         UsernamePasswordCredentials credentials = new UsernamePasswordCredentials("bill", "password1");
         CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
         credentialsProvider.setCredentials(new AuthScope(AuthScope.ANY), credentials);
         CloseableHttpClient client = HttpClients.custom().setDefaultCredentialsProvider(credentialsProvider).build();
         ApacheHttpClientEngine engine = ApacheHttpClientEngine.create(client);
         authorizedClient = ((ResteasyClientBuilder)ClientBuilder.newBuilder()).httpEngine(engine).build();
      }
      // unauthorizedClient
      {
         UsernamePasswordCredentials credentials_other = new UsernamePasswordCredentials("ordinaryUser", "password2");
         CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
         credentialsProvider.setCredentials(new AuthScope(AuthScope.ANY), credentials_other);
         CloseableHttpClient client = HttpClients.custom().setDefaultCredentialsProvider(credentialsProvider).build();
         ApacheHttpClientEngine engine = ApacheHttpClientEngine.create(client);
         unauthorizedClient = ((ResteasyClientBuilder)ClientBuilder.newBuilder()).httpEngine(engine).build();
      }
      // noAutorizationClient
      noAutorizationClient = (ResteasyClient)ClientBuilder.newClient();
   }

   @AfterClass
   public static void after() throws Exception {
      authorizedClient.close();
      unauthorizedClient.close();
      noAutorizationClient.close();
   }

   @Deployment
   public static Archive<?> deployLocatingResource() {
      WebArchive war = TestUtil.prepareArchive(BasicAuthTest.class.getSimpleName());

      Hashtable<String, String> contextParams = new Hashtable<String, String>();
      contextParams.put("resteasy.role.based.security", "true");

      war.addClass(BasicAuthBaseProxy.class)
            .addAsWebInfResource(BasicAuthTest.class.getPackage(), "jboss-web.xml", "/jboss-web.xml")
            .addAsWebInfResource(BasicAuthTest.class.getPackage(), "web.xml", "/web.xml");

      return TestUtil.finishContainerPrepare(war, contextParams, BasicAuthBaseResource.class,
            BasicAuthBaseResourceMoreSecured.class, BasicAuthBaseResourceAnybody.class);
   }

   private String generateURL(String path) {
      return PortProviderUtil.generateURL(path, BasicAuthTest.class.getSimpleName());
   }

   /**
    * @tpTestDetails Basic ProxyFactory test. Correct credentials are used.
    * @tpSince RESTEasy 3.0.16
    */
   @Test
   public void testProxy() throws Exception {
      BasicAuthBaseProxy proxy = authorizedClient.target(generateURL("/")).proxyBuilder(BasicAuthBaseProxy.class).build();
      Assert.assertEquals(WRONG_RESPONSE, proxy.get(), "hello");
      Assert.assertEquals(WRONG_RESPONSE, proxy.getAuthorized(), "authorized");
   }

   /**
    * @tpTestDetails Basic ProxyFactory test. No credentials are used.
    * @tpSince RESTEasy 3.0.16
    */
   @Test
   public void testProxyFailure() throws Exception {
      BasicAuthBaseProxy proxy = noAutorizationClient.target(generateURL("/")).proxyBuilder(BasicAuthBaseProxy.class).build();
      try {
         proxy.getFailure();
         Assert.fail();
      } catch (NotAuthorizedException e) {
         Assert.assertEquals(HttpResponseCodes.SC_UNAUTHORIZED, e.getResponse().getStatus());
      }
   }

   /**
    * @tpTestDetails Test secured resource with correct and incorrect credentials.
    * @tpSince RESTEasy 3.0.16
    */
   @Test
   public void testSecurity() throws Exception {
      // authorized client
      {
         Response response = authorizedClient.target(generateURL("/secured")).request().get();
         Assert.assertEquals(HttpResponseCodes.SC_OK, response.getStatus());
         Assert.assertEquals(WRONG_RESPONSE, "hello", response.readEntity(String.class));
      }

      {
         Response response = authorizedClient.target(generateURL("/secured/authorized")).request().get();
         Assert.assertEquals(HttpResponseCodes.SC_OK, response.getStatus());
         Assert.assertEquals(WRONG_RESPONSE, "authorized", response.readEntity(String.class));
      }

      {
         Response response = authorizedClient.target(generateURL("/secured/deny")).request().get();
         Assert.assertEquals(HttpResponseCodes.SC_FORBIDDEN, response.getStatus());
         Assert.assertEquals(WRONG_RESPONSE, ACCESS_FORBIDDEN_MESSAGE, response.readEntity(String.class));
      }
      {
         Response response = authorizedClient.target(generateURL("/secured3/authorized")).request().get();
         Assert.assertEquals(HttpResponseCodes.SC_OK, response.getStatus());
         Assert.assertEquals(WRONG_RESPONSE, "authorized", response.readEntity(String.class));
      }

      // unauthorized client
      {
         Response response = unauthorizedClient.target(generateURL("/secured3/authorized")).request().get();
         Assert.assertEquals(HttpResponseCodes.SC_FORBIDDEN, response.getStatus());
         Assert.assertEquals(WRONG_RESPONSE, ACCESS_FORBIDDEN_MESSAGE, response.readEntity(String.class));
      }
      {
         Response response = unauthorizedClient.target(generateURL("/secured3/anybody")).request().get();
         Assert.assertEquals(HttpResponseCodes.SC_OK, response.getStatus());
         response.close();
      }
   }

   /**
    * @tpTestDetails Regression test for RESTEASY-579
    * @tpSince RESTEasy 3.0.16
    */
   @Test
   public void test579() throws Exception {
      Response response = authorizedClient.target(generateURL("/secured2")).request().get();
      Assert.assertEquals(HttpResponseCodes.SC_NOT_FOUND, response.getStatus());
      response.close();
   }

   /**
    * @tpTestDetails Check failures for secured resource.
    * @tpSince RESTEasy 3.0.16
    */
   @Test
   public void testSecurityFailure() throws Exception {
      {
         Response response = noAutorizationClient.target(generateURL("/secured")).request().get();
         Assert.assertEquals(HttpResponseCodes.SC_UNAUTHORIZED, response.getStatus());
         response.close();
      }

      {
         Response response = authorizedClient.target(generateURL("/secured/authorized")).request().get();
         Assert.assertEquals(HttpResponseCodes.SC_OK, response.getStatus());
         Assert.assertEquals(WRONG_RESPONSE, "authorized", response.readEntity(String.class));
      }

      {
         Response response = unauthorizedClient.target(generateURL("/secured/authorized")).request().get();
         Assert.assertEquals(HttpResponseCodes.SC_FORBIDDEN, response.getStatus());
         Assert.assertEquals(ACCESS_FORBIDDEN_MESSAGE, response.readEntity(String.class));
      }
   }

   /**
    * @tpTestDetails Regression test for JBEAP-1589, RESTEASY-1249
    * @tpSince RESTEasy 3.0.16
    */
   @Test
   public void testAccesForbiddenMessage() throws Exception {
      UsernamePasswordCredentials credentials = new UsernamePasswordCredentials("bill", "password1");
      CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
      credentialsProvider.setCredentials(new AuthScope(AuthScope.ANY), credentials);
      CloseableHttpClient client = HttpClients.custom().setDefaultCredentialsProvider(credentialsProvider).build();
      ApacheHttpClientEngine engine = ApacheHttpClientEngine.create(client);

      ResteasyClient authorizedClient = ((ResteasyClientBuilder)ClientBuilder.newBuilder()).httpEngine(engine).build();
      Response response = authorizedClient.target(generateURL("/secured/deny")).request().get();
      Assert.assertEquals(HttpResponseCodes.SC_FORBIDDEN, response.getStatus());
      Assert.assertEquals(ACCESS_FORBIDDEN_MESSAGE, response.readEntity(String.class));
      authorizedClient.close();
   }

   /**
    * @tpTestDetails Test Content-type when forbidden exception is raised, RESTEASY-1563
    * @tpSince RESTEasy 3.1.1
    */
   @Test
   @Category(NotForForwardCompatibility.class)
   public void testContentTypeWithForbiddenMessage() {
      Response response = unauthorizedClient.target(generateURL("/secured/denyWithContentType")).request().get();
      Assert.assertEquals(HttpResponseCodes.SC_FORBIDDEN, response.getStatus());
      Assert.assertEquals("Incorrect Content-type header", "text/html;charset=UTF-8", response.getHeaderString("Content-type"));
      Assert.assertEquals("Missing forbidden message in the response", ACCESS_FORBIDDEN_MESSAGE, response.readEntity(String.class));
   }

   /**
    * @tpTestDetails Test Content-type when unauthorized exception is raised
    * @tpSince RESTEasy 3.1.1
    */
   @Test
   public void testContentTypeWithUnauthorizedMessage() {
      Response response = noAutorizationClient.target(generateURL("/secured/denyWithContentType")).request().get();
      Assert.assertEquals(HttpResponseCodes.SC_UNAUTHORIZED, response.getStatus());
      Assert.assertEquals("Incorrect Content-type header", "text/html;charset=UTF-8", response.getHeaderString("Content-type"));
   }

   static class SecurityDomainSetup extends AbstractUsersRolesSecurityDomainSetup {

      @Override
      public void setConfigurationPath() throws URISyntaxException {
         Path filepath= Paths.get(BasicAuthTest.class.getResource("users.properties").toURI());
         Path parent = filepath.getParent();
         createPropertiesFiles(new File(parent.toUri()));
      }

   }
}
