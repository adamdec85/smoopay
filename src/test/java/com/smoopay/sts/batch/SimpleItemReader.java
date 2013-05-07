package com.smoopay.sts.batch;

import org.springframework.batch.item.ItemReader;
import org.springframework.stereotype.Component;

@Component("reader")
public class SimpleItemReader implements ItemReader<String> {

	private String[] input = { "Smoopay!", null };

	private int index = 0;

	public String read() throws Exception {
		if (index < input.length) {
			return input[index++];
		} else {
			return null;
		}
	}
}