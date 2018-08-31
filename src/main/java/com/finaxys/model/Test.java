package com.finaxys.model;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Test {

    String block_hash;
    long nbTransactions;

    public Test(String block_hash, long nbTransactions) {
        this.block_hash = block_hash;
        this.nbTransactions = nbTransactions;
    }

    public static Test fromString(String test) {
        ObjectMapper mapper = new ObjectMapper();
        Test t = null;
        try {
            t = mapper.readValue(test, Test.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Test{");
        sb.append("block_hash='").append(block_hash).append('\'');
        sb.append(", nbTransactions=").append(nbTransactions);
        sb.append('}');
        return sb.toString();
    }
}
