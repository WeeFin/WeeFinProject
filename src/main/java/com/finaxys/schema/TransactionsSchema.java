package com.finaxys.schema;

import java.io.IOException;

import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.api.common.serialization.SerializationSchema;
import org.apache.flink.api.common.typeinfo.TypeInformation;

import com.finaxys.model.Transactions;

public class TransactionsSchema implements DeserializationSchema<Transactions>, SerializationSchema<Transactions> {

    
    private static final long serialVersionUID = 6680074076962797876L;

    @Override
    public TypeInformation<Transactions> getProducedType() {
        return TypeInformation.of(Transactions.class);
    }

    @Override
    public byte[] serialize(Transactions transaction) {
        return transaction.toString().getBytes();
    }

    @Override
    public Transactions deserialize(byte[] message) throws IOException {
        return Transactions.fromString(new String(message));
    }

    @Override
    public boolean isEndOfStream(Transactions nextElement) {
        return false;
    }

}
