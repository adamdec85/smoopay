package com.smoopay.sts.services.payment.pos;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smoopay.sts.aop.logging.LogLevel;
import com.smoopay.sts.aop.logging.Loggable;
import com.smoopay.sts.dao.payment.PaymentCustomRepository;
import com.smoopay.sts.services.payment.pos.request.AmendPaymentRequest_1_0;
import com.smoopay.sts.services.payment.pos.request.CancelPaymentRequest_1_0;
import com.smoopay.sts.services.payment.pos.request.NewPaymentRequest_1_0;
import com.smoopay.sts.services.payment.pos.response.AmendPaymentResponse_1_0;
import com.smoopay.sts.services.payment.pos.response.CancelPaymentResponse_1_0;
import com.smoopay.sts.services.payment.pos.response.NewPaymentResponse_1_0;

@Controller
@RequestMapping("/payment/v1")
public class PosPaymentService {

	@Autowired
	private PaymentCustomRepository paymentCustomRepository;

	@Loggable(LogLevel.DEBUG)
	@RequestMapping(value = "/new", method = RequestMethod.POST, consumes = "application/json", produces = "application/json", headers = "Accept=application/json")
	public @ResponseBody
	NewPaymentResponse_1_0 newPayment(@RequestBody NewPaymentRequest_1_0 request) {
		return paymentCustomRepository.createOrAddNewPayment(request);
	}

	@Loggable(LogLevel.DEBUG)
	@RequestMapping(value = "/status/{paymentId}", method = RequestMethod.GET, produces = "application/json")
	public @Valid
	@ResponseBody
	NewPaymentResponse_1_0 paymentStatus(@NotNull @PathVariable Long paymentId) {
		return paymentCustomRepository.checkPaymentStatus(paymentId);
	}

	@Loggable(LogLevel.DEBUG)
	@RequestMapping(value = "/cancel", method = RequestMethod.POST, consumes = "application/json", produces = "application/json", headers = "Accept=application/json")
	public @ResponseBody
	CancelPaymentResponse_1_0 cancelPayment(@RequestBody CancelPaymentRequest_1_0 request) {
		return paymentCustomRepository.cancelPayment(request);
	}

	@Loggable(LogLevel.DEBUG)
	@RequestMapping(value = "/amend", method = RequestMethod.POST, consumes = "application/json", produces = "application/json", headers = "Accept=application/json")
	public @ResponseBody
	AmendPaymentResponse_1_0 amendPayment(@RequestBody AmendPaymentRequest_1_0 request) {
		return paymentCustomRepository.amendPayment(request);
	}
}