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


    public DataStream<BlocksTransactions> getDataStreamFromKafka(String topicName, StreamExecutionEnvironment env) {
        FlinkKafkaConsumer011<BlocksTransactions> flinkBlocksTransactionsConsumer = new FlinkKafkaConsumer011<>(topicName, new BlocksTransactionsSchema(), KafkaUtils.getProperties());
        flinkBlocksTransactionsConsumer.setStartFromEarliest();

        return env.addSource(flinkBlocksTransactionsConsumer);
    }

    public abstract T getDataStreamFromTable(StreamTableEnvironment tableEnv, Table sqlResult);

    public abstract Table getNumberOfTransactionsByBlock(StreamTableEnvironment tableEnv);

    public abstract void sendDataStreamToElasticSearch(T resultStream);
}