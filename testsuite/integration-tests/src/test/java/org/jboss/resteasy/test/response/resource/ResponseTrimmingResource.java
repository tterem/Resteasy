package org.jboss.resteasy.test.response.resource;

import javax.ws.rs.*;

@Path("/")
public class ResponseTrimmingResource {

    public ResponseTrimmingResource() {
    }

    @GET
    @Path("/json")
    @POST
    @Consumes({"application/json"})
    @Produces({"application/json"})
    public String getJSON(int n) {
        return "{\"result\":\"" + n + "\"}";
    }

    @GET
    @POST
    @Consumes({"application/json"})
    @Path("/xml")
    @Produces({"application/xml"})
    public String getXML(int n) {
        return "<xml><result>" + n + "</result></xml>";
    }
}
