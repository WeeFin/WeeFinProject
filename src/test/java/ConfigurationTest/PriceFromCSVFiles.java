package ConfigurationTest;

import com.finaxys.preprocessing.CryptoPrice;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class PriceFromCSVFiles {

    @Test
    public String convertTimestampToDate(long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Timestamp(timestamp * 1000));
    }

    @Test
    public void addPriceColumnToCSVFiles() throws Exception {

        CryptoPrice.getUSDFromETHPriceAndTimestamp(String.valueOf(System.currentTimeMillis()));
       /* CsvListReader reader = new CsvListReader(new FileReader("/home/christophe/joinedTables/blocksTransactions.csv"), CsvPreference.STANDARD_PREFERENCE);
        // CsvListWriter writer = new CsvListWriter(new FileWriter("/home/finaxys/output.csv"), CsvPreference.STANDARD_PREFERENCE);
        List<String> columns;
        while ((columns = reader.read()) != null) {
            //System.out.println("Input: " + columns);
            String value = columns.get(15);
            System.out.println(value);
           *//*columns.add("Column_2");
            columns.add("Last_column");
            System.out.println("Output: " + columns);
            writer.write(columns);*//*
        }
        reader.close();
        //writer.close();*/

    }

}
