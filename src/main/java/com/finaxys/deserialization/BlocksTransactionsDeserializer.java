package com.finaxys.deserialization;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.finaxys.model.BlocksTransactions;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

/**
 * Each deserializer model class is used to retrieve model class objects from JSON information in the Kafka Topic
 */
public class BlocksTransactionsDeserializer implements Deserializer<BlocksTransactions> {

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    @Override
    public BlocksTransactions deserialize(String topic, byte[] data) {
        ObjectMapper mapper = new ObjectMapper();
        BlocksTransactions blocksTransactions = null;
        try {
            blocksTransactions = mapper.readValue(data, BlocksTransactions.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return blocksTransactions;
    }

    @Override
    public void close() {

    }
}
