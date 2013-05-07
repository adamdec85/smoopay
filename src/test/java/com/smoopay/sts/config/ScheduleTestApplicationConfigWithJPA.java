package com.smoopay.sts.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;
import org.springframework.transaction.PlatformTransactionManager;

import com.jolbox.bonecp.BoneCPDataSource;
import com.smoopay.sts.schedule.AutowiringSpringBeanJobFactory;
import com.smoopay.sts.schedule.SimpleJobWithAutowiring;

@Configuration
@EnableJpaRepositories(basePackages = "com.smoopay.sts.repository")
@ComponentScan({ "com.smoopay.sts.utils", "com.smoopay.sts.dao", "com.smoopay.sts.common.converter", "com.smoopay.sts.schedule" })
@PropertySource("classpath:quartz/quartz.properties")
public class ScheduleTestApplicationConfigWithJPA {

	@Autowired
	protected ApplicationContext applicationContext;

	private static final String PACKAGE_TO_SCAN = "com.smoopay.sts.entity";

	@Bean
	public DataSource dataSource() {
		BoneCPDataSource dataSource = new BoneCPDataSource();
		dataSource.setJdbcUrl("jdbc:hsqldb:mem:testDb");
		dataSource.setDriverClass("org.hsqldb.jdbcDriver");
		dataSource.setUsername("sa");
		dataSource.setPassword("");
		return dataSource;
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		vendorAdapter.setDatabase(Database.HSQL);
		vendorAdapter.setGenerateDdl(true);
		vendorAdapter.setShowSql(true);

		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setJpaVendorAdapter(vendorAdapter);
		factory.setPackagesToScan(PACKAGE_TO_SCAN);
		factory.setDataSource(dataSource());
		return factory;
	}

	@Bean
	public PlatformTransactionManager transactionManager() {
		JpaTransactionManager txManager = new JpaTransactionManager();
		txManager.setEntityManagerFactory(entityManagerFactory().getObject());
		txManager.setDataSource(dataSource());
		return txManager;
	}

	@Bean
	public JobDetailFactoryBean jobDetailFactoryBean() throws Exception {
		JobDetailFactoryBean jobDetailFactory = new JobDetailFactoryBean();
		jobDetailFactory.setGroup("SimpleJobWithAutowiring-Group");
		jobDetailFactory.setName("SimpleJobWithAutowiring-Job");
		jobDetailFactory.setJobClass(SimpleJobWithAutowiring.class);
		jobDetailFactory.setDurability(true);
		return jobDetailFactory;
	}

	@Bean
	public SimpleTriggerFactoryBean simpleTrigger() throws Exception {
		SimpleTriggerFactoryBean simpleTrigger = new SimpleTriggerFactoryBean();
		simpleTrigger.setName("SimpleJobWithAutowiring-Trigger");
		simpleTrigger.setJobDetail(jobDetailFactoryBean().getObject());
		simpleTrigger.setStartDelay(1000L);
		simpleTrigger.setRepeatInterval(100 * 1000L);
		return simpleTrigger;
	}

	@Bean
	public SpringBeanJobFactory springFactory() {
		return new SpringBeanJobFactory();
	}

	@Bean
	public AutowiringSpringBeanJobFactory springAutowiredFactory() {
		AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
		jobFactory.setApplicationContext(applicationContext);
		return jobFactory;
	}

	/**
	 * If this was added in @Before - thread was hung in job execution (invoking
	 * JPa query) method ;/
	 * 
	 * @return
	 * @throws Exception
	 */
	@Bean
	public SchedulerFactoryBean scheduler() throws Exception {
		ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
		populator.addScript(new ClassPathResource("sql/quartz/hsqldb.sql"));
		populator.addScript(new ClassPathResource("sql/banks_data.sql"));
		populator.addScript(new ClassPathResource("sql/clients_data.sql"));
		populator.addScript(new ClassPathResource("sql/merchant_data.sql"));
		DatabasePopulatorUtils.execute(populator, dataSource());

		SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
		schedulerFactoryBean.setAutoStartup(false);
		schedulerFactoryBean.setWaitForJobsToCompleteOnShutdown(false);
		schedulerFactoryBean.setOverwriteExistingJobs(false);
		schedulerFactoryBean.setApplicationContextSchedulerContextKey("applicationContext");
		schedulerFactoryBean.setDataSource(dataSource());
		schedulerFactoryBean.setTransactionManager(transactionManager());
		schedulerFactoryBean.setJobFactory(springAutowiredFactory());
		Properties quartzProperties = PropertiesLoaderUtils.loadProperties(new ClassPathResource("quartz/quartz.properties"));
		schedulerFactoryBean.setQuartzProperties(quartzProperties);
		Trigger[] triggers = { simpleTrigger().getObject() };
		schedulerFactoryBean.setTriggers(triggers);
		return schedulerFactoryBean;
	}
}