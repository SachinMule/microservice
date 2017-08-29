package com.bluejeans.microservice.spring.http.client;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.Contract;
import feign.Request;
import feign.Retryer;
import feign.jaxrs.JAXRSContract;

@Configuration
public class FeignConfiguration {

	private static Logger logger = LogManager.getLogger(FeignConfiguration.class);

	@Value("${application.http.client.connectTimeout.ms:20000}")
	private int connectTimeout;

	@Value("${application.http.client.requestTimeout.ms:60000}")
	private int requestTimeout;

	@Bean
	public Contract feignContract() {
		return new JAXRSContract();
	}

	@Bean
	public Request.Options options() {
		logger.info("configuring feign timeouts");
		return new Request.Options(connectTimeout, requestTimeout);
	}

	@Bean
	public Retryer retryer() {
		logger.info("configuring feign retries");
		return Retryer.NEVER_RETRY;
	}

}
