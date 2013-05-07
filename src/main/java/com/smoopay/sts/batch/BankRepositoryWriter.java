package com.smoopay.sts.batch;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Service;

@Service
public class BankRepositoryWriter implements ItemWriter<String> {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public void write(List<? extends String> items) {
		if (logger.isDebugEnabled()) {
			logger.debug("--->>> Sending bank account=" + items.get(0) + " to ELIXIR-OK");
		}
	}
}