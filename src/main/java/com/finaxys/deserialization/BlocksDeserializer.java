package com.finaxys.deserialization;

import java.util.Map;

import org.apache.kafka.common.serialization.Deserializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.finaxys.model.Blocks;

public class BlocksDeserializer implements Deserializer<Blocks> {

    @Override
    public void configure(Map<String, ?> var1, boolean var2) {

    }

    @Override
    public Blocks deserialize(String var1, byte[] var2) {
        ObjectMapper mapper = new ObjectMapper();
        Blocks blocks = null;
        try {
            blocks = mapper.readValue(var2, Blocks.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return blocks;
    }

    @Override
    public void close() {

    }

}
