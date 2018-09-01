package com.finaxys.deserialization;

import java.util.Map;

import org.apache.kafka.common.serialization.Deserializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.finaxys.model.Transactions;

public class TransactionsDeserializer implements Deserializer<Transactions> {

    @Override
    public void configure(Map<String, ?> var1, boolean var2) {
    }

    @Override
    public Transactions deserialize(String var1, byte[] var2) {
        ObjectMapper mapper = new ObjectMapper();
        Transactions transactions = null;
        try {
            transactions = mapper.readValue(var2, Transactions.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return transactions;
    }

    @Override
    public void close() {
    }


}
