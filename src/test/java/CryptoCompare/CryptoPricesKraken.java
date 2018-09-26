package CryptoCompare;

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

public class CryptoPricesKraken {

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
                .path("/home/finaxys/Téléchargements/Kraken_ETHUSD_d.csv")
                .ignoreFirstLine()
                .fieldDelimiter(",")
                .field("DateRegistered", Types.STRING())
                .field("Symbol", Types.STRING())
                .field("OpenPrice", Types.DOUBLE())
                .field("HighPrice", Types.DOUBLE())
                .field("LowPrice", Types.DOUBLE())
                .field("ClosePrice", Types.DOUBLE())
                .field("VolumeFrom", Types.DOUBLE())
                .field("VolumeTo", Types.DOUBLE())
                .build();
    }


    public DataStream<Tuple2<String, Double>> getPricesDataStream() {

        tableEnv.registerTableSource("ETHPrices", csvTableSource);
        Table sqlResult = tableEnv.sqlQuery(
                "SELECT DateRegistered, ClosePrice FROM ETHPrices");
        DataStream<Tuple2<String, Double>> data = tableEnv.toRetractStream(sqlResult, Row.class)
                .map(x -> {
                    Row r = x.f1;
                    double price = Double.valueOf(r.getField(1).toString());
                    String date = r.getField(0).toString();
                    return new Tuple2<>(date, price);
                }).returns(TypeInformation.of(new TypeHint<Tuple2<String, Double>>() {
                }));

        data.print();

        return data;
    }

    @Test
    public void sendDataStreamToElastic() {
        DataStream<Tuple2<String, Double>> resultStream = getPricesDataStream();
        List<HttpHost> httpHosts = new ArrayList<>();
        httpHosts.add(new HttpHost("127.0.0.1", 9200, "http"));
        httpHosts.add(new HttpHost("10.2.3.1", 9200, "http"));

        ElasticsearchSinkFunction<Tuple2<String, Double>> esf = (Tuple2<String, Double> element, RuntimeContext ctx, RequestIndexer indexer) -> {
            // mapping of results
            Map<String, Object> json = new HashMap<>();
            json.put("Date", element.f0);
            json.put("AvgPrice", element.f1);

            // creation of index in elasticsearch
            IndexRequest r = Requests.indexRequest()
                    .index("avgpricebydate")
                    .type("avgprice")
                    .source(json);

            indexer.add(r);
        };

        ElasticsearchSink.Builder<Tuple2<String, Double>> esSinkBuilder = new ElasticsearchSink.Builder<>(
                httpHosts, esf);

        resultStream.addSink(esSinkBuilder.build());
    }

    @AfterAll
    public static void initJob() throws Exception {
        env.execute();
    }

}