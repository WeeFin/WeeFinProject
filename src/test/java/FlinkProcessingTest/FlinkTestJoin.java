package FlinkProcessingTest;

import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.TableEnvironment;
import org.apache.flink.table.api.java.StreamTableEnvironment;
import org.apache.flink.types.Row;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class FlinkTestJoin {

    public static StreamExecutionEnvironment env;
    public static StreamTableEnvironment tableEnv;

    @BeforeAll
    static void setUp() throws Exception {
        env = StreamExecutionEnvironment.getExecutionEnvironment();
        tableEnv = TableEnvironment.getTableEnvironment(env);
    }

    @Test
    void test() {

        DataStream<Tuple2<Integer, String>> test = env.fromElements(
                new Tuple2<>(1, "Bonjour"),
                new Tuple2<>(2, "World"));

        DataStream<Tuple2<Integer, String>> test2 = env.fromElements(
                new Tuple2<>(1, "Français"),
                new Tuple2<>(2, "English"));

        // register the datastreams into tables
        tableEnv.registerDataStream("testTable", test);
        tableEnv.registerDataStream("testTable2", test2);

        Table sqlTestResult = tableEnv.sqlQuery("" +
                "SELECT * FROM testTable " +
                "JOIN testTable2 " +
                "ON testTable.f0=testTable2.f0");

        // convert the table into DataStream
        DataStream<String> resultStream = tableEnv.toRetractStream(sqlTestResult, Row.class)
                .map(t -> {
                    Row row = t.f1;
                    return row.toString();
                }).returns(String.class);
        resultStream.print();
    }

    @AfterAll
    static void init() throws Exception {
        env.execute();
    }

}
