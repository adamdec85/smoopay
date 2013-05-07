package com.smoopay.sts.services.payment.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.joda.time.DateTime;
import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.smoopay.sts.AbstractServiceIntegrationTest;
import com.smoopay.sts.common.dto.CurrencyEnum;
import com.smoopay.sts.services.payment.client.request.AckPaymentRequest_1_0;
import com.smoopay.sts.utils.HttpBasicCredentialCreator;

/**
 * https://github.com/jeffsheets/MockRestServiceServerExample/blob/master/
 * SimpleRestServiceFunctionalTest.java
 * 
 * http://www.petrikainulainen.net/programming/spring-framework/spring-data-jpa-
 * tutorial-part-six-sorting/
 * 
 * http://www.querydsl.com/static/querydsl/1.1.0/reference/html/ch02s02.html#
 * d0e362
 * 
 * @author Adam Dec
 * 
 */
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class PaymentClientServiceInvocationTest extends AbstractServiceIntegrationTest {

	@Test
	public void shouldInvokePaymentClientGet() throws Exception {
		// given
		final long id = 1L;
		DateTime now = new DateTime(new Date().getTime());
		now = now.minusMinutes(1);

		// when
		ResultActions perform = this.mockMvc.perform(MockMvcRequestBuilders.get(
				"/payment/client/v1/get/" + id + "/" + new SimpleDateFormat("dd-MM-yyyy kk:mm:ss").format(now.toDate())).accept(MediaType.APPLICATION_JSON));

		// than
		perform.andExpect(MockMvcResultMatchers.status().isOk());
		perform.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
		perform.andExpect(MockMvcResultMatchers.jsonPath("$..paymentId[0]").value(1));
		perform.andExpect(MockMvcResultMatchers.jsonPath("$..amount[0]").value(10.0));
		perform.andExpect(MockMvcResultMatchers.jsonPath("$..currency[0]").value("PLN"));
		perform.andExpect(MockMvcResultMatchers.jsonPath("$..paymentStatus[0]").value("FILLED"));
		perform.andExpect(MockMvcResultMatchers.jsonPath("$..paymentId[1]").value(2));
		perform.andExpect(MockMvcResultMatchers.jsonPath("$..amount[1]").value(200.0));
		perform.andExpect(MockMvcResultMatchers.jsonPath("$..currency[1]").value("PLN"));
		perform.andExpect(MockMvcResultMatchers.jsonPath("$..paymentStatus[1]").value("FILLED"));
		perform.andExpect(MockMvcResultMatchers.jsonPath("$..paymentId[2]").value(3));
		perform.andExpect(MockMvcResultMatchers.jsonPath("$..amount[2]").value(300.30));
		perform.andExpect(MockMvcResultMatchers.jsonPath("$..currency[2]").value("USD"));
		perform.andExpect(MockMvcResultMatchers.jsonPath("$..paymentStatus[2]").value("FILLED"));
	}

	@Test
	public void shouldClientAckPosPayment() throws Exception {
		// given
		AckPaymentRequest_1_0 request = new AckPaymentRequest_1_0(1L, "1-Alior Bank", 1L, 1L, new BigDecimal("100.0"), CurrencyEnum.PLN, new Date());
		byte[] content = jsonUtils.convertObjectToBytes(request);

		// when
		ResultActions perform = this.mockMvc.perform(MockMvcRequestBuilders.post("/payment/client/v1/ack", "json").content(content).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));

		// than
		perform.andExpect(MockMvcResultMatchers.status().isOk());
		perform.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
		perform.andExpect(MockMvcResultMatchers.jsonPath("$..paymentId[0]").value(1));
		perform.andExpect(MockMvcResultMatchers.jsonPath("$..paymentStatus[0]").value("FILLED"));
	}

	@Test
	public void shouldClientNotAckPosPaymentSecuredInvalidCredetial() throws Exception {
		// given
		AckPaymentRequest_1_0 request = new AckPaymentRequest_1_0(1L, "1-Alior", 1L, 1L, new BigDecimal("100.0"), CurrencyEnum.PLN, new Date());
		byte[] content = jsonUtils.convertObjectToBytes(request);

		// when
		HttpHeaders httpHeaders = HttpBasicCredentialCreator.createBasicCredential("aaa", "bbb");
		ResultActions perform = this.mockMvc.perform(MockMvcRequestBuilders.post("/payment/client/v1/secured_ack", "json").headers(httpHeaders).content(content)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		// than
		perform.andExpect(MockMvcResultMatchers.status().isUnauthorized());
	}

	@Test
	public void shouldClientAckPosPaymentSecured() throws Exception {
		// given
		AckPaymentRequest_1_0 request = new AckPaymentRequest_1_0(1L, "1-Alior Bank", 1L, 1L, new BigDecimal("100.0"), CurrencyEnum.PLN, new Date());
		byte[] content = jsonUtils.convertObjectToBytes(request);

		// when
		HttpHeaders httpHeaders = HttpBasicCredentialCreator.createBasicSpringCredential();
		ResultActions perform = this.mockMvc.perform(MockMvcRequestBuilders.post("/payment/client/v1/secured_ack", "json").headers(httpHeaders).content(content)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		// than
		perform.andExpect(MockMvcResultMatchers.status().isOk());
		perform.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
		perform.andExpect(MockMvcResultMatchers.jsonPath("$..paymentId[0]").value(1));
		perform.andExpect(MockMvcResultMatchers.jsonPath("$..paymentStatus[0]").value("FILLED"));
	}

	@Test
	public void shouldClientNotAckPosPaymentCausedByInsufficientFunds() throws Exception {
		// given
		AckPaymentRequest_1_0 request = new AckPaymentRequest_1_0(1L, "1-Alior Bank", 1L, 1L, new BigDecimal("110.0"), CurrencyEnum.PLN, new Date());
		byte[] content = jsonUtils.convertObjectToBytes(request);

		// when
		ResultActions perform = this.mockMvc.perform(MockMvcRequestBuilders.post("/payment/client/v1/ack", "json").content(content).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));

		// than
		perform.andExpect(MockMvcResultMatchers.status().isOk());
		perform.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
		perform.andExpect(MockMvcResultMatchers.jsonPath("$..reasonText[0]").value("Insufficient funds for cash account=Alior Bank"));
		perform.andExpect(MockMvcResultMatchers.jsonPath("$..responseStatus[0]").value("ERROR"));
	}

	@Test
	public void shouldClientNotAckPosPaymentCausedByAccountNotFound() throws Exception {
		// given
		AckPaymentRequest_1_0 request = new AckPaymentRequest_1_0(1L, "111-Alior", 1L, 1L, new BigDecimal("100.0"), CurrencyEnum.GBP, new Date());
		byte[] content = jsonUtils.convertObjectToBytes(request);

		// when
		ResultActions perform = this.mockMvc.perform(MockMvcRequestBuilders.post("/payment/client/v1/ack", "json").content(content).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));

		// than
		perform.andExpect(MockMvcResultMatchers.status().isOk());
		perform.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
		perform.andExpect(MockMvcResultMatchers.jsonPath("$..reasonText[0]").value("Can not find client account with virtualAccNo=111-Alior"));
		perform.andExpect(MockMvcResultMatchers.jsonPath("$..responseStatus[0]").value("ERROR"));
	}
}