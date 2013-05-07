package com.smoopay.sts.batch;

import org.springframework.batch.repeat.RepeatCallback;
import org.springframework.batch.repeat.RepeatContext;
import org.springframework.batch.repeat.RepeatStatus;

public class SimpleRepeatCallback implements RepeatCallback {

	private SimpleItemProcessor processor;

	private SimpleItemReader reader;

	public void setItemReader(SimpleItemReader itemReader) {
		this.reader = itemReader;
	}

	public void setItemProcessor(SimpleItemProcessor processor) {
		this.processor = processor;
	}

	public RepeatStatus doInIteration(RepeatContext repeatContext) throws Exception {
		String data = reader.read();
		if (data == null) {
			return RepeatStatus.FINISHED;
		}

		String processedData = processor.process(data);
		System.out.println("For input data " + data + ", the processed data is " + processedData);

		return RepeatStatus.CONTINUABLE;
	}
}