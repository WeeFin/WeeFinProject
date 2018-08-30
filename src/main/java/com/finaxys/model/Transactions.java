package com.finaxys.model;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Transactions {

    private String tx_hash;
    private long tx_nonce;
    private String tx_block_hash;
    private long tx_block_number;
    private long tx_index;
    private String tx_from;
    private String tx_to;
    private double tx_value;
    private long tx_gas;
    private long tx_gas_price;
    private String tx_input;

    public Transactions(String tx_hash, long tx_nonce, String tx_block_hash, long tx_block_number, long tx_index,
                        String tx_from, String tx_to, double tx_value, long tx_gas, long tx_gas_price, String tx_input) {
        super();
        this.tx_hash = tx_hash;
        this.tx_nonce = tx_nonce;
        this.tx_block_hash = tx_block_hash;
        this.tx_block_number = tx_block_number;
        this.tx_index = tx_index;
        this.tx_from = tx_from;
        this.tx_to = tx_to;
        this.tx_value = tx_value;
        this.tx_gas = tx_gas;
        this.tx_gas_price = tx_gas_price;
        this.tx_input = tx_input;
    }

    public Transactions() {

    }

    public String getTx_hash() {
        return tx_hash;
    }

    public void setTx_hash(String tx_hash) {
        this.tx_hash = tx_hash;
    }

    public long getTx_nonce() {
        return tx_nonce;
    }

    public void setTx_nonce(long tx_nonce) {
        this.tx_nonce = tx_nonce;
    }

    public String getTx_block_hash() {
        return tx_block_hash;
    }

    public void setTx_block_hash(String tx_block_hash) {
        this.tx_block_hash = tx_block_hash;
    }

    public long getTx_block_number() {
        return tx_block_number;
    }

    public void setTx_block_number(long tx_block_number) {
        this.tx_block_number = tx_block_number;
    }

    public long getTx_index() {
        return tx_index;
    }

    public void setTx_index(long tx_index) {
        this.tx_index = tx_index;
    }

    public String getTx_from() {
        return tx_from;
    }

    public void setTx_from(String tx_from) {
        this.tx_from = tx_from;
    }

    public String getTx_to() {
        return tx_to;
    }

    public void setTx_to(String tx_to) {
        this.tx_to = tx_to;
    }

    public double getTx_value() {
        return tx_value;
    }

    public void setTx_value(double tx_value) {
        this.tx_value = tx_value;
    }

    public long getTx_gas() {
        return tx_gas;
    }

    public void setTx_gas(long tx_gas) {
        this.tx_gas = tx_gas;
    }

    public long getTx_gas_price() {
        return tx_gas_price;
    }

    public void setTx_gas_price(long tx_gas_price) {
        this.tx_gas_price = tx_gas_price;
    }

    public String getTx_input() {
        return tx_input;
    }

    public void setTx_input(String tx_input) {
        this.tx_input = tx_input;
    }

    public static Transactions fromString(String transactions) {
        ObjectMapper mapper = new ObjectMapper();
        Transactions t = null;
        try {
            t = mapper.readValue(transactions, Transactions.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }

}
