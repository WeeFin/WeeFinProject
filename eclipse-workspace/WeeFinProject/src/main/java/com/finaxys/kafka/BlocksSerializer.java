package com.finaxys.kafka;

import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.finaxys.model.Blocks;

public class BlocksSerializer implements Serializer<Blocks> {

	@Override
	public void configure(Map<String, ?> var1, boolean var2) {
		// TODO Auto-generated method stub

	}

	@Override
	public byte[] serialize(String var1, Blocks var2) {
		byte[] retVal = null;
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			retVal = objectMapper.writeValueAsString(var2).getBytes();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retVal;
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub

	}

}
