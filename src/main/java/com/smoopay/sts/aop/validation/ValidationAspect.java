package com.smoopay.sts.aop.validation;

import java.util.List;
import java.util.Locale;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.bind.annotation.RequestMapping;

import com.smoopay.sts.services.rest.response.BaseResponse;
import com.smoopay.sts.services.rest.response.ResponseStatus;

@Component
@Aspect
public class ValidationAspect {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private Validator validator;

	@Pointcut(value = "execution(public * *(..))")
	public void anyPublicMethod() {
	}

	@Around(value = "anyPublicMethod() && @annotation(requestMapping)", argNames = "joinPoint, requestMapping")
	public Object validateField(ProceedingJoinPoint joinPoint, RequestMapping requestMapping) throws Throwable {
		BeanPropertyBindingResult bindingResult = null;
		Object[] args = joinPoint.getArgs();
		for (int i = 0; i < args.length; i++) {
			bindingResult = new BeanPropertyBindingResult(args[i], args[i].getClass().getSimpleName());
			getValidator().validate(args[i], bindingResult);
		}

		if (bindingResult.hasFieldErrors()) {
			return handleFormValidationError(createErrorResponse(joinPoint, "INPUT_VALIDATION_ERROR"), bindingResult.getFieldErrors());
		}

		Object retVal = joinPoint.proceed();

		bindingResult = new BeanPropertyBindingResult(retVal, retVal.getClass().getSimpleName());
		getValidator().validate(retVal, bindingResult);

		if (bindingResult.hasFieldErrors()) {
			return handleFormValidationError(createErrorResponse(joinPoint, "OUTPUT_VALIDATION_ERROR"), bindingResult.getFieldErrors());
		}

		return retVal;
	}

	private BaseResponse createErrorResponse(ProceedingJoinPoint joinPoint, String msg) {
		BaseResponse errorResponse = null;
		try {
			errorResponse = (BaseResponse) ((MethodSignature) joinPoint.getSignature()).getReturnType().newInstance();
		} catch (Exception exc) {
			exc.printStackTrace();
		}
		errorResponse.setResponseStatus(ResponseStatus.ERROR);
		errorResponse.setReasonText(msg);
		return errorResponse;
	}

	private BaseResponse handleFormValidationError(BaseResponse response, List<FieldError> fieldErrors) {
		Locale locale = LocaleContextHolder.getLocale();
		for (FieldError fieldError : fieldErrors) {
			String[] fieldErrorCodes = fieldError.getCodes();
			for (int index = 0; index < fieldErrorCodes.length; index++) {
				String fieldErrorCode = fieldErrorCodes[index];
				String localizedError = "";
				try {
					if (messageSource != null) {
						localizedError = messageSource.getMessage(fieldErrorCode, fieldError.getArguments(), locale);
					} else {
						localizedError = fieldError.getDefaultMessage();
					}
				} catch (NoSuchMessageException e) {
					localizedError = fieldError.getDefaultMessage();
				}
				if (localizedError != null && !localizedError.equals(fieldErrorCode)) {
					response.addFieldError(fieldError.getField(), localizedError);
					break;
				} else {
					if (isLastFieldErrorCode(index, fieldErrorCodes)) {
						response.addFieldError(fieldError.getField(), localizedError);
					}
				}
			}
		}
		return response;
	}

	private boolean isLastFieldErrorCode(int index, String[] fieldErrorCodes) {
		return index == fieldErrorCodes.length - 1;
	}

	public Validator getValidator() {
		// Do not know how to inject Mock to aspect in standalone setup
		if (validator == null) {
			validator = new LocalValidatorFactoryBean();
			try {
				((InitializingBean) validator).afterPropertiesSet();
			} catch (Exception e) {
				logger.error("Could not initialize validator!", e);
			}
		}
		return validator;
	}
}