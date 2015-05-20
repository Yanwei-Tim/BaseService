package com.unistrong.tracker.model.serialize;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import com.unistrong.tracker.model.Position;

public class LazySerializer extends JsonSerializer<Position> {

	@Override
	public void serialize(Position value, JsonGenerator jsonGenerator, SerializerProvider provider)
			throws IOException, JsonProcessingException {
		// String rs = null;// 通用方式,未测
		try {
			// rs = JsonUtils.obj2Str(value);
			value.getInfo();
		} catch (Exception e) {
			jsonGenerator.writeObject(null);
			return;
		}
		// jsonGenerator.writeObject(rs);
		jsonGenerator.writeObject(value);
	}
}
