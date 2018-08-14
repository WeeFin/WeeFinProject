package com.finaxys.kafka;

import java.util.List;
import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import com.finaxys.configuration.LoadClasses;
import com.finaxys.model.Transactions;

public class KafkaProducerTransactions {

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
		props.put("value.serializer", "com.finaxys.serialization.TransactionsSerializer");

		LoadClasses loadCSVFiles = new LoadClasses("/home/finaxys/transactions.csv");

		List<Transactions> transactions = loadCSVFiles.getListOfTransactionsFromCSV();

		try (Producer<String, Transactions> producer = new KafkaProducer<>(props)) {

			producer.send(new ProducerRecord<String, Transactions>(topicName, transactions.get(0)));
			System.out.println("Transaction " + transactions.get(0).getTx_block_hash() + " sent !");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
