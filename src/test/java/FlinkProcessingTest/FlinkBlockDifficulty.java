package FlinkProcessingTest;

import org.apache.flink.api.common.functions.RuntimeContext;
import org.apache.flink.api.common.typeinfo.TypeHint;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.elasticsearch.ElasticsearchSinkFunction;
import org.apache.flink.streaming.connectors.elasticsearch.RequestIndexer;
import org.apache.flink.streaming.connectors.elasticsearch6.ElasticsearchSink;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.Types;
import org.apache.flink.table.api.java.StreamTableEnvironment;
import org.apache.flink.table.sources.CsvTableSource;
import org.apache.flink.types.Row;
import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.Requests;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FlinkBlockDifficulty {

    public static StreamExecutionEnvironment env;
    public static StreamTableEnvironment tableEnv;
    public static CsvTableSource csvTableSource;


    @BeforeAll
    public static void setUp() {
        env = StreamExecutionEnvironment.getExecutionEnvironment();
        tableEnv = StreamTableEnvironment.getTableEnvironment(env);
    }

    @Test
    public void configureTableSource() {
        csvTableSource = CsvTableSource.builder()
                .path("/home/finaxys/ethereum-etl/output/joinedTables/blocksTransactions_00100000_00199999.csv")
                .ignoreFirstLine()
                .fieldDelimiter(",")
                .field("block_number", Types.LONG())
                .field("block_hash", Types.STRING())
                .field("block_parent_hash", Types.STRING())
                .field("block_nonce", Types.STRING())
                .field("block_sha3_uncles", Types.STRING())
                .field("block_logs_bloom", Types.STRING())
                .field("block_transactions_root", Types.STRING())
                .field("block_state_root", Types.STRING())
                .field("block_miner", Types.STRING())
                .field("block_difficulty", Types.LONG())
                .field("block_total_difficulty", Types.DOUBLE())
                .field("block_size", Types.LONG())
                .field("block_extra_data", Types.STRING())
                .field("block_gas_limit", Types.LONG())
                .field("block_gas_used", Types.LONG())
                .field("block_timestamp", Types.LONG())
                .field("block_transaction_count", Types.LONG())
                .field("tx_hash", Types.STRING())
                .field("tx_nonce", Types.LONG())
                .field("tx_block_number", Types.LONG())
                .field("tx_index", Types.LONG())
                .field("tx_from", Types.STRING())
                .field("tx_to", Types.STRING())
                .field("tx_value", Types.DOUBLE())
                .field("tx_gas", Types.LONG())
                .field("tx_gas_price", Types.LONG())
                .field("tx_input", Types.STRING())
                .build();
    }


    @Test
    public DataStream<Tuple2<String, Long>> getPricesDataStream() {

        tableEnv.registerTableSource("blocksTransactions", csvTableSource);
        Table sqlResult = tableEnv.scan("blocksTransactions");
        Table result = sqlResult.select("block_difficulty, block_timestamp");
        DataStream<Tuple2<String, Long>> data = tableEnv.toRetractStream(result, Row.class)
                .map(x -> {
                    Row r = x.f1;
                    long difficulty = Long.valueOf(r.getField(1).toString());
                    String date = r.getField(0).toString();
                    return new Tuple2<>(date, difficulty);
                }).returns(TypeInformation.of(new TypeHint<Tuple2<String, Long>>() {
                }));

        data.print();

        return data;
    }


    @Test
    public void sendDataStreamToElastic() {
        DataStream<Tuple2<String, Long>> resultStream = getPricesDataStream();
        List<HttpHost> httpHosts = new ArrayList<>();
        httpHosts.add(new HttpHost("127.0.0.1", 9200, "http"));
        httpHosts.add(new HttpHost("10.2.3.1", 9200, "http"));

        ElasticsearchSinkFunction<Tuple2<String, Long>> esf = (Tuple2<String, Long> element, RuntimeContext ctx, RequestIndexer indexer) -> {
            // mapping of results
            Map<String, Object> json = new HashMap<>();
            json.put("Date", element.f0);
            json.put("BlockDifficulty", element.f1);

            // creation of index in elasticsearch
            IndexRequest r = Requests.indexRequest()
                    .index("blockdifficulty")
                    .type("chart")
                    .source(json);

            indexer.add(r);
        };

        ElasticsearchSink.Builder<Tuple2<String, Long>> esSinkBuilder = new ElasticsearchSink.Builder<>(
                httpHosts, esf);

        resultStream.addSink(esSinkBuilder.build());
    }

    @AfterAll
    public static void initJob() throws Exception {
        env.execute();
    }
}
