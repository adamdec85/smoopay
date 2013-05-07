package com.smoopay.sts.services.client.service;

import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.smoopay.sts.AbstractServiceIntegrationTest;
import com.smoopay.sts.services.client.request.NewClientRequest_1_0;
import com.smoopay.sts.utils.HttpBasicCredentialCreator;

/**
 * @author Adam Dec
 */
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class ClientServiceInvocationTest extends AbstractServiceIntegrationTest {

	@Test
	public void shouldNotInvokeClientGetByLogin() throws Exception {
		// given
		final String login = "witold3_login";

		// when
		HttpHeaders httpHeaders = HttpBasicCredentialCreator.createBasicCredential("witold3_login", "witold3_pass");
		ResultActions perform = this.mockMvc.perform(MockMvcRequestBuilders.get("/client/v1/login/" + login).headers(httpHeaders)
				.accept(MediaType.APPLICATION_JSON));

		// than
		perform.andExpect(MockMvcResultMatchers.status().isUnauthorized());
	}

	@Test
	public void shouldInvokeClientGetByLogin() throws Exception {
		// given
		final String login = "witold1_login";

		// when
		HttpHeaders httpHeaders = HttpBasicCredentialCreator.createBasicCredential("witold1_login", "witold1_pass");
		ResultActions perform = this.mockMvc.perform(MockMvcRequestBuilders.get("/client/v1/login/" + login).headers(httpHeaders)
				.accept(MediaType.APPLICATION_JSON));

		// than
		perform.andExpect(MockMvcResultMatchers.status().isOk());
		perform.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
		perform.andExpect(MockMvcResultMatchers.jsonPath("$..clientId[0]").value(3));
		perform.andExpect(MockMvcResultMatchers.jsonPath("$..firstName[0]").value("Witold"));
		perform.andExpect(MockMvcResultMatchers.jsonPath("$..lastName[0]").value("Slizowski"));
		perform.andExpect(MockMvcResultMatchers.jsonPath("$..pesel[0]").value(85082416755L));
	}

	@Test
	public void shouldInvokeClientGetFirstName() throws Exception {
		// given
		final String firstName = "Adam";

		// when
		ResultActions perform = this.mockMvc.perform(MockMvcRequestBuilders.get("/client/v1/firstName/" + firstName).accept(MediaType.APPLICATION_JSON));

		// than
		perform.andExpect(MockMvcResultMatchers.status().isOk());
		perform.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
		perform.andExpect(MockMvcResultMatchers.jsonPath("$..clientId[0]").value(1));
		perform.andExpect(MockMvcResultMatchers.jsonPath("$..firstName[0]").value("Adam"));
		perform.andExpect(MockMvcResultMatchers.jsonPath("$..lastName[0]").value("Dec"));
		perform.andExpect(MockMvcResultMatchers.jsonPath("$..pesel[0]").value(85122413455L));
		perform.andExpect(MockMvcResultMatchers.jsonPath("$..clientId[1]").value(2));
		perform.andExpect(MockMvcResultMatchers.jsonPath("$..firstName[1]").value("Adam"));
		perform.andExpect(MockMvcResultMatchers.jsonPath("$..lastName[1]").value("Latuszek"));
		perform.andExpect(MockMvcResultMatchers.jsonPath("$..pesel[1]").value(85042413125L));
	}

	@Test
	public void shouldInvokeClientGetLastName() throws Exception {
		// given
		final String lastName = "Dec";

		// when
		ResultActions perform = this.mockMvc.perform(MockMvcRequestBuilders.get("/client/v1/lastName/" + lastName).accept(MediaType.APPLICATION_JSON));

		// than
		perform.andExpect(MockMvcResultMatchers.status().isOk());
		perform.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
		perform.andExpect(MockMvcResultMatchers.jsonPath("$..clientId[0]").value(1));
		perform.andExpect(MockMvcResultMatchers.jsonPath("$..firstName[0]").value("Adam"));
		perform.andExpect(MockMvcResultMatchers.jsonPath("$..lastName[0]").value("Dec"));
		perform.andExpect(MockMvcResultMatchers.jsonPath("$..pesel[0]").value(85122413455L));
	}

	@Test
	public void shouldInvokeClientGetById() throws Exception {
		// given
		final String id = "1";

		// when
		HttpHeaders httpHeaders = HttpBasicCredentialCreator.createBasicSpringCredential();
		ResultActions perform = this.mockMvc.perform(MockMvcRequestBuilders.get("/client/v1/id/" + id).headers(httpHeaders).accept(MediaType.APPLICATION_JSON));

		// than
		perform.andExpect(MockMvcResultMatchers.status().isOk());
		perform.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
		perform.andExpect(MockMvcResultMatchers.jsonPath("$..clientId[0]").value(1));
		perform.andExpect(MockMvcResultMatchers.jsonPath("$..firstName[0]").value("Adam"));
		perform.andExpect(MockMvcResultMatchers.jsonPath("$..lastName[0]").value("Dec"));
		perform.andExpect(MockMvcResultMatchers.jsonPath("$..pesel[0]").value(85122413455L));
	}

	@Test
	public void shouldNotInvokeClientGetById() throws Exception {
		// given
		final String id = "4";

		// when
		HttpHeaders httpHeaders = HttpBasicCredentialCreator.createBasicSpringCredential();
		ResultActions perform = this.mockMvc.perform(MockMvcRequestBuilders.get("/client/v1/id/" + id).headers(httpHeaders).accept(MediaType.APPLICATION_JSON));

		// than
		perform.andExpect(MockMvcResultMatchers.status().isOk());
		perform.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
		perform.andExpect(MockMvcResultMatchers.jsonPath("$..responseStatus[0]").value("ERROR"));
		perform.andExpect(MockMvcResultMatchers.jsonPath("$..reasonText[0]").value("Client with id=" + id + " has not been found."));
	}

	@Test
	public void shouldCreateNewClientSecured() throws Exception {
		// given
		NewClientRequest_1_0 request = new NewClientRequest_1_0("Kasia", "Dec", 85112412344L, "login", "pass");
		request.setCity("Lezajsk");
		request.setCountry("Poland");
		request.setEmailAddress("katarzyna.dec@smoopay.com");
		request.setPostCode("37-300");
		request.setResident(true);
		request.setStreet("Ulica");
		byte[] content = jsonUtils.convertObjectToBytes(request);

		// when
		HttpHeaders httpHeaders = HttpBasicCredentialCreator.createBasicSpringCredential();
		ResultActions perform = this.mockMvc.perform(MockMvcRequestBuilders.post("/client/v1/new", "json").headers(httpHeaders).content(content)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		// than
		perform.andExpect(MockMvcResultMatchers.status().isOk());
		perform.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
		perform.andExpect(MockMvcResultMatchers.jsonPath("$..clientId[0]").value(4));
	}
}