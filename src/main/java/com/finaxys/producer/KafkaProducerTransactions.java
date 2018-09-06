package com.finaxys.producer;

import com.finaxys.loader.LoadModel;
import com.finaxys.model.Transactions;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;

/**
 * This class is used to send Transactions informations on a Kafka Topic given in argument
 */
public class KafkaProducerTransactions {

    public static void main(String[] args) throws Exception {

        if (args.length == 0) {
            System.out.println("Name of the topic missing");
            return;
        }

        String topicName = args[0];

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


        Files.walk(Paths.get(args[1]))
                .filter(Files::isRegularFile)
                .forEach(x -> {
                    LoadModel loadCSVFiles = new LoadModel(x.toString());
                    List<Transactions> transactions = loadCSVFiles.getListOfTransactionsFromCSV();
                    try (Producer<String, Transactions> producer = new KafkaProducer<>(props)) {
                        for (Transactions t : transactions) {
                            producer.send(new ProducerRecord<>(topicName, t));
                            System.out.println("Transaction " + t.getTx_block_hash() + " sent !");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

}
