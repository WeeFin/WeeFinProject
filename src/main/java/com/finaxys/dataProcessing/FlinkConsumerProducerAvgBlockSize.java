package com.finaxys.dataProcessing;

import com.finaxys.model.BlocksTransactions;
import com.finaxys.queriesModel.AvgBlockSizeByDate;
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

public class FlinkConsumerProducerAvgBlockSize extends FlinkAbtractConsumerProducer<AvgBlockSizeByDate> {


    public static void main(String[] args) throws Exception {

        FlinkConsumerProducerAvgBlockSize flinkConsumerProducerAvgBlockSize = new FlinkConsumerProducerAvgBlockSize();

        DataStream<BlocksTransactions> blocksTransactionsDataStream = flinkConsumerProducerAvgBlockSize.getDataStreamFromKafka(args[0], flinkConsumerProducerAvgBlockSize.env);

        flinkConsumerProducerAvgBlockSize.tableEnv.registerDataStream("blocksSizeTable", blocksTransactionsDataStream);

        Table sqlResult = flinkConsumerProducerAvgBlockSize.getAvgBlockSizeByDate(flinkConsumerProducerAvgBlockSize.tableEnv);

        DataStream<AvgBlockSizeByDate> resultStream = flinkConsumerProducerAvgBlockSize.getDataStreamFromTable(flinkConsumerProducerAvgBlockSize.tableEnv, sqlResult);

        resultStream.print();

        flinkConsumerProducerAvgBlockSize.sendDataStreamToElasticSearch(resultStream);

        flinkConsumerProducerAvgBlockSize.env.execute();

    }


    @Override
    public DataStream<AvgBlockSizeByDate> getDataStreamFromTable(StreamTableEnvironment tableEnv, Table sqlResult) {
        return tableEnv
                .toRetractStream(sqlResult, Row.class)
                .filter(t -> t.f0)
                .map(t -> {
                    Row r = t.f1;
                    long avgBlockSize = Long.valueOf(r.getField(0).toString());
                    String dateTransactions = r.getField(1).toString();
                    return new AvgBlockSizeByDate(avgBlockSize, dateTransactions);
                })
                .returns(AvgBlockSizeByDate.class);
    }

    public Table getAvgBlockSizeByDate(StreamTableEnvironment tableEnv) {
        return tableEnv.sqlQuery(
                "SELECT avg(block_size),block_timestamp FROM blocksSizeTable group by block_timestamp");
    }

    @Override
    public void sendDataStreamToElasticSearch(DataStream<AvgBlockSizeByDate> resultStream) {
        List<HttpHost> httpHosts = new ArrayList<>();
        httpHosts.add(new HttpHost("127.0.0.1", 9200, "http"));
        httpHosts.add(new HttpHost("10.2.3.1", 9200, "http"));

        ElasticsearchSink.Builder<AvgBlockSizeByDate> esSinkBuilder = new ElasticsearchSink.Builder<>(
                httpHosts, new AvgBlockSizeToElastic());

        resultStream.addSink(esSinkBuilder.build());
    }

    public static class AvgBlockSizeToElastic implements ElasticsearchSinkFunction<AvgBlockSizeByDate> {

        @Override
        public void process(AvgBlockSizeByDate element, RuntimeContext ctx, RequestIndexer indexer) {
            indexer.add(createIndexRequest(element));
        }

        public IndexRequest createIndexRequest(AvgBlockSizeByDate element) {
            Map<String, String> json = new HashMap<>();
            json.put("avgBlockSize", String.valueOf(element.avgBlockSize));
            json.put("numberOfTransactions", element.dateTransactions);

            return Requests.indexRequest()
                    .index("avg-block-size-by-date")
                    .type("avg-block-size")
                    .source(json);
        }
    }
}
