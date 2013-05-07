package com.smoopay.sts.batch;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component("writer")
public class SimpleItemWriter implements ItemWriter<Object> {

	private static final Log log = LogFactory.getLog(SimpleItemWriter.class);

	public void write(List<? extends Object> data) throws Exception {
		log.info(data);
	}
}