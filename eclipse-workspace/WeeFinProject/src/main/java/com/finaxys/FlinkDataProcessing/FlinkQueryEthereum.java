package com.finaxys.FlinkDataProcessing;

import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.tuple.Tuple17;

public class FlinkQueryEthereum {

	public static void main(String[] args) throws Exception {

		final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

		DataSet<Tuple17<Integer, String, String, String, String, String, String, String, String, Double, Double, Integer, String, String, String, String, String>> csvInputBlocks = env
				.readCsvFile("/home/finaxys/ethereum-etl/output/blocks/").types(Integer.class, String.class, String.class, String.class, String.class,
						String.class, String.class, String.class, String.class, Double.class, Double.class,
						Integer.class, String.class, String.class, String.class, String.class, String.class);

		csvInputBlocks.print();

		env.execute("Flink Query Ethereum");

	}

}
