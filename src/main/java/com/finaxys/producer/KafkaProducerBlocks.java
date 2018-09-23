package com.finaxys.producer;

import com.finaxys.loader.LoadModel;
import com.finaxys.model.Blocks;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;

/**
 * This class is used to send Blocks informations on a Kafka Topic given in argument
 */
public class KafkaProducerBlocks {

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
        props.put("value.serializer", "com.finaxys.serialization.BlocksSerializer");

        Files.walk(Paths.get(args[1]))
                .filter(Files::isRegularFile)
                .forEach(x -> {
                    LoadModel loadCSVFiles = new LoadModel(x.toString());
                    List<Blocks> blocks = loadCSVFiles.getListOfBlocksFromCSV();
                    try (Producer<String, Blocks> producer = new KafkaProducer<>(props)) {
                        blocks.forEach((Blocks b) -> {
                            producer.send(new ProducerRecord<>(topicName, b));
                            System.out.println("Blocks " + b.getBlock_hash() + " sent !");
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }
}
