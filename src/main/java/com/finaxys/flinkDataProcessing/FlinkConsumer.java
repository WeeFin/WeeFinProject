package com.finaxys.flinkDataProcessing;

import com.finaxys.model.Blocks;
import com.finaxys.model.Transactions;
import com.finaxys.schema.BlocksSchema;
import com.finaxys.schema.TransactionsSchema;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer011;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer011;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.TableEnvironment;
import org.apache.flink.table.api.java.StreamTableEnvironment;
import org.apache.flink.types.Row;

import java.util.Properties;

public class FlinkConsumer {

    public static void main(String[] args) throws Exception {

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        StreamTableEnvironment tableEnv = TableEnvironment.getTableEnvironment(env);
        // configure Kafka consumer
        Properties props = new Properties();
        props.setProperty("bootstrap.servers", "localhost:9092"); // Broker default host:port
        props.setProperty("group.id", "flink-consumer"); // Consumer group ID

        FlinkKafkaConsumer011<Blocks> flinkBlocksConsumer = new FlinkKafkaConsumer011<>(args[0], new BlocksSchema(), props);
        flinkBlocksConsumer.setStartFromEarliest();

       /* FlinkKafkaConsumer011<Transactions> flinkTransactionsConsumer = new FlinkKafkaConsumer011<>(args[1], new TransactionsSchema(), props);
        flinkTransactionsConsumer.setStartFromEarliest();*/


        DataStream<Blocks> blocks = env.addSource(flinkBlocksConsumer);

//        DataStream<Transactions> transactions = env.addSource(flinkTransactionsConsumer);

        tableEnv.registerDataStream("blocksTable", blocks);

//        tableEnv.registerDataStream("transactionsTable", transactions);

        Table sqlResult = tableEnv.sqlQuery("SELECT block_number," +
                "block_hash," +
                "block_parent_hash," +
                "block_nonce," +
                "block_sha3_uncles," +
                "block_logs_bloom," +
                "block_transactions_root," +
                "block_state_root," +
                "block_miner," +
                "block_difficulty," +
                "block_total_difficulty," +
                "block_size," +
                "block_extra_data," +
                "block_gas_limit," +
                "block_gas_used," +
                "block_timestamp," +
                "block_transaction_count FROM blocksTable");
        /*Table sqlResult = tableEnv.sqlQuery(
                "SELECT tx_gas" +
                "FROM blocksTable " +
                "JOIN transactionsTable " +
                "ON blocksTable.block_hash=transactionsTable.tx_hash"
        );*/
       /* DataStream<String> resultStream = tableEnv
                .toRetractStream(sqlResult, Row.class)
                .map(t -> t.f1.toString()).returns(String.class);*/
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
                    long block_gas_limit = Long.valueOf(r.getField(13 ).toString());
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
                .returns(Blocks.class)
                ;

        //DataStream<Tuple2<Boolean,Row>> resultStream = tableEnv.toRetractStream(sqlResult, Row.class);
        resultStream.print();

        //resultStream.addSink(new FlinkKafkaProducer011<>("localhost:9092", "TargetTopic", new SimpleStringSchema()));
        resultStream.addSink(new FlinkKafkaProducer011<>("localhost:9092", "TargetTopic", new BlocksSchema()));

        //blocks.print();
        //env.fromElements(1, 2, 3, 4).print();

        env.execute();

    }

}
