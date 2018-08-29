package ConfigurationTest;

import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.TableEnvironment;
import org.apache.flink.table.api.java.StreamTableEnvironment;
import org.apache.flink.types.Row;

public class FlinkTestJoin {

    public static void main(String[] args) throws Exception {

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        StreamTableEnvironment tableEnv = TableEnvironment.getTableEnvironment(env);

        DataStream<Tuple2<Integer, String>> test = env.fromElements(
                new Tuple2<>(1, "Bonjour"),
                new Tuple2<>(2, "World"));

        DataStream<Tuple2<Integer, String>> test2 = env.fromElements(
                new Tuple2<>(1, "Français"),
                new Tuple2<>(2, "English"));

        tableEnv.registerDataStream("testTable", test);
        tableEnv.registerDataStream("testTable2", test2);

        Table sqlTestResult = tableEnv.sqlQuery("" +
                "SELECT * FROM testTable " +
                "JOIN testTable2 " +
                "ON testTable.f0=testTable2.f0");

        DataStream<Tuple2<Boolean, Row>> resultStream = tableEnv.toRetractStream(sqlTestResult, Row.class);
        resultStream.print();

        env.execute();

    }

}
