package com.bluejeans.microservice;

import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.DispatcherServletAutoConfiguration;
import org.springframework.boot.autoconfigure.web.ErrorMvcAutoConfiguration;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication(exclude = { ErrorMvcAutoConfiguration.class, WebMvcAutoConfiguration.class,
		DispatcherServletAutoConfiguration.class })
@ComponentScan("com.bluejeans")
@Configuration
@EnableAutoConfiguration
@EnableDiscoveryClient
@EnableFeignClients
@EnableHystrix
public class ServiceLauncher {
	public static void main(String[] args) {
		new SpringApplicationBuilder().bannerMode(Banner.Mode.OFF).sources(ServiceLauncher.class).headless(true)
				.logStartupInfo(true).run(args);
	}
}
