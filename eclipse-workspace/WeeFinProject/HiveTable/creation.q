CREATE EXTERNAL TABLE IF NOT EXISTS blocks (
    block_number BIGINT,
    block_hash STRING,
    block_parent_hash STRING,
    block_nonce STRING,
    block_sha3_uncles STRING,
    block_logs_bloom STRING,
    block_transactions_root STRING,
    block_state_root STRING,
    block_miner STRING,
    block_difficulty DECIMAL(38,0),
    block_total_difficulty DECIMAL(38,0),
    block_size BIGINT,
    block_extra_data STRING,
    block_gas_limit BIGINT,
    block_gas_used BIGINT,
    block_timestamp BIGINT,
    block_transaction_count BIGINT
)
PARTITIONED BY (start_block BIGINT, end_block BIGINT)
ROW FORMAT SERDE 'org.apache.hadoop.hive.serde2.lazy.LazySimpleSerDe'
WITH SERDEPROPERTIES (
    'serialization.format' = ',',
    'field.delim' = ',',
    'escape.delim' = '\\'
)
STORED AS TEXTFILE
LOCATION '/user/maria_dev/output/blocks'
TBLPROPERTIES (
  'skip.header.line.count' = '1'
);

MSCK REPAIR TABLE blocks;


CREATE EXTERNAL TABLE IF NOT EXISTS transactions (
    tx_hash STRING, 
    tx_nonce BIGINT, 
    tx_block_hash STRING,
    tx_block_number BIGINT, 
    tx_index BIGINT, 
    tx_from STRING, 
    tx_to STRING, 
    tx_value DECIMAL(38,0), 
    tx_gas BIGINT, 
    tx_gas_price BIGINT, 
    tx_input STRING  
)
PARTITIONED BY (start_block BIGINT, end_block BIGINT)
ROW FORMAT SERDE 'org.apache.hadoop.hive.serde2.lazy.LazySimpleSerDe'
WITH SERDEPROPERTIES (
    'serialization.format' = ',',
    'field.delim' = ',',
    'escape.delim' = '\\'
)
STORED AS TEXTFILE
LOCATION '/user/maria_dev/output/transactions'
TBLPROPERTIES (
  'skip.header.line.count' = '1'
);

MSCK REPAIR TABLE transactions;


CREATE EXTERNAL TABLE IF NOT EXISTS erc20_transfers (
    erc20_token STRING, 
    erc20_from STRING, 
    erc20_to STRING, 
    erc20_value DECIMAL(38,0), 
    erc20_tx_hash STRING, 
    erc20_log_index BIGINT, 
    erc20_block_number BIGINT  
)
PARTITIONED BY (start_block BIGINT, end_block BIGINT)
ROW FORMAT SERDE 'org.apache.hadoop.hive.serde2.lazy.LazySimpleSerDe'
WITH SERDEPROPERTIES (
    'serialization.format' = ',',
    'field.delim' = ',',
    'escape.delim' = '\\'
)
STORED AS TEXTFILE
LOCATION '/user/maria_dev/output/erc20_transfers'
TBLPROPERTIES (
  'skip.header.line.count' = '1'
);

MSCK REPAIR TABLE erc20_transfers;