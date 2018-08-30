package com.finaxys.schema;

import java.io.IOException;

import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.api.common.serialization.SerializationSchema;
import org.apache.flink.api.common.typeinfo.TypeInformation;

import com.finaxys.model.Erc20_Transfers;

public class Erc20_TransfersSchema implements DeserializationSchema<Erc20_Transfers>, SerializationSchema<Erc20_Transfers> {

    /**
     *
     */
    private static final long serialVersionUID = 3048430915983835876L;

    @Override
    public TypeInformation<Erc20_Transfers> getProducedType() {
        return TypeInformation.of(Erc20_Transfers.class);
    }

    @Override
    public Erc20_Transfers deserialize(byte[] message) throws IOException {
        return Erc20_Transfers.fromString(new String(message));
    }

    @Override
    public boolean isEndOfStream(Erc20_Transfers nextElement) {
        return false;
    }

    @Override
    public byte[] serialize(Erc20_Transfers erc20_Transfers) {
        return erc20_Transfers.toString().getBytes();
    }

}
