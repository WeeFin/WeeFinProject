%jdbc(hive)

SELECT from_unixtime(cast(blocks.block_timestamp as bigint)),count(transactions.tx_hash)
FROM blocks
JOIN transactions
ON transactions.tx_block_number=blocks.block_number
GROUP BY blocks.block_timestamp
LIMIT 10;

SELECT avg(transactions.tx_value) as AverageTransactionValue ,hour(from_unixtime(cast(blocks.block_timestamp as bigint))) as hour
FROM transactions
JOIN blocks
ON transactions.tx_block_number=blocks.block_number
GROUP BY hour(from_unixtime(cast(blocks.block_timestamp as bigint)));

SELECT hour(from_unixtime(cast(blocks.block_timestamp as bigint))),sum(block_transaction_count)
FROM blocks
WHERE  hour(from_unixtime(cast(blocks.block_timestamp as bigint))) is not null
GROUP BY hour(from_unixtime(cast(blocks.block_timestamp as bigint)));
