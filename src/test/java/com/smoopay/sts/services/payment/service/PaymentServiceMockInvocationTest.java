package com.smoopay.sts.services.payment.service;

import java.math.BigDecimal;
import java.util.Date;

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
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder;

import com.smoopay.sts.common.dto.CurrencyEnum;
import com.smoopay.sts.common.dto.payment.PaymentStatusEnum;
import com.smoopay.sts.dao.payment.PaymentCustomRepository;
import com.smoopay.sts.services.payment.pos.PosPaymentService;
import com.smoopay.sts.services.payment.pos.request.AmendPaymentRequest_1_0;
import com.smoopay.sts.services.payment.pos.request.CancelPaymentRequest_1_0;
import com.smoopay.sts.services.payment.pos.request.NewPaymentRequest_1_0;
import com.smoopay.sts.services.payment.pos.response.AmendPaymentResponse_1_0;
import com.smoopay.sts.services.payment.pos.response.CancelPaymentResponse_1_0;
import com.smoopay.sts.services.payment.pos.response.NewPaymentResponse_1_0;
import com.smoopay.sts.utils.JsonUtils;

/**
 * http ://java-success.blogspot.com/2012/12/unit-testing-spring-mvc-controllers
 * -for.html
 * 
 * @author Adam Dec
 */
public class PaymentServiceMockInvocationTest {

	@InjectMocks
	private PosPaymentService posPaymentService;

	@Mock
	private PaymentCustomRepository paymentCustomRepository;

	private NewPaymentResponse_1_0 newResponse;
	private CancelPaymentResponse_1_0 cancelResponse;
	private AmendPaymentResponse_1_0 amendResposne;

	private JsonUtils jsonUtils;

	@Before
	public void setup() {
		posPaymentService = new PosPaymentService();
		MockitoAnnotations.initMocks(this);
		jsonUtils = new JsonUtils();
	}

	@Test
	public void shouldInvokeNewPayment() throws Exception {
		// given
		NewPaymentRequest_1_0 request = new NewPaymentRequest_1_0(1L, 1L, new BigDecimal("100.0"), CurrencyEnum.PLN, new Date());
		byte[] content = jsonUtils.convertObjectToBytes(request);

		newResponse = new NewPaymentResponse_1_0(1L, new BigDecimal("100.0"), CurrencyEnum.PLN);
		Mockito.reset(paymentCustomRepository);
		Mockito.when(paymentCustomRepository.createOrAddNewPayment((NewPaymentRequest_1_0) Mockito.any())).thenReturn(newResponse);

		StandaloneMockMvcBuilder builder = MockMvcBuilders.standaloneSetup(posPaymentService);

		// when
		ResultActions perform = builder.build().perform(
				MockMvcRequestBuilders.post("/payment/v1/new", "json").content(content).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		// than
		Mockito.verify(paymentCustomRepository, Mockito.times(1)).createOrAddNewPayment((NewPaymentRequest_1_0) Mockito.any());
		perform.andExpect(MockMvcResultMatchers.status().isOk());
		perform.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
		perform.andExpect(MockMvcResultMatchers.jsonPath("$.paymentId").value(1));
		perform.andExpect(MockMvcResultMatchers.jsonPath("$.amount").value(100.0));
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
		ResultActions perform = MockMvcBuilders.standaloneSetup(posPaymentService).build()
				.perform(MockMvcRequestBuilders.post("/payment/v1/new", "json").content(content).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		// than
		perform.andExpect(MockMvcResultMatchers.status().isOk());
		perform.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
		perform.andExpect(MockMvcResultMatchers.jsonPath("$.responseStatus").value("ERROR"));
		perform.andExpect(MockMvcResultMatchers.jsonPath("$.reasonText").value("INPUT_VALIDATION_ERROR"));
	}

	@Test
	public void shouldInvokePaymentStatus() throws Exception {
		// given
		newResponse = new NewPaymentResponse_1_0(1L, new BigDecimal("100.0"), CurrencyEnum.PLN, PaymentStatusEnum.PENDING);
		Mockito.reset(paymentCustomRepository);
		Mockito.when(paymentCustomRepository.checkPaymentStatus((Long) Mockito.any())).thenReturn(newResponse);

		// when
		ResultActions perform = MockMvcBuilders.standaloneSetup(posPaymentService).build()
				.perform(MockMvcRequestBuilders.get("/payment/v1/status/1").accept(MediaType.APPLICATION_JSON));

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

		cancelResponse = new CancelPaymentResponse_1_0(1L, new BigDecimal("100.0"), CurrencyEnum.PLN, PaymentStatusEnum.CANCELED);
		Mockito.reset(paymentCustomRepository);
		Mockito.when(paymentCustomRepository.cancelPayment((CancelPaymentRequest_1_0) Mockito.any())).thenReturn(cancelResponse);

		// when
		ResultActions perform = MockMvcBuilders.standaloneSetup(posPaymentService).build()
				.perform(MockMvcRequestBuilders.post("/payment/v1/cancel", "json").content(content).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

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

		amendResposne = new AmendPaymentResponse_1_0(5L, new BigDecimal("30.0"), CurrencyEnum.USD);
		Mockito.reset(paymentCustomRepository);
		Mockito.when(paymentCustomRepository.amendPayment((AmendPaymentRequest_1_0) Mockito.any())).thenReturn(amendResposne);

		// when
		ResultActions perform = MockMvcBuilders.standaloneSetup(posPaymentService).build()
				.perform(MockMvcRequestBuilders.post("/payment/v1/amend", "json").content(content).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		// than
		perform.andExpect(MockMvcResultMatchers.status().isOk());
		perform.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
		perform.andExpect(MockMvcResultMatchers.jsonPath("$.paymentId").value(5));
		perform.andExpect(MockMvcResultMatchers.jsonPath("$.amount").value(30.0));
		perform.andExpect(MockMvcResultMatchers.jsonPath("$.currency").value("USD"));
		perform.andExpect(MockMvcResultMatchers.jsonPath("$.amendPaymentStatus").value("PENDING"));
	}
}