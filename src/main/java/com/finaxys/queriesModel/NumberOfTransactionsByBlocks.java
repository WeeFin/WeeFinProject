package com.finaxys.queriesModel;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * This class is used to store the result of the query : Get the number of transactions by block
 */
public class NumberOfTransactionsByBlocks {

    public String block_number;
    public long nbTransactions;

    public NumberOfTransactionsByBlocks() {
    }

    /**
     * @param block_number
     * @param nbTransactions : corresponding number of transactions for the block
     */
    public NumberOfTransactionsByBlocks(String block_number, long nbTransactions) {
        this.block_number = block_number;
        this.nbTransactions = nbTransactions;
    }

    /**
     * @param nbTransactionsByBlocks in JSON format
     * @return an instance of NumberOfTransactionsByBlocks
     */
    public static NumberOfTransactionsByBlocks fromString(String nbTransactionsByBlocks) {
        ObjectMapper mapper = new ObjectMapper();
        NumberOfTransactionsByBlocks t = null;
        try {
            t = mapper.readValue(nbTransactionsByBlocks, NumberOfTransactionsByBlocks.class);
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
