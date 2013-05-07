package com.smoopay.sts;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;

import com.smoopay.sts.config.ScheduleTestApplicationConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ScheduleTestApplicationConfig.class)
@Transactional
@ActiveProfiles(profiles = "unit-tests")
public class AbstractScheduleTest {

	@Autowired
	protected DataSource dataSource;

	@Resource
	protected PlatformTransactionManager transactionManager;

	@Autowired
	protected SpringBeanJobFactory springFactory;

	@Autowired
	protected ApplicationContext applicationContext;
}