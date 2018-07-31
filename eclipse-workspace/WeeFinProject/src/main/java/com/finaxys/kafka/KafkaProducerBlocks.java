package com.finaxys.kafka;

import java.util.List;
import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import com.finaxys.Configuration.LoadClasses;
import com.finaxys.model.Blocks;

public class KafkaProducerBlocks {

	public static void main(String[] args) throws Exception {

		if (args.length == 0) {
			System.out.println("Name of the topic missing");
			return;
		}

		String topicName = args[0].toString();

		// create instance for properties to access producer configs
		Properties props = new Properties();
		props.put("bootstrap.servers", "localhost:9092");
		props.put("acks", "all");
		props.put("retries", 0);
		props.put("batch.size", 16384);
		props.put("linger.ms", 1);
		props.put("buffer.memory", 33554432);
		props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		props.put("value.serializer", "com.finaxys.serialization.BlocksSerializer");

		LoadClasses loadCSVFiles = new LoadClasses("/home/finaxys/blocks.csv");

		List<Blocks> blocks = loadCSVFiles.getListOfBlocksFromCSV();

		try (Producer<String, Blocks> producer = new KafkaProducer<>(props)) {

			producer.send(new ProducerRecord<String, Blocks>(topicName, blocks.get(0)));
			System.out.println("Blocks " + blocks.get(0).getBlock_hash() + " sent !");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
