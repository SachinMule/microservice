package com.bluejeans.microservice.service;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.springframework.boot.actuate.endpoint.HealthEndpoint;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;

@Component
@Path("/health")
public class HealthCheckService {

	@Inject
	private HealthEndpoint healthEndpoint;

	@GET
	@Path("/")
	@Produces("application/json")
	public Response healthcheck() {
		Health health = healthEndpoint.invoke();
		Response resp = null;
		if (health.getStatus() == Status.DOWN) {
			resp = Response.status(javax.ws.rs.core.Response.Status.BAD_GATEWAY).entity(health).build();
		} else {
			resp = Response.status(javax.ws.rs.core.Response.Status.OK).entity(health).build();
		}
		return resp;
	}
}
