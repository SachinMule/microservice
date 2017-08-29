package com.bluejeans.microservice.service;

import java.util.Arrays;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.bluejeans.microservice.client.S1Client;
import com.bluejeans.microservice.client.S2Client;
import com.bluejeans.microservice.client.S3Client;

@Component
@Path("/v1")
public class Service {
	private Random r = new Random();

	@Inject
	private S1Client s1Client;

	@Inject
	private S2Client s2Client;

	@Inject
	private S3Client s3Client;

	@Value("${spring.application.name}")
	private String serviceName;

	@GET
	@Path("/call")
	public String call(@QueryParam("also") String also, @QueryParam("responseSize") Integer respSize) {
		// try {
		// Thread.sleep(r.nextInt(300));
		// } catch (InterruptedException e) {
		// }
		Set<String> res = new TreeSet<>();
		res.add(serviceName);
		if (also != null) {
			String[] arr = also.split(",");
			String alsoParam = null;
			for (String serviceName : arr) {
				if (serviceName.contains(":")) {
					String[] temp = serviceName.split(":");
					serviceName = temp[0];
					alsoParam = temp[1];
				}
				String result = null;
				switch (serviceName) {
				case "s1":
					result = s1Client.call(alsoParam);
					break;
				case "s2":
					result = s2Client.call(alsoParam);
					break;
				case "s3":
					result = s3Client.call(alsoParam);
					break;
				default:
					throw new java.lang.UnsupportedOperationException();
				}
				res.addAll(Arrays.asList(result.split(",")));
			}
		}

		String resp = StringUtils.join(res, ",");

		if (respSize != null && respSize > 0) {
			resp += "\n" + RandomStringUtils.random(respSize);
		}
		return resp;
	}
}
