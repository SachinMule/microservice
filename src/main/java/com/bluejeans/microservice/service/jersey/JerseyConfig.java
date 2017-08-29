package com.bluejeans.microservice.service.jersey;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ws.rs.Path;
import javax.ws.rs.ext.Provider;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.logging.LoggingFeature;
import org.glassfish.jersey.process.internal.RequestScoped;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.stereotype.Component;

@Component("dispatcherServlet")
public class JerseyConfig extends ResourceConfig {
	private Logger logger = LogManager.getLogger(this.getClass());
	@Autowired
	private ApplicationContext appCtx;

	@PostConstruct
	public void setup() {
		logger.info("Rest classes found:");
		Map<String, Object> beans = appCtx.getBeansWithAnnotation(Path.class);
		for (Object o : beans.values()) {
			logger.info("Registering resource with class -> " + o.getClass().getName());
			register(o);
		}
		register(new LoggingFeature(java.util.logging.Logger.getLogger("com.bluejeans.smg.service.jersey")));
		Map<String, Object> providersMap = appCtx.getBeansWithAnnotation(Provider.class);
		List<Object> providers = new ArrayList<>(providersMap.values());
		Collections.sort(providers, AnnotationAwareOrderComparator.INSTANCE);
		logger.info("Found filers " + providers);
		for (Object o : providers) {
			logger.info("Registering filter with class -> " + o.getClass().getName());
			register(o);
		}
		property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);
	}
}