package com.bluejeans.microservice.spring.http.client;

import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;

@Configuration
public class OkHttpConfiguration {

	private static Logger logger = LogManager.getLogger(OkHttpConfiguration.class);

	@Value("${application.http.client.connectTimeout.ms:20000}")
	private int connectTimeout;

	@Value("${application.http.client.requestTimeout.ms:60000}")
	private int requestTimeout;

	@Bean
	public ConnectionPool getConnectionPool() {
		ConnectionPool pool = new ConnectionPool(10, 2, TimeUnit.MINUTES);
		logger.info("okhttp3 using connection pool " + pool);
		return pool;
	}

	@Bean
	public OkHttpClient client(ConnectionPool pool) {
		logger.info("okhttp3 using connection pool " + pool);
		logger.info("okhttp3 " + pool.connectionCount());
		// read and connect timeout controlled through feign as of now
		OkHttpClient.Builder builder = new OkHttpClient.Builder().connectionPool(pool)
				.connectTimeout(connectTimeout, TimeUnit.MILLISECONDS)
				.readTimeout(requestTimeout, TimeUnit.MILLISECONDS).writeTimeout(requestTimeout, TimeUnit.MILLISECONDS);
		return builder.build();
	}

}
