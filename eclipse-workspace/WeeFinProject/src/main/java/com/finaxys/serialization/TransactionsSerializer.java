package com.finaxys.serialization;

import java.util.Map;

import org.apache.kafka.common.serialization.Serializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.finaxys.model.Transactions;

public class TransactionsSerializer implements Serializer<Transactions>{
	
	@Override
	public void configure(Map<String, ?> var1, boolean var2) {
		// TODO Auto-generated method stub

	}

	@Override
	public byte[] serialize(String var1, Transactions var2) {
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
