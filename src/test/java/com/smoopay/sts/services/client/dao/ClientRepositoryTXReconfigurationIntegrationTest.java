package com.smoopay.sts.services.client.dao;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.smoopay.sts.config.TestApplicationConfig;
import com.smoopay.sts.entity.client.Client;
import com.smoopay.sts.entity.common.AuthData;
import com.smoopay.sts.repository.client.ClientRepository;

/**
 * Integration test to show customized transaction configuration in
 * {@link ClientRepository}.
 * 
 * @author Adam Dec
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestApplicationConfig.class)
@DirtiesContext
@ActiveProfiles(profiles = "unit-tests")
public class ClientRepositoryTXReconfigurationIntegrationTest {

	@Autowired
	private ClientRepository repository;

	@Test
	public void executesRedeclaredMethodWithCustomTransactionConfiguration() {
		Client customer = new Client("A", "B", 1, new AuthData("login", "pass"));
		Client result = repository.save(customer);
		assertThat(result, is(notNullValue()));
		assertThat(result.getId(), is(notNullValue()));
		assertThat(result.getFirstName(), is("A"));
		assertThat(result.getLastName(), is("B"));
	}
}