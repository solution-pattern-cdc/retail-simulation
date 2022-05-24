package org.acme;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import io.vertx.core.json.JsonObject;

@ApplicationScoped
@Path("/simulate")
public class SimulatorResource {

    @Inject
    SimulatorService simulatorService;

    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response simulate(String payload) {
        JsonObject json = new JsonObject(payload);
        Integer count = json.getInteger("count");
        Long customer = json.getLong("customer");
        String response = simulatorService.simulate(customer, count);
        return Response.status(Response.Status.OK.getStatusCode()).entity(response).build();
    }

}
