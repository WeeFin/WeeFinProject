package com.finaxys.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;

public class Test {

    String block_number;
    long nbTransactions;

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
        JSONObject jsonObj = new JSONObject();
        jsonObj.append("block_number", block_number);
        jsonObj.append("nbTransactions", nbTransactions);
        return jsonObj.toString();
    }
}
