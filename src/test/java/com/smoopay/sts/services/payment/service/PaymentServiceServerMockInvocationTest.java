package com.smoopay.sts.services.payment.service;

import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.client.response.MockRestResponseCreators;
import org.springframework.web.client.RestTemplate;

import com.smoopay.sts.common.dto.CurrencyEnum;
import com.smoopay.sts.common.dto.payment.PaymentStatusEnum;
import com.smoopay.sts.services.payment.pos.request.NewPaymentRequest_1_0;
import com.smoopay.sts.services.payment.pos.response.NewPaymentResponse_1_0;

/**
 * http ://java-success.blogspot.com/2012/12/unit-testing-spring-mvc-controllers
 * -for.html
 * 
 * @author Adam Dec
 * 
 */
public class PaymentServiceServerMockInvocationTest {

	private static final Logger logger = LoggerFactory.getLogger(PaymentServiceServerMockInvocationTest.class.getName());

	private MockRestServiceServer mockServer;
	private ClientHttpRequestFactory factory;
	private RestTemplate restTemplate;

	@Before
	public void setup() {
		this.restTemplate = new RestTemplate();
		this.mockServer = MockRestServiceServer.createServer(restTemplate);
		this.factory = restTemplate.getRequestFactory();
	}

	@Test
	public void shouldCreateNewPayment() throws Exception {
		// given
		if (logger.isDebugEnabled()) {
			logger.debug("ClientHttpRequestFactory=" + factory);
		}
		NewPaymentResponse_1_0 response = new NewPaymentResponse_1_0(1L, new BigDecimal("100.0"), CurrencyEnum.PLN, PaymentStatusEnum.PENDING);
		ObjectMapper mapper = new ObjectMapper();
		mockServer.expect(MockRestRequestMatchers.requestTo("/payment/v1/new")).andExpect(MockRestRequestMatchers.method(HttpMethod.POST))
				.andRespond(MockRestResponseCreators.withSuccess(mapper.writeValueAsString(response), MediaType.APPLICATION_JSON));

		NewPaymentRequest_1_0 request = new NewPaymentRequest_1_0(1L, 1L, new BigDecimal("100.0"), CurrencyEnum.PLN, new Date());
		byte[] content = mapper.writeValueAsBytes(request);
		if (logger.isDebugEnabled()) {
			logger.debug("JSON request=" + new String(content));
		}

		// when
		@SuppressWarnings("unused")
		NewPaymentResponse_1_0 res = restTemplate.postForObject("/payment/v1/new", request, NewPaymentResponse_1_0.class);

		// than
		mockServer.verify();
	}
}