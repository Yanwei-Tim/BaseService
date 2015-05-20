package com.unistrong.tracker.model.serialize;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

public class FenceCenterSerializer extends JsonSerializer<String> {

	@Override
	public void serialize(String value, JsonGenerator jsonGenerator, SerializerProvider provider)
			throws IOException, JsonProcessingException {
		value = value.replaceAll("\\[", "");
		value = value.replaceAll("\\]", "");
		String[] valueStrs = value.split(",");
		Double[] valueNums = new Double[valueStrs.length];
		for (int i = 0; i < valueNums.length; i++) {
			valueNums[i] = Double.parseDouble(valueStrs[i].trim());
		}
		jsonGenerator.writeObject(valueNums);
	}
}
