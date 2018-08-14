package com.finaxys.kafka;

import java.util.Collections;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import com.finaxys.model.Erc20_Transfers;

public class KafkaConsumerErc20_Transfers {

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
		props.put("value.deserializer", "com.finaxys.deserialization.Erc20_TransfersDeserializer");

		try (KafkaConsumer<String, Erc20_Transfers> consumer = new KafkaConsumer<>(props)) {
			consumer.subscribe(Collections.singletonList(topicName));
			while (true) {
				ConsumerRecords<String, Erc20_Transfers> messages = consumer.poll(100);
				for (ConsumerRecord<String, Erc20_Transfers> message : messages) {
					System.out.println("Message received " + message.value().getErc20_block_number());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
