package FlinkProcessingTest;


import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.Types;
import org.apache.flink.table.api.java.BatchTableEnvironment;
import org.apache.flink.table.sources.CsvTableSource;
import org.apache.flink.types.Row;

public class FlinkDummyConsumerBatch {

    public static void main(String[] args) throws Exception {

        final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        final BatchTableEnvironment tableEnv = BatchTableEnvironment.getTableEnvironment(env);

        CsvTableSource csvTableSource = CsvTableSource.builder()
                .path("/home/finaxys/ethereum-etl/output/test/")
                .ignoreFirstLine()
                .fieldDelimiter(",")
                .field("block_number", Types.LONG())
                .field("block_hash", Types.STRING())
                .field("block_parent_hash", Types.STRING())
                .field("block_nonce", Types.STRING())
                .field("block_sha3_uncles", Types.STRING())
                .field("block_logs_bloom", Types.STRING())
                .field("block_transactions_root", Types.STRING())
                .field("block_state_root", Types.STRING())
                .field("block_miner", Types.STRING())
                .field("block_difficulty", Types.DOUBLE())
                .field("block_total_difficulty", Types.DOUBLE())
                .field("block_size", Types.LONG())
                .field("block_extra_data", Types.STRING())
                .field("block_gas_limit", Types.LONG())
                .field("block_gas_used", Types.LONG())
                .field("block_timestamp", Types.LONG())
                .field("block_transaction_count", Types.LONG())
                .field("tx_hash", Types.STRING())
                .field("tx_nonce", Types.LONG())
                .field("tx_block_number", Types.LONG())
                .field("tx_index", Types.LONG())
                .field("tx_from", Types.STRING())
                .field("tx_to", Types.STRING())
                .field("tx_value", Types.DOUBLE())
                .field("tx_gas", Types.LONG())
                .field("tx_gas_price", Types.LONG())
                .field("tx_input", Types.STRING())
                .build();

        tableEnv.registerTableSource("blocksTransactionsTable", csvTableSource);

        Table sqlResult = tableEnv.sqlQuery(
                "SELECT CAST(CAST(cast(block_timestamp as bigint) as decimal(30,0))/1000 AS timestamp) FROM blocksTransactionsTable");

        DataSet<Row> data = tableEnv.toDataSet(sqlResult, Row.class);

        data.print();

        // env.execute();

    }
}
