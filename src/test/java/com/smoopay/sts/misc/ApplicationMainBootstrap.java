package com.smoopay.sts.misc;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.smoopay.sts.config.ScheduleTestApplicationConfig;
import com.smoopay.sts.config.TestApplicationConfig;

/**
 * Sample test case bootstrapping the application.
 * 
 * @author Adam Dec
 */
public class ApplicationMainBootstrap {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Test
	public void bootstrapsTestApplication() {
		if (logger.isDebugEnabled()) {
			logger.debug("bootstrapsTestApplication...");
		}
		AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(TestApplicationConfig.class);
		annotationConfigApplicationContext.registerShutdownHook();
		annotationConfigApplicationContext.close();
	}

	@Test
	public void bootstrapsScheduleTestApplication() {
		if (logger.isDebugEnabled()) {
			logger.debug("bootstrapsScheduleTestApplication...");
		}
		AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(ScheduleTestApplicationConfig.class);
		annotationConfigApplicationContext.registerShutdownHook();
		annotationConfigApplicationContext.close();
	}
}