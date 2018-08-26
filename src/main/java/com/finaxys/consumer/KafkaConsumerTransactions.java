package com.finaxys.consumer;

import java.util.Collections;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import com.finaxys.model.Transactions;

public class KafkaConsumerTransactions {

	public static void main(String[] args) {

		if (args.length == 0) {
			System.out.println("Name of the topic missing");
			return;
		}

		String topicName = args[0].toString();
		Properties props = new Properties();

		props.put("bootstrap.servers", "localhost:9092");
		props.put("group.id", "test");
		props.put("enable.auto.commit", "true");
		props.put("auto.commit.interval.ms", "1000");
		props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		props.put("value.deserializer", "com.finaxys.deserialization.TransactionsDeserializer");

		try (KafkaConsumer<String, Transactions> consumer = new KafkaConsumer<>(props)) {
			consumer.subscribe(Collections.singletonList(topicName));
			while (true) {
				ConsumerRecords<String, Transactions> messages = consumer.poll(100);
				for (ConsumerRecord<String, Transactions> message : messages) {
					System.out.println("Message received " + message.value().getTx_block_hash());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
