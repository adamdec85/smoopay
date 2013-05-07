package com.smoopay.sts.services.payment.service;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.smoopay.sts.AbstractServiceIntegrationTest;
import com.smoopay.sts.common.dto.CurrencyEnum;
import com.smoopay.sts.services.payment.pos.request.AmendPaymentRequest_1_0;
import com.smoopay.sts.services.payment.pos.request.CancelPaymentRequest_1_0;
import com.smoopay.sts.services.payment.pos.request.NewPaymentRequest_1_0;

/**
 * https://github.com/jeffsheets/MockRestServiceServerExample/blob/master/
 * SimpleRestServiceFunctionalTest.java
 * 
 * @author Adam Dec
 * 
 */
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class PaymentServiceInvocationTest extends AbstractServiceIntegrationTest {

	@Test
	public void shouldInvokeCreateNewPayment() throws Exception {
		// given
		NewPaymentRequest_1_0 request = new NewPaymentRequest_1_0(1L, 3L, new BigDecimal("300.0"), CurrencyEnum.PLN, new Date());
		byte[] content = jsonUtils.convertObjectToBytes(request);

		// when
		ResultActions perform = this.mockMvc.perform(MockMvcRequestBuilders.post("/payment/v1/new", "json").content(content).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));

		// than
		perform.andExpect(MockMvcResultMatchers.status().isOk());
		perform.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
		perform.andExpect(MockMvcResultMatchers.jsonPath("$.paymentId").value(5));
		perform.andExpect(MockMvcResultMatchers.jsonPath("$.amount").value(300.0));
		perform.andExpect(MockMvcResultMatchers.jsonPath("$.currency").value("PLN"));
		perform.andExpect(MockMvcResultMatchers.jsonPath("$.paymentStatus").value("PENDING"));
	}

	@Test
	public void shouldTestRequestValidationInNewPayment() throws Exception {
		// given
		NewPaymentRequest_1_0 request = new NewPaymentRequest_1_0();
		request.setMerchantId(1L);
		byte[] content = jsonUtils.convertObjectToBytes(request);

		// when
		ResultActions perform = this.mockMvc.perform(MockMvcRequestBuilders.post("/payment/v1/new", "json").content(content).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));

		// than
		perform.andExpect(MockMvcResultMatchers.status().isOk());
		perform.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
		perform.andExpect(MockMvcResultMatchers.jsonPath("$.responseStatus").value("ERROR"));
		perform.andExpect(MockMvcResultMatchers.jsonPath("$.reasonText").value("INPUT_VALIDATION_ERROR"));
	}

	@Test
	public void shouldInvokePaymentStatus() throws Exception {
		// given
		final long paymentId = 1L;

		// when
		ResultActions perform = this.mockMvc.perform(MockMvcRequestBuilders.get("/payment/v1/status/" + paymentId).accept(MediaType.APPLICATION_JSON));

		// than
		perform.andExpect(MockMvcResultMatchers.status().isOk());
		perform.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
		perform.andExpect(MockMvcResultMatchers.jsonPath("$.paymentId").value(1));
		perform.andExpect(MockMvcResultMatchers.jsonPath("$.amount").value(100.0));
		perform.andExpect(MockMvcResultMatchers.jsonPath("$.currency").value("PLN"));
		perform.andExpect(MockMvcResultMatchers.jsonPath("$.paymentStatus").value("PENDING"));
	}

	@Test
	public void shouldInvokeCancelPayment() throws Exception {
		// given
		CancelPaymentRequest_1_0 request = new CancelPaymentRequest_1_0(1L);
		byte[] content = jsonUtils.convertObjectToBytes(request);

		// when
		ResultActions perform = this.mockMvc.perform(MockMvcRequestBuilders.post("/payment/v1/cancel", "json").content(content).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));

		// than
		perform.andExpect(MockMvcResultMatchers.status().isOk());
		perform.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
		perform.andExpect(MockMvcResultMatchers.jsonPath("$.paymentId").value(1));
		perform.andExpect(MockMvcResultMatchers.jsonPath("$.amount").value(100.0));
		perform.andExpect(MockMvcResultMatchers.jsonPath("$.currency").value("PLN"));
		perform.andExpect(MockMvcResultMatchers.jsonPath("$.cancelPaymentStatus").value("CANCELED"));
	}

	@Test
	public void shouldInvokeAmendPayment() throws Exception {
		// given
		AmendPaymentRequest_1_0 request = new AmendPaymentRequest_1_0(1L, 1L, 1L, new BigDecimal("30.0"), CurrencyEnum.USD);
		byte[] content = jsonUtils.convertObjectToBytes(request);

		// when
		ResultActions perform = this.mockMvc.perform(MockMvcRequestBuilders.post("/payment/v1/amend", "json").content(content).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));

		// than
		perform.andExpect(MockMvcResultMatchers.status().isOk());
		perform.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
		perform.andExpect(MockMvcResultMatchers.jsonPath("$.paymentId").value(5));
		perform.andExpect(MockMvcResultMatchers.jsonPath("$.amount").value(30.0));
		perform.andExpect(MockMvcResultMatchers.jsonPath("$.currency").value("USD"));
		perform.andExpect(MockMvcResultMatchers.jsonPath("$.amendPaymentStatus").value("PENDING"));
	}
}