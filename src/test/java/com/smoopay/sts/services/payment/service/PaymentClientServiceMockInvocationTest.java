package com.smoopay.sts.services.payment.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.smoopay.sts.common.dto.CurrencyEnum;
import com.smoopay.sts.common.dto.payment.PaymentStatusEnum;
import com.smoopay.sts.dao.client.ClientCustomRepository;
import com.smoopay.sts.services.payment.client.ClientPaymentService;
import com.smoopay.sts.services.payment.client.response.PaymentResponse_1_0;

/**
 * http ://java-success.blogspot.com/2012/12/unit-testing-spring-mvc-controllers
 * -for.html
 * 
 * @author Adam Dec
 * 
 */
public class PaymentClientServiceMockInvocationTest {

	@InjectMocks
	private ClientPaymentService clientPaymentService;

	@Mock
	private ClientCustomRepository clientCustomRepository;

	@Before
	public void setup() {
		clientPaymentService = new ClientPaymentService();
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void shouldInvokePaymentClientGet() throws Exception {
		// given
		List<PaymentResponse_1_0> result = new ArrayList<>(2);
		result.add(new PaymentResponse_1_0(1L, "Tesco", new BigDecimal("100.0"), CurrencyEnum.PLN, PaymentStatusEnum.NEW, new Date()));
		result.add(new PaymentResponse_1_0(2L, "Orlen", new BigDecimal("200.0"), CurrencyEnum.USD, PaymentStatusEnum.FILLED, new Date()));
		Mockito.reset(clientCustomRepository);
		Mockito.when(clientCustomRepository.findClientPaymentsFromDate((Long) Mockito.any(), (Date) Mockito.any())).thenReturn(result);

		// when
		ResultActions perform = MockMvcBuilders.standaloneSetup(clientPaymentService).build()
				.perform(MockMvcRequestBuilders.get("/payment/client/v1/get/1/01-01-2013 12:12:12").accept(MediaType.APPLICATION_JSON));

		// than
		Mockito.verify(clientCustomRepository, Mockito.times(1)).findClientPaymentsFromDate((Long) Mockito.any(), (Date) Mockito.any());
		perform.andExpect(MockMvcResultMatchers.status().isOk());
		perform.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
		perform.andExpect(MockMvcResultMatchers.jsonPath("$..paymentId[0]").value(1));
		perform.andExpect(MockMvcResultMatchers.jsonPath("$..amount[0]").value(100.0));
		perform.andExpect(MockMvcResultMatchers.jsonPath("$..currency[0]").value("PLN"));
		perform.andExpect(MockMvcResultMatchers.jsonPath("$..paymentStatus[0]").value("NEW"));
		perform.andExpect(MockMvcResultMatchers.jsonPath("$..paymentId[1]").value(2));
		perform.andExpect(MockMvcResultMatchers.jsonPath("$..amount[1]").value(200.0));
		perform.andExpect(MockMvcResultMatchers.jsonPath("$..currency[1]").value("USD"));
		perform.andExpect(MockMvcResultMatchers.jsonPath("$..paymentStatus[1]").value("FILLED"));
	}
}