package org.jboss.resteasy.test.response;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.test.response.resource.ResponseTrimmingResource;
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
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

/**
 * @tpSubChapter Response
 * @tpChapter Integration tests
 * @tpTestCaseDetails Ensures that response is not too long after endpoint consumes big invalid data (see JBEAP-6316)
 * @tpSince RESTEasy 3.6.1
 */
@RunWith(Arquillian.class)
@RunAsClient
public class ResponseTrimmingTest {

    static Client client;
    private static String original;
    private static String trimmed;
    private static final String DEFAULT = "war_default";
    private static final String NO_JSON_B = "war_no_json_b";
    private static final String JSON = "/json";
    private static final String XML = "/xml";

    @Deployment(name = DEFAULT)
    public static Archive<?> deployDefault() {
        WebArchive war = TestUtil.prepareArchive(DEFAULT);
        return TestUtil.finishContainerPrepare(war, null, ResponseTrimmingResource.class);
    }

    @Deployment(name = NO_JSON_B)
    public static Archive<?> deployNoJsonB() {
        WebArchive war = TestUtil.prepareArchive(NO_JSON_B);
        war.addAsManifestResource("jboss-deployment-structure-no-json-b.xml", "jboss-deployment-structure.xml");
        return TestUtil.finishContainerPrepare(war, null, ResponseTrimmingResource.class);
    }

    @BeforeClass
    public static void init() {
        client = new ResteasyClientBuilder().build();

        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= 10 * 1024 * 1024; i++) {
            sb.append("A");
        }
        original = sb.toString();

        StringBuilder sb2 = new StringBuilder();
        for (int i = 1; i <= 256; i++) {
            sb2.append("A");
        }
        trimmed = sb2.toString();
    }

    @AfterClass
    public static void after() throws Exception {
        client.close();
    }

    @Test
    public void testJsonDefault(){
        test(DEFAULT, JSON);
    }

    @Test
    public void testJsonNoJsonB(){
        test(NO_JSON_B, JSON);
    }

    @Test
    public void testXmlDefault(){
        test(DEFAULT, XML);
    }

    @Test
    public void testXmlNoJsonB(){
        test(NO_JSON_B, XML);
    }

    /**
     * Send very (10 MB) long string to the endpoint that expects int so error message is returned in response.
     * Check that response does not contain full string and has reasonable length.
     *
     * @param deployment DEFAULT (use JSON-B) or NO_JSON_B (use Jackson)
     * @param language JSON or XML endpoint
     */
    private void test(String deployment, String language) {
        Response response = client.target(PortProviderUtil.generateURL(language, deployment)).request().post(Entity.entity(original, "application/json"));
        String responseText = response.readEntity(String.class);

        if (deployment.equals(NO_JSON_B)) {
            Assert.assertTrue("Unrecognized token does not show", responseText.contains(trimmed));
            Assert.assertFalse("Unrecognized token is not reasonably trimmed", responseText.contains(trimmed.concat("A")));
        }
        Assert.assertTrue("Response is longer than 550 characters", responseText.length() <= 550);

        response.close();
    }
}
