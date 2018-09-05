package com.finaxys.dataProcessing;

import com.finaxys.model.BlocksTransactions;
import com.finaxys.model.Test;
import com.finaxys.schema.BlocksTransactionsSchema;
import com.finaxys.schema.TestSchema;
import com.finaxys.utils.KafkaUtils;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FlinkConsumerProducerNbTransactionsByBlocks extends FlinkAbtractConsumerProducer {


    public static void main(String[] args) throws Exception {

        FlinkAbtractConsumerProducer facp = new FlinkConsumerProducerNbTransactionsByBlocks();

        // get blockstransactions data from Kafka and put it in a DataStream
        DataStream<BlocksTransactions> blocksTransactions = facp.getDataStreamFromKafka(args[0], facp.env);

        // register datastream into a Table
        facp.tableEnv.registerDataStream("blocksTransactionsTable", blocksTransactions);

        // get Result of SQL query from DataStream
        Table sqlResult = facp.getNumberOfTransactionsByBlock(facp.tableEnv);

        // Convert Table to DataStream
        DataStream<Test> resultStream = facp.getDataStreamFromTable(facp.tableEnv, sqlResult);

        resultStream.print();

        // send datastream data to elasticsearch
        facp.sendDataStreamToElasticSearch(resultStream);

        facp.env.execute();

    }

    public DataStream<Test> getDataStreamFromTable(StreamTableEnvironment tableEnv, Table sqlResult) {
        return tableEnv
                .toRetractStream(sqlResult, Row.class)
                .filter(t -> t.f0)
                .map(t -> {
                    Row r = t.f1;
                    String block_hash = r.getField(0).toString();
                    long nbTransactions = Long.valueOf(r.getField(1).toString());
                    return new Test(block_hash, nbTransactions);
                })
                .returns(Test.class);
    }


    public Table getNumberOfTransactionsByBlock(StreamTableEnvironment tableEnv) {
        return tableEnv.sqlQuery(
                "SELECT block_number, count(tx_hash) " +
                        "FROM blocksTransactionsTable " +
                        "GROUP BY block_number");
    }

    public void sendDataStreamToElasticSearch(DataStream<Test> resultStream) {
        List<HttpHost> httpHosts = new ArrayList<>();
        httpHosts.add(new HttpHost("127.0.0.1", 9200, "http"));
        httpHosts.add(new HttpHost("10.2.3.1", 9200, "http"));

        resultStream.addSink(new FlinkKafkaProducer011<>("localhost:9092", "TargetTopic", new TestSchema()));

        ElasticsearchSink.Builder<Test> esSinkBuilder = new ElasticsearchSink.Builder<>(
                httpHosts, new NumberOfTransactionsByBlocks());

        resultStream.addSink(esSinkBuilder.build());
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
