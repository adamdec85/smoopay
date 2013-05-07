package com.smoopay.sts.schedule;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Adam Dec
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(profiles = "unit-tests")
@ContextConfiguration("classpath:/WEB-INF/spring/application-context.xml")
public class SimpleScheduleTestWithQuartzFromXMLContext {

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