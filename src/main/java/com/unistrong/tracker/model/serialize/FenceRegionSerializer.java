package com.unistrong.tracker.model.serialize;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import module.util.JsonUtils;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

public class FenceRegionSerializer extends JsonSerializer<String> {

	@Override
	public void serialize(String value, JsonGenerator jsonGenerator, SerializerProvider provider)
			throws IOException, JsonProcessingException {
		value = value.replaceAll("lat=", "\"lat\":");
		value = value.replaceAll("lng=", "\"lng\":");
		@SuppressWarnings("unchecked")
		List<SpotVo> spotVos = JsonUtils.json2Obj(value, ArrayList.class);
		jsonGenerator.writeObject(spotVos);
	}
}
