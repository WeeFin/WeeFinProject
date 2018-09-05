package com.finaxys.model;

import com.fasterxml.jackson.databind.ObjectMapper;

public class NumberOfTransactionsByBlocks {

    public String block_number;
    public long nbTransactions;

    public NumberOfTransactionsByBlocks() {
    }

    public NumberOfTransactionsByBlocks(String block_number, long nbTransactions) {
        this.block_number = block_number;
        this.nbTransactions = nbTransactions;
    }

    public static NumberOfTransactionsByBlocks fromString(String test) {
        ObjectMapper mapper = new ObjectMapper();
        NumberOfTransactionsByBlocks t = null;
        try {
            t = mapper.readValue(test, NumberOfTransactionsByBlocks.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("NumberOfTransactionsByBlocks{");
        sb.append("block_number='").append(block_number).append('\'');
        sb.append(", nbTransactions=").append(nbTransactions);
        sb.append('}');
        return sb.toString();
    }
}
