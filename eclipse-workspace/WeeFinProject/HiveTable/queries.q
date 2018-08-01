%jdbc(hive)

SELECT from_unixtime(cast(blocks.block_timestamp as bigint)),count(transactions.tx_hash)
FROM blocks
JOIN transactions
ON transactions.tx_block_number=blocks.block_number
GROUP BY blocks.block_timestamp
LIMIT 10;
