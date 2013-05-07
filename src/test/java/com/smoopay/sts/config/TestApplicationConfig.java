package com.smoopay.sts.config;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.config.CustomScopeConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.converter.json.JacksonObjectMapperFactoryBean;
import org.springframework.orm.hibernate4.HibernateExceptionTranslator;
import org.springframework.orm.jpa.JpaDialect;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.jolbox.bonecp.BoneCPDataSource;

/**
 * Application configuration. (Must be in the same directory as tests) <br />
 * 
 * http://stackoverflow.com/questions/9512177/spring-data-repository-autowiring-
 * fails <br />
 * 
 * http://stackoverflow.com/questions/10539417/spring-data-jpa-injection-fails-
 * beancreationexception-could-not-autowire-fi <br />
 * 
 * http://blog.springsource.org/2011/02/10/getting-started-with-spring-data-jpa <br />
 * 
 * http://blog.springsource.org/2012/04/06/migrating-to-spring-3-1-and-hibernate
 * -4-1 <br />
 * 
 * @author Adam Dec
 */
@Configuration
@Profile("unit-tests")
@EnableTransactionManagement
@EnableAspectJAutoProxy
@EnableJpaRepositories(basePackages = "com.smoopay.sts.repository")
@ComponentScan({ "com.smoopay.sts.utils", "com.smoopay.sts.dao", "com.smoopay.sts.common.converter" })
public class TestApplicationConfig {

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
	public ObjectMapper objectMapper() {
		JacksonObjectMapperFactoryBean factory = new JacksonObjectMapperFactoryBean();
		factory.afterPropertiesSet();
		ObjectMapper mapper = factory.getObject();
		return mapper;
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		vendorAdapter.setDatabase(Database.HSQL);
		vendorAdapter.setDatabasePlatform("org.hibernate.dialect.HSQLDialect");
		vendorAdapter.setGenerateDdl(true);
		vendorAdapter.setShowSql(true);

		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setJpaVendorAdapter(vendorAdapter);
		factory.setPackagesToScan(PACKAGE_TO_SCAN);
		factory.setDataSource(dataSource());

		Properties properties = new Properties();
		properties.setProperty("hibernate.cache.use_second_level_cache", "true");
		properties.setProperty("hibernate.cache.region.factory_class", "org.hibernate.cache.ehcache.SingletonEhCacheRegionFactory");
		properties.setProperty("hibernate.cache.use_query_cache", "true");
		properties.setProperty("hibernate.generate_statistics", "true");

		// For Hibernate Evers
		properties.setProperty("org.hibernate.envers.auditTablePrefix", "");
		properties.setProperty("org.hibernate.envers.auditTableSuffix", "_History");

		properties.setProperty("javax.persistence.sharedCache.mode", "ENABLE_SELECTIVE");
		factory.setJpaProperties(properties);

		return factory;
	}

	@Bean
	public PlatformTransactionManager transactionManager() {
		JpaTransactionManager txManager = new JpaTransactionManager();
		txManager.setEntityManagerFactory(entityManagerFactory().getObject());
		JpaDialect jpaDialect = new HibernateJpaDialect();
		txManager.setJpaDialect(jpaDialect);
		return txManager;
	}

	@Bean
	public HibernateExceptionTranslator hibernateExceptionTranslator() {
		return new HibernateExceptionTranslator();
	}

	@Bean
	public static CustomScopeConfigurer customScopeConfigurer() {
		CustomScopeConfigurer customScope = new CustomScopeConfigurer();
		Map<String, Object> scopes = new HashMap<>();
		scopes.put("thread", org.springframework.context.support.SimpleThreadScope.class);
		customScope.setScopes(scopes);
		return customScope;
	}
}