package com.smoopay.sts.schedule.jobs;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * http://www.javabeat.net/2010/09/introduction-to-spring-batch/
 * http://blogs.justenougharchitecture.com/?p=122
 * https://github.com/SpringSource
 * /spring-batch/tree/master/spring-batch-samples/
 * src/test/java/org/springframework/batch/sample
 * http://static.springsource.org/spring-batch/reference/html/index.html
 * 
 * @author Adam Dec
 * 
 */
public class BanksQuartzJob extends QuartzJobBean {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private JobLauncher jobLauncher;

	@Autowired
	private Job bankJob;

	@Override
	public void executeInternal(JobExecutionContext context) throws JobExecutionException {
		if (logger.isDebugEnabled()) {
			logger.debug("--->>> Smoopay job executed...");
		}

		JobParametersBuilder builder = new JobParametersBuilder();
		builder.addLong("currentTime", new Long(System.currentTimeMillis()));

		try {
			jobLauncher.run(bankJob, builder.toJobParameters());
		} catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException | JobParametersInvalidException e) {
			logger.error("Could not launch smoopay job!", e);
		}
	}

	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}
}