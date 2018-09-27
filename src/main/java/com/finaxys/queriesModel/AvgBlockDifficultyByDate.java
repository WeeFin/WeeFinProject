package com.finaxys.queriesModel;

import com.fasterxml.jackson.databind.ObjectMapper;

public class AvgBlockDifficultyByDate {

    public long avgBlockDifficulty;
    public String dateTransactions;

    public AvgBlockDifficultyByDate(long avgBlockDifficulty, String dateTransactions) {
        this.avgBlockDifficulty = avgBlockDifficulty;
        this.dateTransactions = dateTransactions;
    }

    public static AvgBlockDifficultyByDate fromString(String res) {
        ObjectMapper mapper = new ObjectMapper();
        AvgBlockDifficultyByDate avgBlockDifficultyByDate = null;
        try {
            avgBlockDifficultyByDate = mapper.readValue(res, AvgBlockDifficultyByDate.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return avgBlockDifficultyByDate;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("(");
        sb.append(dateTransactions).append(",");
        sb.append(avgBlockDifficulty);
        sb.append(')');
        return sb.toString();
    }
}
