package model;

public class Blocks {

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
	private long block_timestamp;
	private long block_transaction_count;
	
	

	public Blocks(long block_number, String block_hash, String block_parent_hash, String block_nonce,
			String block_sha3_uncles, String block_logs_bloom, String block_transactions_root, String block_state_root,
			String block_miner, double block_difficulty, double block_total_difficulty, long block_size,
			String block_extra_data, long block_gas_limit, long block_gas_used, long block_timestamp,
			long block_transaction_count) {
		super();
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

	public long getBlock_timestamp() {
		return block_timestamp;
	}

	public void setBlock_timestamp(long block_timestamp) {
		this.block_timestamp = block_timestamp;
	}

	public long getBlock_transaction_count() {
		return block_transaction_count;
	}

	public void setBlock_transaction_count(long block_transaction_count) {
		this.block_transaction_count = block_transaction_count;
	}

}
