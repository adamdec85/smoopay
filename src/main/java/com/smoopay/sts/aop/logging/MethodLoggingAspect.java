package com.smoopay.sts.aop.logging;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Component
@Aspect
public class MethodLoggingAspect {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Around(value = "@annotation(loggable)", argNames = "joinPoint, loggable")
	public Object logTimeMethod(ProceedingJoinPoint joinPoint, Loggable loggable) throws Throwable {
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		Object retVal = joinPoint.proceed(joinPoint.getArgs());
		stopWatch.stop();

		StringBuilder logMessage = new StringBuilder();
		logMessage.append(joinPoint.getTarget().getClass().getName());
		logMessage.append(".");
		logMessage.append(joinPoint.getSignature().getName());
		logMessage.append("(");

		Object[] args = joinPoint.getArgs();
		for (int i = 0; i < args.length; i++) {
			logMessage.append(args[i]).append(",");
		}
		if (args.length > 0) {
			logMessage.deleteCharAt(logMessage.length() - 1);
		}

		logMessage.append(")");
		logMessage.append(" execution time: ");
		logMessage.append(stopWatch.getTotalTimeMillis());
		logMessage.append(" ms");
		if (LogLevel.INFO.equals(loggable.value())) {
			logger.info(logMessage.toString());
		} else if (LogLevel.DEBUG.equals(loggable.value())) {
			logger.debug(logMessage.toString());
		} else if (LogLevel.ERROR.equals(loggable.value())) {
			logger.error(logMessage.toString());
		} else if (LogLevel.WARN.equals(loggable.value())) {
			logger.warn(logMessage.toString());
		} else if (LogLevel.TRACE.equals(loggable.value())) {
			logger.trace(logMessage.toString());
		}
		return retVal;
	}
}