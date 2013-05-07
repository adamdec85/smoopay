package com.smoopay.sts;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Launch {

	private static final Logger logger = LoggerFactory.getLogger(Launch.class.getName());

	private static final String VERSION = "0.1";

	private static volatile boolean running = false;

	static {
		System.setProperty("spring.profiles.active", "dev");
	}

	public static void main(String[] args) throws InterruptedException {

		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:WEB-INF/spring/application-context.xml");
				context.registerShutdownHook();
				logger.info("Started Smoopay Transactional Server " + VERSION + "...");
				running = true;
				while (isRunning()) {
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						logger.error("Interrupted!", e);
					}
				}
				context.close();
			}
		});
		t.start();
		t.join();
	}

	public static boolean isRunning() {
		return running;
	}

	public static void setRunning(boolean running) {
		Launch.running = running;
	}
}