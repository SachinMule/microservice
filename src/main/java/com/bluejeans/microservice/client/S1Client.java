package com.bluejeans.microservice.client;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import org.springframework.cloud.netflix.feign.FeignClient;

import com.bluejeans.microservice.spring.http.client.FeignConfiguration;

@FeignClient(name = "s1", configuration = FeignConfiguration.class)
public interface S1Client {

	@GET
	@Path("/v1/call")
	public String call(@QueryParam("also") String also);
}
