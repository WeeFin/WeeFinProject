package com.finaxys.queriesModel;

import com.fasterxml.jackson.databind.ObjectMapper;

public class AvgBlockSizeByDate {

    public long avgBlockSize;
    public String dateTransactions;

    public AvgBlockSizeByDate(long avgBlockSize, String dateTransactions) {
        this.avgBlockSize = avgBlockSize;
        this.dateTransactions = dateTransactions;
    }

    public static AvgBlockSizeByDate fromString(String res) {
        ObjectMapper mapper = new ObjectMapper();
        AvgBlockSizeByDate avgBlockSizeByDate = null;
        try {
            avgBlockSizeByDate = mapper.readValue(res, AvgBlockSizeByDate.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return avgBlockSizeByDate;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AvgBlockSizeByDate{");
        sb.append("avgBlockSize=").append(avgBlockSize);
        sb.append(", dateTransactions='").append(dateTransactions).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
