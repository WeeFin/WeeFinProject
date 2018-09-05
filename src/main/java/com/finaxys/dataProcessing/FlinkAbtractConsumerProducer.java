package com.finaxys.dataProcessing;

import com.finaxys.model.BlocksTransactions;
import com.finaxys.model.Test;
import com.finaxys.schema.BlocksTransactionsSchema;
import com.finaxys.utils.KafkaUtils;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer011;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.TableEnvironment;
import org.apache.flink.table.api.java.StreamTableEnvironment;

public abstract class FlinkAbtractConsumerProducer {

    public StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
    public StreamTableEnvironment tableEnv = TableEnvironment.getTableEnvironment(env);

    public DataStream<BlocksTransactions> getDataStreamFromKafka(String arg, StreamExecutionEnvironment env) {
        FlinkKafkaConsumer011<BlocksTransactions> flinkBlocksTransactionsConsumer = new FlinkKafkaConsumer011<>(arg, new BlocksTransactionsSchema(), KafkaUtils.getProperties());
        flinkBlocksTransactionsConsumer.setStartFromEarliest();

        return env.addSource(flinkBlocksTransactionsConsumer);
    }

    public abstract DataStream<Test> getDataStreamFromTable(StreamTableEnvironment tableEnv, Table sqlResult);

    public abstract Table getNumberOfTransactionsByBlock(StreamTableEnvironment tableEnv);

    public abstract void sendDataStreamToElasticSearch(DataStream<Test> resultStream);
}
