package com.finaxys.deserialization;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.finaxys.model.Test;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

public class TestDeserializer implements Deserializer<Test> {


    @Override
    public void configure(Map<String, ?> map, boolean b) {

    }

    @Override
    public Test deserialize(String s, byte[] bytes) {
        ObjectMapper mapper = new ObjectMapper();
        Test test = null;
        try {
            test = mapper.readValue(bytes, Test.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return test;
    }

    @Override
    public void close() {

    }
}
