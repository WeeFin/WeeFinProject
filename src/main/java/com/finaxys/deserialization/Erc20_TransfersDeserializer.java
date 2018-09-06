package com.finaxys.deserialization;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.finaxys.model.Erc20_Transfers;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

/**
 * Each deserializer model class is used to retrieve model class objects from JSON information in the Kafka Topic
 */
public class Erc20_TransfersDeserializer implements Deserializer<Erc20_Transfers> {

    @Override
    public void configure(Map<String, ?> var1, boolean var2) {
    }

    @Override
    public Erc20_Transfers deserialize(String var1, byte[] var2) {
        ObjectMapper mapper = new ObjectMapper();
        Erc20_Transfers erc20_Transfers = null;
        try {
            erc20_Transfers = mapper.readValue(var2, Erc20_Transfers.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return erc20_Transfers;
    }

    @Override
    public void close() {
    }


}
