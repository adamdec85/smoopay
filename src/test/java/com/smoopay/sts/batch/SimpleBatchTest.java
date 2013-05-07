package com.smoopay.sts.batch;

import static org.junit.Assert.assertNotNull;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.smoopay.sts.config.BatchSimpleTestApplicationConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = BatchSimpleTestApplicationConfig.class)
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
public class SimpleBatchTest {

	@Autowired
	private JobLauncher jobLauncher;

	@Autowired
	private Job job1;

	@Autowired
	private Environment env;

	@Autowired
	protected DataSource dataSource;

	@Before
	public void init() {
		ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
		populator.addScript(new ClassPathResource(env.getProperty("batch.schema.script")));
		DatabasePopulatorUtils.execute(populator, dataSource);
	}

	@Test
	public void testSimpleProperties() throws Exception {
		assertNotNull(jobLauncher);
	}

	@Test
	public void testLaunchSimpleJob() throws Exception {
		jobLauncher.run(job1, new JobParameters());
	}
}