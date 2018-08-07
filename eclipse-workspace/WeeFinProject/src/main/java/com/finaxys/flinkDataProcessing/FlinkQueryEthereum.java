package com.finaxys.flinkDataProcessing;

import java.util.Properties;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer011;

public class FlinkQueryEthereum {

	public static void main(String[] args) throws Exception {

		final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

		// configure Kafka consumer
		Properties props = new Properties();
		props.setProperty("bootstrap.servers", "localhost:9092"); // Broker default host:port
		props.setProperty("group.id", "test"); // Consumer group ID

		DataStream<String> stream = env
				.addSource(new FlinkKafkaConsumer011<String>(args[0], new SimpleStringSchema(), props));

		stream.map(new MapFunction<String, String>() {
			private static final long serialVersionUID = -6867736771747690202L;

			@Override
			public String map(String value) throws Exception {
				return "Stream Value: " + value;
			}
		}).print();

		env.execute();

	}

}
