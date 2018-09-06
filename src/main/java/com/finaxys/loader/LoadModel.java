package com.finaxys.loader;

import com.finaxys.model.Blocks;
import com.finaxys.model.BlocksTransactions;
import com.finaxys.model.Erc20_Transfers;
import com.finaxys.model.Transactions;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class is used for Loading Model classes like Blocks or Transactions on our application
 */
public class LoadModel {

    private String csvFilesPath;

    /**
     * @param csvFilesPath : Path in which there are your CSV files
     */
    public LoadModel(String csvFilesPath) {
        this.csvFilesPath = csvFilesPath;
    }

    /**
     * @return List of blocks from CSV files
     */
    public List<Blocks> getListOfBlocksFromCSV() {

        List<Blocks> inputListBlocks = new ArrayList<>();

        File inputF = new File(csvFilesPath);
        try (InputStream inputFS = new FileInputStream(inputF);
             BufferedReader br = new BufferedReader(new InputStreamReader(inputFS))) {
            inputListBlocks =
                    br.lines()
                            .skip(1)
                            .map(line -> {
                                        String[] splittedValues = line.split(",");
                                        return new Blocks(Long.valueOf(splittedValues[0]), splittedValues[1], splittedValues[2],
                                                splittedValues[3], splittedValues[4], splittedValues[5], splittedValues[6],
                                                splittedValues[7], splittedValues[8], Double.valueOf(splittedValues[9]),
                                                Double.valueOf(splittedValues[10]), Long.valueOf(splittedValues[11]), splittedValues[12],
                                                Long.valueOf(splittedValues[13]), Long.valueOf(splittedValues[14]),
                                                Long.valueOf(splittedValues[15]), Long.valueOf(splittedValues[16]));
                                    }
                            )
                            .collect(Collectors.toList());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return inputListBlocks;

    }

    /**
     * @return List of transactions from CSV files
     */
    public List<Transactions> getListOfTransactionsFromCSV() {

        List<Transactions> inputListTransactions = new ArrayList<>();

        File inputF = new File(csvFilesPath);
        try (InputStream inputFS = new FileInputStream(inputF);
             BufferedReader br = new BufferedReader(new InputStreamReader(inputFS))) {
            inputListTransactions =
                    br.lines()
                            .skip(1)
                            .map(line -> {
                                String[] splittedValues = line.split(",");
                                System.out.println((splittedValues[7]));
                                return new Transactions(splittedValues[0], Long.valueOf(splittedValues[1]),
                                        splittedValues[2], Long.valueOf(splittedValues[3]), Long.valueOf(splittedValues[4]),
                                        splittedValues[5], splittedValues[6], Double.valueOf(splittedValues[7]),
                                        Long.valueOf(splittedValues[8]), Long.valueOf(splittedValues[9]), splittedValues[10]);
                            })
                            .collect(Collectors.toList());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return inputListTransactions;

    }

    /**
     * @return List of ERC20 transfers from CSV Files
     */
    public List<Erc20_Transfers> getListOfErc20_TransfersFromCSV() {

        List<Erc20_Transfers> inputListErc_20_Transfers = new ArrayList<>();

        File inputF = new File(csvFilesPath);
        try (InputStream inputFS = new FileInputStream(inputF);
             BufferedReader br = new BufferedReader(new InputStreamReader(inputFS))) {
            inputListErc_20_Transfers =
                    br.lines()
                            .skip(1)
                            .map(line -> {
                                String[] splittedValues = line.split(",");
                                return new Erc20_Transfers(splittedValues[0], splittedValues[1], splittedValues[2],
                                        Double.valueOf(splittedValues[3]), splittedValues[4], Long.valueOf(splittedValues[5]),
                                        Long.valueOf(splittedValues[6]));
                            })
                            .collect(Collectors.toList());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return inputListErc_20_Transfers;

    }

    /**
     * @return List of transactions and blocks from joined CSV Files
     */
    public List<BlocksTransactions> getListOfBlocksTransactionsFromCSV() {

        List<BlocksTransactions> inputListBlocksTransactions = new ArrayList<>();

        File inputF = new File(csvFilesPath);
        try (InputStream inputFS = new FileInputStream(inputF);
             BufferedReader br = new BufferedReader(new InputStreamReader(inputFS))) {
            inputListBlocksTransactions =
                    br.lines()
                            .skip(1)
                            .map(line -> {
                                        String[] splittedValues = line.split(",");
                                        return new BlocksTransactions(Long.valueOf(splittedValues[0]), splittedValues[1], splittedValues[2],
                                                splittedValues[3], splittedValues[4], splittedValues[5], splittedValues[6],
                                                splittedValues[7], splittedValues[8], Double.valueOf(splittedValues[9]),
                                                Double.valueOf(splittedValues[10]), Long.valueOf(splittedValues[11]), splittedValues[12],
                                                Long.valueOf(splittedValues[13]), Long.valueOf(splittedValues[14]),
                                                Long.valueOf(splittedValues[15]), Long.valueOf(splittedValues[16]), splittedValues[17], Long.valueOf(splittedValues[18]),
                                                Long.valueOf(splittedValues[19]), Long.valueOf(splittedValues[20]),
                                                splittedValues[21], splittedValues[22], Double.valueOf(splittedValues[23]),
                                                Long.valueOf(splittedValues[24]), Long.valueOf(splittedValues[25]), splittedValues[26]);
                                    }
                            )
                            .collect(Collectors.toList());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return inputListBlocksTransactions;

    }

    /**
     * Print every line from a CSV File
     */
    public void printCSVFilesLines() {

        File inputF = new File(csvFilesPath);
        System.out.println(inputF.length());
        try (InputStream inputFS = new FileInputStream(inputF);
             BufferedReader br = new BufferedReader(new InputStreamReader(inputFS))) {
            br.lines().skip(1).forEach(System.out::println);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
