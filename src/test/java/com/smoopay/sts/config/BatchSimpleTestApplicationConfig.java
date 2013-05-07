package com.smoopay.sts.config;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.MapJobRepositoryFactoryBean;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.jolbox.bonecp.BoneCPDataSource;
import com.smoopay.sts.batch.SimpleItemReader;
import com.smoopay.sts.batch.SimpleItemWriter;
import com.smoopay.sts.batch.SimpleTasklet;

@Configuration
@EnableTransactionManagement
@EnableAspectJAutoProxy
@ComponentScan({ "com.smoopay.sts.batch" })
@PropertySource("batch/batch.properties")
@EnableBatchProcessing
public class BatchSimpleTestApplicationConfig {

	@Autowired
	private JobBuilderFactory jobs;

	@Autowired
	private StepBuilderFactory steps;

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
	public JobRepository jobRepository() throws Exception {
		return new MapJobRepositoryFactoryBean(transactionManager()).getJobRepository();
	}

	@Bean
	public SimpleJobLauncher jobLauncher() throws Exception {
		SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
		jobLauncher.setJobRepository(jobRepository());
		return jobLauncher;
	}

	@Bean
	public SimpleItemReader simpleItemReader() {
		return new SimpleItemReader();
	}

	@Bean
	public SimpleItemWriter simpleItemWriter() {
		return new SimpleItemWriter();
	}

	// TODO: Add tasklet with chunk processing
	@Bean
	public Tasklet tasklet() {
		return new SimpleTasklet();
	}

	@Bean
	public Job job() throws Exception {
		return jobs.get("job1").start(step1()).build();
	}

	@Bean
	public Step step1() throws Exception {
		return steps.get("step1").tasklet(tasklet()).build();
	}

	@Bean
	public PlatformTransactionManager transactionManager() {
		return new DataSourceTransactionManager(dataSource());
	}
}