package com.finaxys.loader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.finaxys.model.Blocks;
import com.finaxys.model.Erc20_Transfers;
import com.finaxys.model.Transactions;

public class LoadModel {

	private String csvFilesPath;

	public LoadModel(String csvFilesPath) {
		this.csvFilesPath = csvFilesPath;
	}

	/**
	 * Load Blocks classes from CSV data
	 */
	public List<Blocks> getListOfBlocksFromCSV() {

		List<Blocks> inputListBlocks = new ArrayList<>();

		File inputF = new File(csvFilesPath);
		try (InputStream inputFS = new FileInputStream(inputF);
				BufferedReader br = new BufferedReader(new InputStreamReader(inputFS))) {
			inputListBlocks = br.lines().skip(1)
					.map(line -> new Blocks(Long.valueOf(line.split(",")[0]), line.split(",")[1], line.split(",")[2],
							line.split(",")[3], line.split(",")[4], line.split(",")[5], line.split(",")[6],
							line.split(",")[7], line.split(",")[8], Double.valueOf(line.split(",")[9]),
							Double.valueOf(line.split(",")[10]), Long.valueOf(line.split(",")[11]), line.split(",")[12],
							Long.valueOf(line.split(",")[13]), Long.valueOf(line.split(",")[14]),
							Long.valueOf(line.split(",")[15]), Long.valueOf(line.split(",")[16])))
					.collect(Collectors.toList());

		} catch (Exception e) {

		}

		return inputListBlocks;

	}

	/**
	 * Load Transactions classes from CSV data
	 */
	public List<Transactions> getListOfTransactionsFromCSV() {

		List<Transactions> inputListTransactions = new ArrayList<>();

		File inputF = new File(csvFilesPath);
		try (InputStream inputFS = new FileInputStream(inputF);
				BufferedReader br = new BufferedReader(new InputStreamReader(inputFS))) {
			inputListTransactions = br.lines().skip(1)
					.map(line -> new Transactions(line.split(",")[0], Long.valueOf(line.split(",")[1]),
							line.split(",")[2], Long.valueOf(line.split(",")[3]), Long.valueOf(line.split(",")[4]),
							line.split(",")[5], line.split(",")[6], Double.valueOf(line.split(",")[7]),
							Long.valueOf(line.split(",")[8]), Long.valueOf(line.split(",")[9]), line.split(",")[10]))
					.collect(Collectors.toList());

		} catch (Exception e) {

		}

		return inputListTransactions;

	}

	/*
	 * Load ERC20 classes from CSV data
	 */
	public List<Erc20_Transfers> getListOfErc20_TransfersFromCSV() {

		List<Erc20_Transfers> inputListErc_20_Transfers = new ArrayList<>();

		File inputF = new File(csvFilesPath);
		try (InputStream inputFS = new FileInputStream(inputF);
				BufferedReader br = new BufferedReader(new InputStreamReader(inputFS))) {
			inputListErc_20_Transfers = br.lines().skip(1)
					.map(line -> new Erc20_Transfers(line.split(",")[0], line.split(",")[1], line.split(",")[2],
							Double.valueOf(line.split(",")[3]), line.split(",")[4], Long.valueOf(line.split(",")[5]),
							Long.valueOf(line.split(",")[6])))
					.collect(Collectors.toList());

		} catch (Exception e) {

		}

		return inputListErc_20_Transfers;

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

		}

	}
	
	
}
