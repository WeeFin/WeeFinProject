package com.finaxys.serialization;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.finaxys.model.Test;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

public class TestSerializer implements Serializer<Test> {
    @Override
    public void configure(Map<String, ?> map, boolean b) {

    }

    @Override
    public byte[] serialize(String s, Test test) {
        byte[] retVal = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            retVal = objectMapper.writeValueAsString(test).getBytes();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retVal;
    }

    @Override
    public void close() {

    }
}
