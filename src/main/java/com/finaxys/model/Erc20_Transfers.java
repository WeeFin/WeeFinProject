package com.finaxys.model;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Erc20_Transfers {

    private String erc20_token;
    private String erc20_from;
    private String erc20_to;
    private double erc20_value;
    private String erc20_tx_hash;
    private long erc20_log_index;
    private long erc20_block_number;

    public Erc20_Transfers() {

    }

    public Erc20_Transfers(String erc20_token, String erc20_from, String erc20_to, double erc20_value,
                           String erc20_tx_hash, long erc20_log_index, long erc20_block_number) {
        super();
        this.erc20_token = erc20_token;
        this.erc20_from = erc20_from;
        this.erc20_to = erc20_to;
        this.erc20_value = erc20_value;
        this.erc20_tx_hash = erc20_tx_hash;
        this.erc20_log_index = erc20_log_index;
        this.erc20_block_number = erc20_block_number;
    }

    public String getErc20_token() {
        return erc20_token;
    }

    public void setErc20_token(String erc20_token) {
        this.erc20_token = erc20_token;
    }

    public String getErc20_from() {
        return erc20_from;
    }

    public void setErc20_from(String erc20_from) {
        this.erc20_from = erc20_from;
    }

    public String getErc20_to() {
        return erc20_to;
    }

    public void setErc20_to(String erc20_to) {
        this.erc20_to = erc20_to;
    }

    public double getErc20_value() {
        return erc20_value;
    }

    public void setErc20_value(double erc20_value) {
        this.erc20_value = erc20_value;
    }

    public String getErc20_tx_hash() {
        return erc20_tx_hash;
    }

    public void setErc20_tx_hash(String erc20_tx_hash) {
        this.erc20_tx_hash = erc20_tx_hash;
    }

    public long getErc20_log_index() {
        return erc20_log_index;
    }

    public void setErc20_log_index(long erc20_log_index) {
        this.erc20_log_index = erc20_log_index;
    }

    public long getErc20_block_number() {
        return erc20_block_number;
    }

    public void setErc20_block_number(long erc20_block_number) {
        this.erc20_block_number = erc20_block_number;
    }
	

    public static Erc20_Transfers fromString(String erc20_transfers) {
        ObjectMapper mapper = new ObjectMapper();
        Erc20_Transfers eT = null;
        try {
            eT = mapper.readValue(erc20_transfers, Erc20_Transfers.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return eT;
    }

}
