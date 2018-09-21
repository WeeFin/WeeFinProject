package com.finaxys.preprocessing;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;

public class CryptoPrice {

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        JSONObject json = null;
        try (InputStream is = new URL(url).openStream()) {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            json = new JSONObject(jsonText);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }

    public static double getUSDFromETHPriceAndTimestamp(String timestamp) {
        JSONObject json = null;
        System.err.println(timestamp);
        String url = "https://min-api.cryptocompare.com/data/pricehistorical?fsym=ETH&tsyms=USD&ts=" + timestamp;
        try {
            json = readJsonFromUrl(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.err.println(json.toString());
        JSONObject USDObject = (JSONObject) json.get("ETH");
        double priceValue = (double) USDObject.get("USD");
        System.err.println(priceValue);
        return priceValue;
    }
}
