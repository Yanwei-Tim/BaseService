package com.unistrong.tracker.model.serialize;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

public class DeviceSosSerializer extends JsonSerializer<String> {

	@Override
	public void serialize(String value, JsonGenerator jsonGenerator, SerializerProvider provider)
			throws IOException, JsonProcessingException {
		String[] valueStrs = value.split(",");
		String[] valueNums = new String[valueStrs.length];
		for (int i = 0; i < valueNums.length; i++) {
			valueNums[i] = valueStrs[i].trim();
		}
		jsonGenerator.writeObject(valueNums);
	}
}
