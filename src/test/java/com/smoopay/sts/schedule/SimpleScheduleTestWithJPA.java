package com.smoopay.sts.schedule;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.test.annotation.Rollback;

import com.smoopay.sts.AbstractScheduleTestWithJPA;

/**
 * @author Adam Dec
 */
public class SimpleScheduleTestWithJPA extends AbstractScheduleTestWithJPA {

	@Autowired
	private SchedulerFactoryBean schedulerFactoryBean;

	@Test
	@Rollback(false)
	public void shouldTriggerJobAfter3sec() throws Exception {
		schedulerFactoryBean.start();
		Thread.sleep(5 * 1000L);
		schedulerFactoryBean.stop();
	}
}