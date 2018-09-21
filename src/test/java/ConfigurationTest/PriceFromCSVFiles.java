package ConfigurationTest;

import org.junit.jupiter.api.Test;
import org.supercsv.io.CsvListReader;
import org.supercsv.prefs.CsvPreference;

import java.io.FileReader;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

public class PriceFromCSVFiles {

    @Test
    public String convertTimestampToDate(long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Timestamp(timestamp * 1000));
    }

    @Test
    public void addPriceColumnToCSVFiles() throws Exception {

        CsvListReader reader = new CsvListReader(new FileReader("/home/finaxys/test.csv"), CsvPreference.STANDARD_PREFERENCE);
        // CsvListWriter writer = new CsvListWriter(new FileWriter("/home/finaxys/output.csv"), CsvPreference.STANDARD_PREFERENCE);
        List<String> columns;
        while ((columns = reader.read()) != null) {
            //System.out.println("Input: " + columns);
            String value = columns.get(15);
            System.out.println(value);
           /*columns.add("Column_2");
            columns.add("Last_column");
            System.out.println("Output: " + columns);
            writer.write(columns);*/
        }
        reader.close();
        //writer.close();

    }

}
