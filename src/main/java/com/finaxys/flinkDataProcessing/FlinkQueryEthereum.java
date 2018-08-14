package com.finaxys.flinkDataProcessing;

import com.finaxys.model.Blocks;
import com.finaxys.schema.BlocksSchema;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer011;
import org.apache.flink.table.api.TableEnvironment;
import org.apache.flink.table.api.java.StreamTableEnvironment;

import java.util.Properties;

public class FlinkQueryEthereum {

    public static void main(String[] args) throws Exception {

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        StreamTableEnvironment tableEnv = TableEnvironment.getTableEnvironment(env);
        // configure Kafka consumer
        Properties props = new Properties();
        props.setProperty("bootstrap.servers", "localhost:9092"); // Broker default host:port
        props.setProperty("group.id", "flink-consumer"); // Consumer group ID

        FlinkKafkaConsumer011<Blocks> abc = new FlinkKafkaConsumer011<>(args[0], new BlocksSchema(), props);
        abc.setStartFromEarliest();
        DataStream<Blocks> blocks = env.addSource(abc);

        blocks.print();
        //env.fromElements(1, 2, 3, 4).print();

        env.execute();

    }

}
