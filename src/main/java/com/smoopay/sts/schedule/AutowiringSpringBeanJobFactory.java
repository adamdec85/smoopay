package com.smoopay.sts.schedule;

import org.quartz.spi.TriggerFiredBundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

public class AutowiringSpringBeanJobFactory extends SpringBeanJobFactory implements ApplicationContextAware {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private transient AutowireCapableBeanFactory beanFactory;

	@Override
	public void setApplicationContext(final ApplicationContext context) {
		beanFactory = context.getAutowireCapableBeanFactory();
	}

	@Override
	protected Object createJobInstance(final TriggerFiredBundle bundle) {
		Object job;
		try {
			job = super.createJobInstance(bundle);
			beanFactory.autowireBean(job);
			return job;
		} catch (Exception e) {
			logger.error("Could not create job instance!", e);
		}
		return null;
	}
}