package com.finaxys.model;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.Timestamp;

public class BlocksTransactions {

    private long block_number;
    private String block_hash;
    private String block_parent_hash;
    private String block_nonce;
    private String block_sha3_uncles;
    private String block_logs_bloom;
    private String block_transactions_root;
    private String block_state_root;
    private String block_miner;
    private double block_difficulty;
    private double block_total_difficulty;
    private long block_size;
    private String block_extra_data;
    private long block_gas_limit;
    private long block_gas_used;
    private Timestamp block_timestamp;
    private long block_transaction_count;
    private String tx_hash;
    private long tx_nonce;
    private long tx_block_number;
    private long tx_index;
    private String tx_from;
    private String tx_to;
    private double tx_value;
    private long tx_gas;
    private long tx_gas_price;
    private String tx_input;

    public BlocksTransactions() {

    }

    public BlocksTransactions(long block_number, String block_hash, String block_parent_hash, String block_nonce, String block_sha3_uncles, String block_logs_bloom, String block_transactions_root, String block_state_root, String block_miner, double block_difficulty, double block_total_difficulty, long block_size, String block_extra_data, long block_gas_limit, long block_gas_used, Timestamp block_timestamp, long block_transaction_count, String tx_hash, long tx_nonce, long tx_block_number, long tx_index, String tx_from, String tx_to, double tx_value, long tx_gas, long tx_gas_price, String tx_input) {
        this.block_number = block_number;
        this.block_hash = block_hash;
        this.block_parent_hash = block_parent_hash;
        this.block_nonce = block_nonce;
        this.block_sha3_uncles = block_sha3_uncles;
        this.block_logs_bloom = block_logs_bloom;
        this.block_transactions_root = block_transactions_root;
        this.block_state_root = block_state_root;
        this.block_miner = block_miner;
        this.block_difficulty = block_difficulty;
        this.block_total_difficulty = block_total_difficulty;
        this.block_size = block_size;
        this.block_extra_data = block_extra_data;
        this.block_gas_limit = block_gas_limit;
        this.block_gas_used = block_gas_used;
        this.block_timestamp = block_timestamp;
        this.block_transaction_count = block_transaction_count;
        this.tx_hash = tx_hash;
        this.tx_nonce = tx_nonce;
        this.tx_block_number = tx_block_number;
        this.tx_index = tx_index;
        this.tx_from = tx_from;
        this.tx_to = tx_to;
        this.tx_value = tx_value;
        this.tx_gas = tx_gas;
        this.tx_gas_price = tx_gas_price;
        this.tx_input = tx_input;
    }

    public long getBlock_number() {
        return block_number;
    }

    public void setBlock_number(long block_number) {
        this.block_number = block_number;
    }

    public String getBlock_hash() {
        return block_hash;
    }

    public void setBlock_hash(String block_hash) {
        this.block_hash = block_hash;
    }

    public String getBlock_parent_hash() {
        return block_parent_hash;
    }

    public void setBlock_parent_hash(String block_parent_hash) {
        this.block_parent_hash = block_parent_hash;
    }

    public String getBlock_nonce() {
        return block_nonce;
    }

    public void setBlock_nonce(String block_nonce) {
        this.block_nonce = block_nonce;
    }

    public String getBlock_sha3_uncles() {
        return block_sha3_uncles;
    }

    public void setBlock_sha3_uncles(String block_sha3_uncles) {
        this.block_sha3_uncles = block_sha3_uncles;
    }

    public String getBlock_logs_bloom() {
        return block_logs_bloom;
    }

    public void setBlock_logs_bloom(String block_logs_bloom) {
        this.block_logs_bloom = block_logs_bloom;
    }

    public String getBlock_transactions_root() {
        return block_transactions_root;
    }

    public void setBlock_transactions_root(String block_transactions_root) {
        this.block_transactions_root = block_transactions_root;
    }

    public String getBlock_state_root() {
        return block_state_root;
    }

