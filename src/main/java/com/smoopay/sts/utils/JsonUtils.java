package com.smoopay.sts.utils;

import java.io.IOException;

import javax.annotation.PostConstruct;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JsonUtils {

	@Autowired
	private ObjectMapper mapper;

	@PostConstruct
	public void init() {
		mapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
	}

	public byte[] convertObjectToBytes(Object object) throws JsonGenerationException, JsonMappingException, IOException {
		return mapper.writeValueAsBytes(object);
	}
}