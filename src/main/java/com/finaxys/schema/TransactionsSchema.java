package com.finaxys.schema;

import com.finaxys.model.Transactions;
import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.api.common.serialization.SerializationSchema;
import org.apache.flink.api.common.typeinfo.TypeInformation;

import java.io.IOException;

/**
 * Serialization schema for Transactions class
 */
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
