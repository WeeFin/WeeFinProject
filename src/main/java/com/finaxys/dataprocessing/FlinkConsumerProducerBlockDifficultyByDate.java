package com.finaxys.dataprocessing;

import com.finaxys.model.BlocksTransactions;
import com.finaxys.queriesModel.AvgBlockDifficultyByDate;
import org.apache.flink.api.common.functions.RuntimeContext;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.connectors.elasticsearch.ElasticsearchSinkFunction;
import org.apache.flink.streaming.connectors.elasticsearch.RequestIndexer;
import org.apache.flink.streaming.connectors.elasticsearch6.ElasticsearchSink;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.java.StreamTableEnvironment;
import org.apache.flink.types.Row;
import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.Requests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FlinkConsumerProducerBlockDifficultyByDate extends FlinkAbtractConsumerProducer<AvgBlockDifficultyByDate> {


    public static void main(String[] args) throws Exception {

        FlinkConsumerProducerBlockDifficultyByDate flinkConsumerProducerBlockDifficultyByDate = new FlinkConsumerProducerBlockDifficultyByDate();

        DataStream<BlocksTransactions> blocksTransactionsDataStream = flinkConsumerProducerBlockDifficultyByDate.getDataStreamFromKafka(args[0], flinkConsumerProducerBlockDifficultyByDate.env);

        flinkConsumerProducerBlockDifficultyByDate.tableEnv.registerDataStream("blocksDifficultyTable", blocksTransactionsDataStream);

        Table sqlResult = flinkConsumerProducerBlockDifficultyByDate.getAvgBlockDifficultyByDate(flinkConsumerProducerBlockDifficultyByDate.tableEnv);

        DataStream<AvgBlockDifficultyByDate> resultStream = flinkConsumerProducerBlockDifficultyByDate.getDataStreamFromTable(flinkConsumerProducerBlockDifficultyByDate.tableEnv, sqlResult);

        resultStream.print();

        flinkConsumerProducerBlockDifficultyByDate.sendDataStreamToElasticSearch(resultStream);

        flinkConsumerProducerBlockDifficultyByDate.env.execute();
    }

    @Override
    public DataStream<AvgBlockDifficultyByDate> getDataStreamFromTable(StreamTableEnvironment tableEnv, Table sqlResult) {
        return tableEnv
                .toRetractStream(sqlResult, Row.class)
                .filter(t -> t.f0)
                .map(t -> {
                    Row r = t.f1;
                    long avgBlockDifficulty = Long.valueOf(r.getField(0).toString());
                    String dateTransactions = r.getField(1).toString();
                    return new AvgBlockDifficultyByDate(avgBlockDifficulty, dateTransactions);
                })
                .returns(AvgBlockDifficultyByDate.class);
    }

    public Table getAvgBlockDifficultyByDate(StreamTableEnvironment tableEnv) {
        return tableEnv.sqlQuery(
                "SELECT block_difficulty, year(cast(cast(block_timestamp as timestamp) as date)) FROM blocksDifficultyTable");
    }


    @Override
    public void sendDataStreamToElasticSearch(DataStream<AvgBlockDifficultyByDate> resultStream) {
        List<HttpHost> httpHosts = new ArrayList<>();
        httpHosts.add(new HttpHost("127.0.0.1", 9200, "http"));
        httpHosts.add(new HttpHost("10.2.3.1", 9200, "http"));

        ElasticsearchSink.Builder<AvgBlockDifficultyByDate> esSinkBuilder = new ElasticsearchSink.Builder<>(
                httpHosts, new AvgBlockDifficultyToElastic());

        resultStream.addSink(esSinkBuilder.build());
    }

    public static class AvgBlockDifficultyToElastic implements ElasticsearchSinkFunction<AvgBlockDifficultyByDate> {

        @Override
        public void process(AvgBlockDifficultyByDate element, RuntimeContext ctx, RequestIndexer indexer) {
            indexer.add(createIndexRequest(element));
        }

        public IndexRequest createIndexRequest(AvgBlockDifficultyByDate element) {
            Map<String, Object> json = new HashMap<>();
            json.put("avgBlockDifficulty", element.avgBlockDifficulty);
            json.put("Date", element.dateTransactions);

            return Requests.indexRequest()
                    .index("avg-block-difficulty-by-date")
                    .type("charts")
                    .source(json);
        }
    }
}
