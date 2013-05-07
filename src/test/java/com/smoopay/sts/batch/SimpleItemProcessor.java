package com.smoopay.sts.batch;

import org.springframework.batch.item.ItemProcessor;

public class SimpleItemProcessor implements ItemProcessor<String, String> {

	@Override
	public String process(String item) throws Exception {
		return item.toUpperCase();
	}
}