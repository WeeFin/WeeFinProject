package com.finaxys.dataProcessing;

import com.finaxys.model.Blocks;
import com.finaxys.model.Test;
import com.finaxys.model.Transactions;
import com.finaxys.schema.BlocksSchema;
import com.finaxys.schema.TestSchema;
import com.finaxys.schema.TransactionsSchema;
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

        FlinkKafkaConsumer011<Transactions> flinkTransactionsConsumer = new FlinkKafkaConsumer011<>(args[1], new TransactionsSchema(), props);
        flinkTransactionsConsumer.setStartFromEarliest();


        DataStream<Blocks> blocks = env.addSource(flinkBlocksConsumer);

        DataStream<Transactions> transactions = env.addSource(flinkTransactionsConsumer);

        tableEnv.registerDataStream("blocksTable", blocks);
        tableEnv.registerDataStream("transactionsTable", transactions);

        Table sqlResult
                = tableEnv.sqlQuery("SELECT block_timestamp,count(tx_hash) " +
                "FROM blocksTable " +
                "JOIN transactionsTable " +
                "ON blocksTable.block_hash=transactionsTable.tx_hash " +
                "GROUP BY blocksTable.block_timestamp");
        System.err.println("After the fall");
        DataStream<Test> resultStream = tableEnv
                .toRetractStream(sqlResult,Row.class)
                .map(t -> {
                    Row r = t.f1;
                    String field2 = r.getField(0).toString();
                    long count = Long.valueOf(r.getField(1).toString());
                    return new Test(field2,count);
                        })
                .returns(Test.class);
        System.err.println("After mapping");

        resultStream.print();

        resultStream.addSink(new FlinkKafkaProducer011<>("localhost:9092", "TargetTopic", new TestSchema()));

        env.execute();

    }

}