    public void setBlock_state_root(String block_state_root) {
        this.block_state_root = block_state_root;
    }

    public String getBlock_miner() {
        return block_miner;
    }

    public void setBlock_miner(String block_miner) {
        this.block_miner = block_miner;
    }

    public double getBlock_difficulty() {
        return block_difficulty;
    }

    public void setBlock_difficulty(double block_difficulty) {
        this.block_difficulty = block_difficulty;
    }

    public double getBlock_total_difficulty() {
        return block_total_difficulty;
    }

    public void setBlock_total_difficulty(double block_total_difficulty) {
        this.block_total_difficulty = block_total_difficulty;
    }

    public long getBlock_size() {
        return block_size;
    }

    public void setBlock_size(long block_size) {
        this.block_size = block_size;
    }

    public String getBlock_extra_data() {
        return block_extra_data;
    }

    public void setBlock_extra_data(String block_extra_data) {
        this.block_extra_data = block_extra_data;
    }

    public long getBlock_gas_limit() {
        return block_gas_limit;
    }

    public void setBlock_gas_limit(long block_gas_limit) {
        this.block_gas_limit = block_gas_limit;
    }

    public long getBlock_gas_used() {
        return block_gas_used;
    }

    public void setBlock_gas_used(long block_gas_used) {
        this.block_gas_used = block_gas_used;
    }

    public Timestamp getBlock_timestamp() {
        return block_timestamp;
    }

    public void setBlock_timestamp(Timestamp block_timestamp) {
        this.block_timestamp = block_timestamp;
    }

    public long getBlock_transaction_count() {
        return block_transaction_count;
    }

    public void setBlock_transaction_count(long block_transaction_count) {
        this.block_transaction_count = block_transaction_count;
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

    /**
     * @param blocksTransactions in JSON format
     * @return an instance of a BlocksTransactions
     */
    public static BlocksTransactions fromString(String blocksTransactions) {
        ObjectMapper mapper = new ObjectMapper();
        BlocksTransactions bt = null;
        try {
            bt = mapper.readValue(blocksTransactions, BlocksTransactions.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bt;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BlocksTransactions{");
        sb.append("block_number=").append(block_number);
        sb.append(", block_hash='").append(block_hash).append('\'');
        sb.append(", block_parent_hash='").append(block_parent_hash).append('\'');
        sb.append(", block_nonce='").append(block_nonce).append('\'');
        sb.append(", block_sha3_uncles='").append(block_sha3_uncles).append('\'');
        sb.append(", block_logs_bloom='").append(block_logs_bloom).append('\'');
        sb.append(", block_transactions_root='").append(block_transactions_root).append('\'');
        sb.append(", block_state_root='").append(block_state_root).append('\'');
        sb.append(", block_miner='").append(block_miner).append('\'');
        sb.append(", block_difficulty=").append(block_difficulty);
        sb.append(", block_total_difficulty=").append(block_total_difficulty);
        sb.append(", block_size=").append(block_size);
        sb.append(", block_extra_data='").append(block_extra_data).append('\'');
        sb.append(", block_gas_limit=").append(block_gas_limit);
        sb.append(", block_gas_used=").append(block_gas_used);
        sb.append(", block_timestamp=").append(block_timestamp);
        sb.append(", block_transaction_count=").append(block_transaction_count);
        sb.append(", tx_hash='").append(tx_hash).append('\'');
        sb.append(", tx_nonce=").append(tx_nonce);
        sb.append(", tx_block_number=").append(tx_block_number);
        sb.append(", tx_index=").append(tx_index);
        sb.append(", tx_from='").append(tx_from).append('\'');
        sb.append(", tx_to='").append(tx_to).append('\'');
        sb.append(", tx_value=").append(tx_value);
        sb.append(", tx_gas=").append(tx_gas);
        sb.append(", tx_gas_price=").append(tx_gas_price);
        sb.append(", tx_input='").append(tx_input).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
