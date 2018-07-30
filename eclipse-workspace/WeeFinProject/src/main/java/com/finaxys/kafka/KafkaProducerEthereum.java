package com.finaxys.kafka;

import java.util.List;
import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import com.finaxys.Configuration.LoadClasses;
import com.finaxys.model.Blocks;

public class KafkaProducerEthereum {

	public static void main(String[] args) throws Exception {

		if (args.length == 0) {
			System.out.println();
			return;
		}

		String topicName = args[0].toString();

		// create instance for properties to access producer configs
		Properties props = new Properties();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
				"org.apache.kafka.common.serialization.StringSerializer");
		props.put(ProducerConfig.RETRIES_CONFIG, "0");
		props.put(ProducerConfig.ACKS_CONFIG, "all");
		props.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
		props.put(ProducerConfig.LINGER_MS_CONFIG, 1);
		props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
		props.put("value.serializer", "com.finaxys.kafka.BlocksSerializer");

		LoadClasses loadCSVFiles = new LoadClasses("/home/finaxys/blocks.csv");

		List<Blocks> blocks = loadCSVFiles.getListOfBlocksFromCSV();

		try (Producer<String, Blocks> producer = new KafkaProducer<>(props)) {

			producer.send(new ProducerRecord<String, Blocks>(topicName, blocks.get(0)));

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
