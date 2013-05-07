package com.smoopay.sts.services.audit;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.smoopay.sts.entity.client.Client;
import com.smoopay.sts.repository.client.ClientRepository;
import com.smoopay.sts.services.client.request.NewClientRequest_1_0;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(profiles = "unit-tests")
@ContextConfiguration("classpath:/WEB-INF/spring/application-context.xml")
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
public class AuditClientCreationIntegrationTest {

	@Autowired
	private ConversionService conversionService;

	@Autowired
	private ClientRepository clientRepository;

	@Test
	public void shouldCreateNewClientWithAuditData() {
		// given
		SecurityContext createEmptyContext = SecurityContextHolder.createEmptyContext();
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority("ADMIN"));
		User user = new User("Adam", "aaaaa", authorities);
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, authorities);
		createEmptyContext.setAuthentication(authentication);
		SecurityContextHolder.setContext(createEmptyContext);

		NewClientRequest_1_0 request = new NewClientRequest_1_0("Kasia", "Dec", 85112412344L, "login", "pass");
		request.setCity("Lezajsk");
		request.setCountry("Poland");
		request.setEmailAddress("katarzyna.dec@smoopay.com");
		request.setPostCode("37-300");
		request.setResident(true);
		request.setStreet("Ulica");

		// when
		Client clientEntity = conversionService.convert(request, Client.class);
		clientRepository.save(clientEntity);

		// than
		assertThat(clientEntity, is(notNullValue()));
		assertThat(clientEntity.getModifiedBy(), is("Adam"));
	}
}