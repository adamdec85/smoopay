package com.smoopay.sts.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.jolbox.bonecp.BoneCPDataSource;
import com.smoopay.sts.schedule.SimpleJob;

@Configuration
@Profile("unit-tests")
@EnableTransactionManagement
@EnableAspectJAutoProxy
@ComponentScan({ "com.smoopay.sts.schedule" })
@PropertySource("classpath:quartz/quartz.properties")
public class ScheduleTestApplicationConfig {

	@Autowired
	protected ApplicationContext applicationContext;

	@Bean
	public JobDetailFactoryBean jobDetailFactoryBean() throws Exception {
		JobDetailFactoryBean jobDetailFactory = new JobDetailFactoryBean();
		jobDetailFactory.setGroup("Simple-Group");
		jobDetailFactory.setName("Simple-Job");
		jobDetailFactory.setJobClass(SimpleJob.class);
		jobDetailFactory.setDurability(true);
		return jobDetailFactory;
	}

	@Bean
	public SimpleTriggerFactoryBean simpleTrigger() throws Exception {
		SimpleTriggerFactoryBean simpleTrigger = new SimpleTriggerFactoryBean();
		simpleTrigger.setName("BanksQuartzJob-Trigger");
		simpleTrigger.setJobDetail(jobDetailFactoryBean().getObject());
		simpleTrigger.setStartDelay(3000L);
		simpleTrigger.setRepeatInterval(10 * 1000L);
		return simpleTrigger;
	}

	@Bean
	public SpringBeanJobFactory springFactory() {
		return new SpringBeanJobFactory();
	}

	@Bean
	public SchedulerFactoryBean scheduler() throws Exception {
		SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
		schedulerFactoryBean.setAutoStartup(false);
		schedulerFactoryBean.setWaitForJobsToCompleteOnShutdown(false);
		schedulerFactoryBean.setOverwriteExistingJobs(false);
		schedulerFactoryBean.setApplicationContext(applicationContext);
		schedulerFactoryBean.setApplicationContextSchedulerContextKey("applicationContext");
		schedulerFactoryBean.setDataSource(dataSource());
		schedulerFactoryBean.setTransactionManager(transactionManager());
		schedulerFactoryBean.setJobFactory(springFactory());
		Properties quartzProperties = PropertiesLoaderUtils.loadProperties(new ClassPathResource("quartz/quartz.properties"));
		schedulerFactoryBean.setQuartzProperties(quartzProperties);
		Trigger[] triggers = { simpleTrigger().getObject() };
		schedulerFactoryBean.setTriggers(triggers);
		return schedulerFactoryBean;
	}

	@Bean
	public DataSource dataSource() {
		BoneCPDataSource dataSource = new BoneCPDataSource();
		dataSource.setDriverClass("org.hsqldb.jdbcDriver");
		dataSource.setJdbcUrl("jdbc:hsqldb:mem:testDb");
		dataSource.setUsername("sa");
		dataSource.setPassword("");
		ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
		populator.addScript(new ClassPathResource("sql/quartz/hsqldb.sql"));
		DatabasePopulatorUtils.execute(populator, dataSource);
		return dataSource;
	}

	@Bean
	public PlatformTransactionManager transactionManager() {
		DataSourceTransactionManager txManager = new DataSourceTransactionManager();
		txManager.setDataSource(dataSource());
		return txManager;
	}
}