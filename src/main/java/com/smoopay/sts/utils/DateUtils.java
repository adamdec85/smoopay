package com.smoopay.sts.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.jcip.annotations.ThreadSafe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@ThreadSafe
@Service
@Scope(value = "thread", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class DateUtils {

	private static final Logger logger = LoggerFactory.getLogger(DateUtils.class.getClass());

	private final SimpleDateFormat dateTimeFormatter = new SimpleDateFormat("dd-MM-yyyy kk:mm:ss");

	public Date convert(String date) {
		try {
			return dateTimeFormatter.parse(date);
		} catch (ParseException e) {
			logger.error("Could not parse date=" + date);
			return null;
		}
	}

	public String convert(Date date) {
		if (date != null) {
			return dateTimeFormatter.format(date);
		} else {
			return null;
		}
	}
}