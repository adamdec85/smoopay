package com.smoopay.sts;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;
import org.springframework.core.env.Environment;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.client.MockMvcClientHttpRequestFactory;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.smoopay.sts.utils.JsonUtils;

/**
 * Abstract integration test to populate the database with dummy data.
 * 
 * @author Adam Dec
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(profiles = "unit-tests")
@WebAppConfiguration
@EnableWebMvc
@EnableSpringConfigured
@ContextConfiguration("classpath:/WEB-INF/spring/application-context.xml")
public abstract class AbstractServiceIntegrationTest {

	@Resource
	private Environment environment;

	@Resource
	private FilterChainProxy springSecurityFilterChain;

	@Autowired
	protected WebApplicationContext wac;

	@Autowired
	protected JsonUtils jsonUtils;

	protected MockMvc mockMvc;
	private RestTemplate restTemplate;

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).addFilter(springSecurityFilterChain).build();
		this.restTemplate = new RestTemplate(new MockMvcClientHttpRequestFactory(mockMvc));
	}

	public RestTemplate getRestTemplate() {
		return restTemplate;
	}

	public Environment getEnvironment() {
		return environment;
	}
}