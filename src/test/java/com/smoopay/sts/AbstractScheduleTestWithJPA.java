package com.smoopay.sts;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.junit.runner.RunWith;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;

import com.smoopay.sts.config.ScheduleTestApplicationConfigWithJPA;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(profiles = "unit-tests")
@ContextConfiguration(classes = ScheduleTestApplicationConfigWithJPA.class)
@Transactional(rollbackFor = { SchedulerException.class })
public class AbstractScheduleTestWithJPA {

	@Autowired
	protected DataSource dataSource;

	@Resource
	protected PlatformTransactionManager transactionManager;

	@Autowired
	protected ApplicationContext applicationContext;
}