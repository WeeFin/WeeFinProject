package com.finaxys.kafka;

import java.util.List;
import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import com.finaxys.Configuration.LoadClasses;
import com.finaxys.model.Erc20_Transfers;

public class KafkaProducerErc20_Transfers {
	
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
		props.put("value.serializer", "com.finaxys.serialization.Erc20_TransfersSerializer");

		LoadClasses loadCSVFiles = new LoadClasses("/home/finaxys/erc20_transfers.csv");

		List<Erc20_Transfers> erc20_transfers = loadCSVFiles.getListOfErc20_TransfersFromCSV();

		try (Producer<String, Erc20_Transfers> producer = new KafkaProducer<>(props)) {

			producer.send(new ProducerRecord<String, Erc20_Transfers>(topicName, erc20_transfers.get(0)));
			System.out.println("Erc20_Transfers " + erc20_transfers.get(0).getErc20_tx_hash() + " sent !");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}


}
