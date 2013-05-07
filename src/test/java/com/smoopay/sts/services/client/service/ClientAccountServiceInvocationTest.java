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
import com.smoopay.sts.services.account.client.request.NewClientAccountRequest_1_0;
import com.smoopay.sts.utils.HttpBasicCredentialCreator;

/**
 * @author Adam Dec
 */
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class ClientAccountServiceInvocationTest extends AbstractServiceIntegrationTest {

	@Test
	public void shouldCreateNewClientAccountSecured() throws Exception {
		// given
		NewClientAccountRequest_1_0 request = new NewClientAccountRequest_1_0(1L, "PLN", "Alior", "1");
		byte[] content = jsonUtils.convertObjectToBytes(request);

		// when
		HttpHeaders httpHeaders = HttpBasicCredentialCreator.createBasicSpringCredential();
		ResultActions perform = this.mockMvc.perform(MockMvcRequestBuilders.post("/client/account/v1/new", "json").headers(httpHeaders).content(content)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		// than
		perform.andExpect(MockMvcResultMatchers.status().isOk());
		perform.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
		perform.andExpect(MockMvcResultMatchers.jsonPath("$.clientAccountId").value(5));
		perform.andExpect(MockMvcResultMatchers.jsonPath("$.virtualAccNo").value("1-Alior"));
	}

	@Test
	public void shouldNotCreateNewClientAccountValidationErrors() throws Exception {
		// given
		NewClientAccountRequest_1_0 request = new NewClientAccountRequest_1_0(1L, null, null, "1");
		byte[] content = jsonUtils.convertObjectToBytes(request);

		// when
		HttpHeaders httpHeaders = HttpBasicCredentialCreator.createBasicSpringCredential();
		ResultActions perform = this.mockMvc.perform(MockMvcRequestBuilders.post("/client/account/v1/new", "json").headers(httpHeaders).content(content)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		// than
		perform.andExpect(MockMvcResultMatchers.status().isOk());
		perform.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
		perform.andExpect(MockMvcResultMatchers.jsonPath("$.responseStatus").value("ERROR"));
		perform.andExpect(MockMvcResultMatchers.jsonPath("$.reasonText").value("INPUT_VALIDATION_ERROR"));
	}

	@Test
	public void shouldGetClientAccountById() throws Exception {
		// given
		final Long clientId = 1L;

		// when
		HttpHeaders httpHeaders = HttpBasicCredentialCreator.createBasicSpringCredential();
		ResultActions perform = this.mockMvc.perform(MockMvcRequestBuilders.get("/client/account/v1/get/" + clientId).headers(httpHeaders).accept(MediaType.APPLICATION_JSON));

		// than
		perform.andExpect(MockMvcResultMatchers.status().isOk());
		perform.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
		perform.andExpect(MockMvcResultMatchers.jsonPath("$..virtualAccNo[0]").value("1-Alior Bank"));
		perform.andExpect(MockMvcResultMatchers.jsonPath("$..accountStatus[0]").value("ACTIVE"));
		perform.andExpect(MockMvcResultMatchers.jsonPath("$..currency[0]").value("PLN"));
		perform.andExpect(MockMvcResultMatchers.jsonPath("$..cashAccNRB[0]").value("12 2490 5678 1234 5678 9012 3456"));
	}

	@Test
	public void shouldGetClientAccountByClientIdAndCurrency() throws Exception {
		// given
		final Long clientId = 1L;
		final String currency = "PLN";

		// when
		ResultActions perform = this.mockMvc.perform(MockMvcRequestBuilders.get("/client/account/v1/currency/" + clientId + "/" + currency).accept(MediaType.APPLICATION_JSON));

		// than
		perform.andExpect(MockMvcResultMatchers.status().isOk());
		perform.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
		perform.andExpect(MockMvcResultMatchers.jsonPath("$..virtualAccNo[0]").value("1-Alior Bank"));
		perform.andExpect(MockMvcResultMatchers.jsonPath("$..accountStatus[0]").value("ACTIVE"));
		perform.andExpect(MockMvcResultMatchers.jsonPath("$..currency[0]").value("PLN"));
		perform.andExpect(MockMvcResultMatchers.jsonPath("$..cashAccNRB[0]").value("12 2490 5678 1234 5678 9012 3456"));
	}

	@Test
	public void shouldGetClientAccountByClientIdAndStatus() throws Exception {
		// given
		final Long clientId = 1L;
		final String status = "ACTIVE";

		// when
		ResultActions perform = this.mockMvc.perform(MockMvcRequestBuilders.get("/client/account/v1/status/" + clientId + "/" + status).accept(MediaType.APPLICATION_JSON));

		// than
		perform.andExpect(MockMvcResultMatchers.status().isOk());
		perform.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
		perform.andExpect(MockMvcResultMatchers.jsonPath("$..virtualAccNo[0]").value("1-Alior Bank"));
		perform.andExpect(MockMvcResultMatchers.jsonPath("$..accountStatus[0]").value("ACTIVE"));
		perform.andExpect(MockMvcResultMatchers.jsonPath("$..currency[0]").value("PLN"));
		perform.andExpect(MockMvcResultMatchers.jsonPath("$..cashAccNRB[0]").value("12 2490 5678 1234 5678 9012 3456"));
	}

	@Test
	public void shouldGetClientAccountByClientIdAndVirtualNumber() throws Exception {
		// given
		final Long clientId = 2L;
		final String virtualNumber = "3-BZ WBK";

		// when
		ResultActions perform = this.mockMvc.perform(MockMvcRequestBuilders.get("/client/account/v1/virtualNumber/" + clientId + "/" + virtualNumber).accept(
				MediaType.APPLICATION_JSON));

		// than
		perform.andExpect(MockMvcResultMatchers.status().isOk());
		perform.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
		perform.andExpect(MockMvcResultMatchers.jsonPath("$..virtualAccNo[0]").value("3-BZ WBK"));
		perform.andExpect(MockMvcResultMatchers.jsonPath("$..accountStatus[0]").value("ACTIVE"));
		perform.andExpect(MockMvcResultMatchers.jsonPath("$..currency[0]").value("PLN"));
		perform.andExpect(MockMvcResultMatchers.jsonPath("$..cashAccNRB[0]").value("11 1090 5678 1234 5678 9012 3456"));
	}
}