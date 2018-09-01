package com.finaxys.schema;

import com.finaxys.model.Test;
import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.api.common.serialization.SerializationSchema;
import org.apache.flink.api.common.typeinfo.TypeInformation;

import java.io.IOException;

public class TestSchema implements DeserializationSchema<Test>, SerializationSchema<Test> {


    private static final long serialVersionUID = 7793410336185483054L;

    @Override
    public Test deserialize(byte[] message) throws IOException {
        return Test.fromString(new String(message));
    }

    @Override
    public boolean isEndOfStream(Test nextElement) {
        return false;
    }

    @Override
    public byte[] serialize(Test element) {
        return element.toString().getBytes();
    }

    @Override
    public TypeInformation<Test> getProducedType() {
        return TypeInformation.of(Test.class);
    }
}
