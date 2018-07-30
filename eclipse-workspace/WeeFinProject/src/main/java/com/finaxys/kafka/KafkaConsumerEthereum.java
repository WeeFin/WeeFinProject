package com.finaxys.kafka;

import java.util.Collections;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.ProducerConfig;

import com.finaxys.model.Blocks;

public class KafkaConsumerEthereum {

	public static void main(String[] args) {

		String topicName = args[0].toString();
		Properties props = new Properties();

	    props.put("bootstrap.servers", "localhost:9092");
		props.put("group.id", "test");
		props.put("enable.auto.commit", "true");
		props.put("auto.commit.interval.ms", "1000");
		props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		props.put("value.deserializer", "com.finaxys.kafka.BlocksDeserializer");

		try (KafkaConsumer<String, Blocks> consumer = new KafkaConsumer<>(props)) {
			consumer.subscribe(Collections.singletonList(topicName));
			while (true) {
				System.out.println("Ici");
				ConsumerRecords<String, Blocks> messages = consumer.poll(100);
				System.out.println("Là");
				for (ConsumerRecord<String, Blocks> message : messages) {
					System.out.println("Message received " + message.value().getBlock_number());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
