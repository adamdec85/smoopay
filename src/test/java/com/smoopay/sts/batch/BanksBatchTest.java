package com.smoopay.sts.batch;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(profiles = "unit-tests")
@ContextConfiguration({ "classpath:/WEB-INF/spring/data-jpa-context.xml", "classpath:/WEB-INF/spring/batch-context.xml" })
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
public class BanksBatchTest {

	@Autowired
	private JobLauncher jobLauncher;

	@Autowired
	private Job bankJob;

	@Test
	public void testLaunchBankJob() throws Exception {
		jobLauncher.run(bankJob, new JobParameters());
	}
}