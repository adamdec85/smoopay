package com.smoopay.sts.schedule;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.transaction.annotation.Transactional;

import com.smoopay.sts.AbstractScheduleTest;

/**
 * http://code.google.com/p/myschedule/downloads/detail?name=myschedule-2.4.4.
 * war
 * 
 * http://jksnu.blogspot.com/2011/03/quartz-framework-implementation-with.html
 * 
 * @author Adam Dec
 * 
 */
@Transactional
public class SimpleScheduleTest extends AbstractScheduleTest {

	@Autowired
	private SchedulerFactoryBean schedulerFactoryBean;

	/**
	 * http://sloanseaman.com/wordpress/2011/06/06/spring-and-quartz-and-
	 * persistence
	 * http://forum.springsource.org/showthread.php?12837-Persistent-
	 * Job-using-Quartz-and-Spring
	 */
	@Test
	public void shouldTriggerJobAfter3sec() throws Exception {
		schedulerFactoryBean.start();
		Thread.sleep(5 * 1000L);
		schedulerFactoryBean.stop();
	}
}