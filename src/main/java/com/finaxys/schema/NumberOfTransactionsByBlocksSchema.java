package com.finaxys.schema;

import com.finaxys.queriesModel.NumberOfTransactionsByBlocks;
import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.api.common.serialization.SerializationSchema;
import org.apache.flink.api.common.typeinfo.TypeInformation;

import java.io.IOException;

/**
 * Serialization schema for NumberOfTransactionsByBlocks class
 */
public class NumberOfTransactionsByBlocksSchema implements DeserializationSchema<NumberOfTransactionsByBlocks>, SerializationSchema<NumberOfTransactionsByBlocks> {


    private static final long serialVersionUID = 7793410336185483054L;

    @Override
    public NumberOfTransactionsByBlocks deserialize(byte[] message) throws IOException {
        return NumberOfTransactionsByBlocks.fromString(new String(message));
    }

    @Override
    public boolean isEndOfStream(NumberOfTransactionsByBlocks nextElement) {
        return false;
    }

    @Override
    public byte[] serialize(NumberOfTransactionsByBlocks element) {
        return element.toString().getBytes();
    }

    @Override
    public TypeInformation<NumberOfTransactionsByBlocks> getProducedType() {
        return TypeInformation.of(NumberOfTransactionsByBlocks.class);
    }
}
