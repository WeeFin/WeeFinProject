package com.finaxys.schema;

import com.finaxys.model.BlocksTransactions;
import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.api.common.serialization.SerializationSchema;
import org.apache.flink.api.common.typeinfo.TypeInformation;

import java.io.IOException;

public class BlocksTransactionsSchema implements DeserializationSchema<BlocksTransactions>, SerializationSchema<BlocksTransactions> {

    private static final long serialVersionUID = 6444283837183255764L;


    @Override
    public BlocksTransactions deserialize(byte[] message) throws IOException {
        return BlocksTransactions.fromString(new String(message));
    }

    @Override
    public boolean isEndOfStream(BlocksTransactions nextElement) {
        return false;
    }

    @Override
    public byte[] serialize(BlocksTransactions element) {
        return element.toString().getBytes();
    }

    @Override
    public TypeInformation<BlocksTransactions> getProducedType() {
        return TypeInformation.of(BlocksTransactions.class);
    }
}

