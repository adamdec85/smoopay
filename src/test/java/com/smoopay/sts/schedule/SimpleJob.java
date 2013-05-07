package com.smoopay.sts.schedule;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class SimpleJob extends QuartzJobBean {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		if (logger.isDebugEnabled()) {
			logger.debug("--->>> Smoopay job executed...");
		}
	}
}