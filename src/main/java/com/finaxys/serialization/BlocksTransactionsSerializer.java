package com.finaxys.serialization;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.finaxys.model.BlocksTransactions;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

/**
 * Each serializer model class is used for Kafka Broker. Kafka stores and transmit the bytes got by serialization process
 * in it's queue
 */
public class BlocksTransactionsSerializer implements Serializer<BlocksTransactions> {
    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    @Override
    public byte[] serialize(String topic, BlocksTransactions data) {
        byte[] retVal = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            retVal = objectMapper.writeValueAsString(data).getBytes();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retVal;
    }

    @Override
    public void close() {

    }
}
