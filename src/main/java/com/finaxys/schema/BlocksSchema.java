package com.finaxys.schema;

import com.finaxys.model.Blocks;
import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.api.common.serialization.SerializationSchema;
import org.apache.flink.api.common.typeinfo.TypeInformation;

import java.io.IOException;

/**
 * Serialization schema for Blocks class
 */
public class BlocksSchema implements DeserializationSchema<Blocks>, SerializationSchema<Blocks> {

    private static final long serialVersionUID = 6444283837183255764L;

    @Override
    public TypeInformation<Blocks> getProducedType() {
        return TypeInformation.of(Blocks.class);
    }

    @Override
    public byte[] serialize(Blocks blocks) {
        return blocks.toString().getBytes();
    }

    @Override
    public Blocks deserialize(byte[] message) throws IOException {
        return Blocks.fromString(new String(message));

    }

    @Override
    public boolean isEndOfStream(Blocks nextElement) {
        return false;
    }


}
