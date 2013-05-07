package com.smoopay.sts;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.smoopay.sts.config.TestApplicationConfig;

/**
 * Abstract integration test to populate the database with dummy data.
 * 
 * @author Adam Dec
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestApplicationConfig.class)
@Transactional
@TransactionConfiguration
@ActiveProfiles(profiles = "unit-tests")
public abstract class AbstractIntegrationTest {

	@PersistenceContext
	protected EntityManager em;

	@Autowired
	protected DataSource dataSource;

	@Before
	public void populateDatabase() {
		ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
		populator.addScript(new ClassPathResource("sql/banks_data.sql"));
		populator.addScript(new ClassPathResource("sql/clients_data.sql"));
		populator.addScript(new ClassPathResource("sql/merchant_data.sql"));
		populator.addScript(new ClassPathResource("sql/bank_registry_data.sql"));
		DatabasePopulatorUtils.execute(populator, dataSource);
	}

	public EntityManager getEm() {
		return em;
	}
}