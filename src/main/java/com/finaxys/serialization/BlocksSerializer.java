package com.finaxys.serialization;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.finaxys.model.Blocks;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

/**
 * Each serializer model class is used for Kafka Broker. Kafka stores and transmit the bytes got by serialization process
 * in it's queue
 */
public class BlocksSerializer implements Serializer<Blocks> {

    @Override
    public void configure(Map<String, ?> var1, boolean var2) {

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

    }

}
