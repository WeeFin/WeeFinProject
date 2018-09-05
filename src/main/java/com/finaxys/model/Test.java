package com.finaxys.model;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Test {

    public String block_number;
    public long nbTransactions;

    public Test(){ }

    public Test(String block_number, long nbTransactions) {
        this.block_number = block_number;
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
        sb.append("block_number='").append(block_number).append('\'');
        sb.append(", nbTransactions=").append(nbTransactions);
        sb.append('}');
        return sb.toString();
    }
}
