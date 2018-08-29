package com.finaxys.dataProcessing;

import com.finaxys.model.Blocks;
import com.finaxys.schema.BlocksSchema;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer011;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer011;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.TableEnvironment;
import org.apache.flink.table.api.java.StreamTableEnvironment;
import org.apache.flink.types.Row;

import java.util.Properties;

public class FlinkConsumerProducer {

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

       /* Table sqlResult = tableEnv.sqlQuery(
                "SELECT tx_gas" +
                "FROM blocksTable " +
                "JOIN transactionsTable " +
                "ON blocksTable.block_hash=transactionsTable.tx_hash"
        );
       DataStream<String> resultStream = tableEnv
                .toRetractStream(sqlResult, Row.class)
                .map(t -> t.f1.toString()).returns(String.class);

        DataStream<Tuple2<Boolean,Row>> resultStream = tableEnv.toRetractStream(sqlResult, Row.class);
        resultStream.print();*/

        //resultStream.addSink(new FlinkKafkaProducer011<>("localhost:9092", "TargetTopic", new SimpleStringSchema()));
        //resultStream.addSink(new FlinkKafkaProducer011<>("localhost:9092", "TargetTopic", new BlocksSchema()));

        //blocks.print();
        DataStream<Tuple2<Integer,String>> test = env.fromElements(
                new Tuple2<>(1,"Bonjour"),
                new Tuple2<>(2,"World"));

        DataStream<Tuple2<Integer,String>> test2 = env.fromElements(
                new Tuple2<>(1,"Français"),
                new Tuple2<>(2,"English"));

        tableEnv.registerDataStream("testTable",test);
        tableEnv.registerDataStream("testTable2",test);

        //Table sqlTestResult = tableEnv.sqlQuery("SELECT


        env.execute();

    }

}
