package com.smoopay.sts.misc;

import org.junit.After;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Sample test case bootstrapping the application.
 * 
 * @author Adam Dec
 */
public class ApplicationBootstrapUnitTestsTest {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	static {
		System.setProperty("spring.profiles.active", "unit-tests");
	}

	private ClassPathXmlApplicationContext context;

	@Test
	public void bootstrapsTestApplication() {
		if (logger.isDebugEnabled()) {
			logger.debug("bootstrapsApplication...");
		}
		context = new ClassPathXmlApplicationContext("classpath:WEB-INF/spring/application-context.xml");
		context.getEnvironment().setActiveProfiles("unit-tests");
		context.registerShutdownHook();

	}

	@After
	public void destroy() {
		if (context != null) {
			context.close();
		}
	}
}