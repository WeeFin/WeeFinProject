#!/usr/bin/env bash

for x in $(ls /home/finaxys/ethereum-etl/output/joinedTables/blocksTransactions*.csv);
do sudo -u postgres psql -c "\COPY blockstransactions(block_number,block_hash,block_parent_hash,block_nonce,block_sha3_uncles,block_logs_bloom,block_transactions_root,block_state_root,block_miner,block_difficulty,block_total_difficulty,block_size,block_extra_data,block_gas_limit,block_gas_used,block_timestamp,block_transaction_count,tx_hash,tx_nonce,tx_block_number,tx_index,tx_from,tx_to,tx_value,tx_gas,tx_gas_price,tx_input) FROM '$x' DELIMITER ',' CSV HEADER"; done