package com.finaxys.dataProcessing;

import com.finaxys.model.BlocksTransactions;
import com.finaxys.model.Test;
import com.finaxys.schema.BlocksTransactionsSchema;
import com.finaxys.schema.TestSchema;
import org.apache.flink.api.common.functions.RuntimeContext;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.elasticsearch.ElasticsearchSinkFunction;
import org.apache.flink.streaming.connectors.elasticsearch.RequestIndexer;
import org.apache.flink.streaming.connectors.elasticsearch6.ElasticsearchSink;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer011;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer011;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.TableEnvironment;
import org.apache.flink.table.api.java.StreamTableEnvironment;
import org.apache.flink.types.Row;
import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.Requests;

import java.util.*;

public class FlinkConsumerProducer {

    public static void main(String[] args) throws Exception {

        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        final StreamTableEnvironment tableEnv = TableEnvironment.getTableEnvironment(env);
        // configure Kafka consumer
        Properties props = new Properties();
        props.setProperty("bootstrap.servers", "localhost:9092"); // Broker default host:port
        props.setProperty("group.id", "flink-consumer"); // Consumer group ID

        FlinkKafkaConsumer011<BlocksTransactions> flinkBlocksTransactionsConsumer = new FlinkKafkaConsumer011<>(args[0], new BlocksTransactionsSchema(), props);
        flinkBlocksTransactionsConsumer.setStartFromEarliest();

        DataStream<BlocksTransactions> blocksTransactions = env.addSource(flinkBlocksTransactionsConsumer);


        tableEnv.registerDataStream("blocksTransactionsTable", blocksTransactions);

        Table sqlResult
                = tableEnv.sqlQuery(
                "SELECT block_number, count(tx_hash) " +
                        "FROM blocksTransactionsTable " +
                        "GROUP BY block_number");


        DataStream<Test> resultStream = tableEnv
                .toRetractStream(sqlResult, Row.class)
                .filter(t -> t.f0)
                .map(t -> {
                    Row r = t.f1;
                    String block_hash = r.getField(0).toString();
                    long nbTransactions = Long.valueOf(r.getField(1).toString());
                    return new Test(block_hash, nbTransactions);
                })
                .returns(Test.class);

        //resultStream.print();

        // send data to elasticsearch

        List<HttpHost> httpHosts = new ArrayList<>();
        httpHosts.add(new HttpHost("127.0.0.1", 9200, "http"));
        httpHosts.add(new HttpHost("10.2.3.1", 9200, "http"));

        resultStream.addSink(new FlinkKafkaProducer011<>("localhost:9092", "TargetTopic", new TestSchema()));

        ElasticsearchSink.Builder<Test> esSinkBuilder = new ElasticsearchSink.Builder<>(
                httpHosts, new NumberOfTransactionsByBlocks());

        resultStream.addSink(esSinkBuilder.build());

        env.execute();

    }

    public static class NumberOfTransactionsByBlocks implements ElasticsearchSinkFunction<Test> {

        public void process(Test element, RuntimeContext ctx, RequestIndexer indexer) {
            indexer.add(createIndexRequest(element));

        }

        public IndexRequest createIndexRequest(Test element) {
            Map<String, String> json = new HashMap<>();
            json.put("block_number", element.block_number);
            json.put("numberOfTransactions", String.valueOf(element.nbTransactions));

            return Requests.indexRequest()
                    .index("nbtransactionsbyblocks")
                    .type("count-transactions")
                    .source(json);
        }
    }

}
