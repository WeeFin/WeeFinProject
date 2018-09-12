package CryptoCompare;

import me.joshmcfarlin.CryptoCompareAPI.Historic;
import org.junit.jupiter.api.Test;

import java.util.Map;

/**
 * Test class for CryptoCompareAPI
 */
public class CryptoPricesTest {


    public Map<String, Double> getPriceFromTimestamp(long timestamp) throws Exception {
        return Historic.getPriceAtTime((int) timestamp, "ETH", "EUR");
    }

    @Test
    public void testGetPriceFromHistoricDate() throws Exception {
        Map<String, Double> m = getPriceFromTimestamp(1438918233);
        m.forEach((x, y) -> System.out.println(x + " " + y));
    }

    @Test
    public void testGetPriceFromToday() throws Exception {
        Map<String, Double> m = getPriceFromTimestamp(System.currentTimeMillis());
        m.forEach((x, y) -> System.out.println(x + " " + y));
    }


}
