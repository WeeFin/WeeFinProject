package com.finaxys.flinkDataProcessing;

import java.util.Properties;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer011;
import org.apache.flink.table.api.java.StreamTableEnvironment;
import org.apache.flink.table.api.TableEnvironment;

import com.finaxys.schema.BlocksSchema;

public class FlinkQueryEthereum {

	public static void main(String[] args) throws Exception {

		StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
		StreamTableEnvironment tableEnv = TableEnvironment.getTableEnvironment(env);
		// configure Kafka consumer
		Properties props = new Properties();
		props.setProperty("bootstrap.servers", "localhost:9092"); // Broker default host:port
		props.setProperty("group.id", "flink-consumer"); // Consumer group ID

		DataStream<Blocks> stream = env
				.addSource(new FlinkKafkaConsumer011<String>(args[0], new BlocksSchema(), props));

		

		env.execute();

	}

}
