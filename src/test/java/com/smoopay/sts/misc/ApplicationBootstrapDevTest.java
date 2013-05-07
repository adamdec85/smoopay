package com.smoopay.sts.misc;

import org.junit.After;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Sample test case bootstrapping the application.
 * 
 * @author Adam Dec
 */
public class ApplicationBootstrapDevTest {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	static {
		System.setProperty("spring.profiles.active", "dev");
	}

	private ClassPathXmlApplicationContext context;

	@Ignore
	@Test
	public void bootstrapsTestApplication() {
		if (logger.isDebugEnabled()) {
			logger.debug("bootstrapsApplication...");
		}
		context = new ClassPathXmlApplicationContext("classpath:WEB-INF/spring/application-context.xml");
		context.getEnvironment().setActiveProfiles("dev");
		context.registerShutdownHook();
	}

	@After
	public void destroy() {
		if (context != null) {
			context.close();
		}
	}
}