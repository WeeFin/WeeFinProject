package com.finaxys.dataProcessing;

import com.finaxys.model.BlocksTransactions;
import com.finaxys.schema.BlocksTransactionsSchema;
import com.finaxys.utils.KafkaUtils;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer011;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.TableEnvironment;
import org.apache.flink.table.api.java.StreamTableEnvironment;

public abstract class FlinkAbtractConsumerProducer<T> {

    public StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
    public StreamTableEnvironment tableEnv = TableEnvironment.getTableEnvironment(env);

    /**
     * @param topicName
     * @param env
     * @return the datastream of BlocksTransactions from Kafka Topic
     */
    public DataStream<BlocksTransactions> getDataStreamFromKafka(String topicName, StreamExecutionEnvironment env) {
        FlinkKafkaConsumer011<BlocksTransactions> flinkBlocksTransactionsConsumer = new FlinkKafkaConsumer011<>(topicName, new BlocksTransactionsSchema(), KafkaUtils.getProperties());
        flinkBlocksTransactionsConsumer.setStartFromEarliest();

        return env.addSource(flinkBlocksTransactionsConsumer);
    }

    /**
     * @param tableEnv
     * @param sqlResult
     * @return a datastream from a table
     */
    public abstract DataStream<T> getDataStreamFromTable(StreamTableEnvironment tableEnv, Table sqlResult);

    /**
     * this method will allow you to index your data stored in a DataStream in elasticsearch
     *
     * @param resultStream
     */
    public abstract void sendDataStreamToElasticSearch(DataStream<T> resultStream);
}