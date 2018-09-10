package FlinkProcessingTest;

import com.finaxys.model.Blocks;
import com.finaxys.schema.BlocksSchema;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer011;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer011;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.TableEnvironment;
import org.apache.flink.table.api.java.StreamTableEnvironment;
import org.apache.flink.types.Row;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Properties;

public class FlinkDummyProducerConsumer {


    public static StreamExecutionEnvironment env;
    public static StreamTableEnvironment tableEnv;


    @BeforeAll
    public static void setUp() {
        env = StreamExecutionEnvironment.getExecutionEnvironment();
        tableEnv = TableEnvironment.getTableEnvironment(env);
    }

    /**
     * @return the datastream of blocks from a Kafka Topic
     */
    DataStream<Blocks> testKafkaConsumer() {

        Properties props;

        props = new Properties();
        props.setProperty("bootstrap.servers", "localhost:9092"); // Broker default host:port
        props.setProperty("group.id", "flink-getDataStreamFromKafka"); // Consumer group ID

        FlinkKafkaConsumer011<Blocks> flinkBlocksTransactionsConsumer = new FlinkKafkaConsumer011<>("TestTopicBlocks", new BlocksSchema(), props);
        flinkBlocksTransactionsConsumer.setStartFromEarliest();

        DataStream<Blocks> blocksTransactions = env.addSource(flinkBlocksTransactionsConsumer);

        return blocksTransactions;
    }

    /**
     * Basic SQL query
     *
     * @return a table containing the sql result
     */
    @Test
    Table testSQLQuery() {

        DataStream<Blocks> blocksTransactions = testKafkaConsumer();

        tableEnv.registerDataStream("blocksTable", blocksTransactions);

        Table sqlResult
                = tableEnv.sqlQuery(
                "SELECT block_number, " +
                        "block_hash, " +
                        "block_parent_hash, " +
                        "block_nonce, block_sha3_uncles, " +
                        "block_logs_bloom, " +
                        "block_transactions_root, " +
                        "block_state_root, " +
                        "block_miner, " +
                        "block_difficulty, " +
                        "block_total_difficulty, " +
                        "block_size, " +
                        "block_gas_limit, " +
                        "block_gas_used, " +
                        "block_extra_data, " +
                        "block_timestamp, " +
                        "block_transaction_count " +
                        "FROM blocksTable");

        return sqlResult;
    }

    /**
     * Kafka Producer, convert Table to Datastream in order to produce the result on a Kafka Topic
     */
    @Test
    void testKafkaProducer() {

        Table sqlResult = testSQLQuery();

        DataStream<Blocks> resultStream = tableEnv
                .toRetractStream(sqlResult, Row.class)
                .map(t -> {
                    Row r = t.f1;
                    long block_number = Long.valueOf(r.getField(0).toString());
                    String block_hash = r.getField(1).toString();
                    String block_parent_hash = r.getField(2).toString();
                    String block_nonce = r.getField(3).toString();
                    String block_sha3_uncles = r.getField(4).toString();
                    String block_logs_bloom = r.getField(5).toString();
                    String block_transactions_root = r.getField(6).toString();
                    String block_state_root = r.getField(7).toString();
                    String block_miner = r.getField(8).toString();
                    double block_difficulty = Double.valueOf(r.getField(9).toString());
                    double block_total_difficulty = Double.valueOf(r.getField(10).toString());
                    long block_size = Long.valueOf(r.getField(11).toString());
                    String block_extra_data = r.getField(12).toString();
                    long block_gas_limit = Long.valueOf(r.getField(13).toString());
                    long block_gas_used = Long.valueOf(r.getField(14).toString());
                    long block_timestamp = Long.valueOf(r.getField(15).toString());
                    long block_transaction_count = Long.valueOf(r.getField(16).toString());
                    return new Blocks(block_number,
                            block_hash,
                            block_parent_hash,
                            block_nonce,
                            block_sha3_uncles,
                            block_logs_bloom,
                            block_transactions_root,
                            block_state_root,
                            block_miner,
                            block_difficulty,
                            block_total_difficulty,
                            block_size,
                            block_extra_data,
                            block_gas_limit,
                            block_gas_used,
                            block_timestamp,
                            block_transaction_count);
                })
                .returns(Blocks.class);

        resultStream.print();

        resultStream.addSink(new FlinkKafkaProducer011<>("localhost:9092", "TargetTestTopic", new BlocksSchema()));
    }

    /**
     * Execute the Job
     *
     * @throws Exception
     */
    @AfterAll
    public static void init() throws Exception {
        env.execute();
    }

}
