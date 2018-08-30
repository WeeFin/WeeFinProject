#!/usr/bin/env bash

blockDirectory=/home/finaxys/ethereum-etl/output/blocks
transactionsDirectory=/home/finaxys/ethereum-etl/output/transactions
erc20TransfersDirectory=/home/finaxys/ethereum-etl/output/erc20_transfers
joinedTablesDirectory=/home/finaxys/ethereum-etl/output/joinedTables/
filePrefix="blocksTransactions"


paste <(find $blockDirectory -name "*.csv" | sort ) <(find $transactionsDirectory -name "*.csv" | sort ) <(ls $blockDirectory/*/* | grep csv | cut -c 7-)|
while read blocksFile transactionsFile fileSuffix;
    do csvjoin -c 2,3 $blocksFile $transactionsFile > $joinedTablesDirectory$filePrefix$fileSuffix
done;
