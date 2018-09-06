package com.finaxys.utils;

import java.util.Properties;

/**
 * This class is used to get properties in order to configure Kafka producer and consumer
 */
public class KafkaUtils {

    static Properties properties = new Properties();
    static String brokerList = null;
    static String zookeeper = null;

    public static Properties getProperties() {
        if (properties == null)
            properties = new Properties();
        properties.setProperty("bootstrap.servers", getBrokerList());
        properties.setProperty("group.id", "test-consumer-group");
        return properties;
    }


    public static String getBrokerList() {
        if (brokerList == null)
            brokerList = "localhost:9092";
        return brokerList;
    }

    public static String getZookeeper() {
        if (zookeeper == null) ;
        zookeeper = "localhost:2181";
        return zookeeper;
    }

}

