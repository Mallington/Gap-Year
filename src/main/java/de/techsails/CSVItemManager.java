/**
 *CSVItemManager manages the items data that are saved in the CSV
 *
 *@author yousuf.amanuel
 *@version 22.November.2018
 *
 */
package de.techsails;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import de.techsails.csv.CSVReader;

public class CSVItemManager {
	private CSVReader itemsReader;
	private CSVReader dependenciesReader;
	private List<Item> items;
	private HashMap<Integer, TreeMap<Integer, Integer>> dependencies;

	public CSVItemManager() {
		itemsReader = new CSVReader("CSV_Data/items.csv");
		// dependenciesReader = new CSVReader("CSV_Data/dependencies.csv");
		items = new ArrayList<>();
		// dependencies = new ArrayList<>();
		dependencies = new HashMap<>();
		createItems(itemsReader.getLines());
		// createDependencies(dependenciesReader.getLines());
	}

	private void createItems(List<String[]> lines) {
		for (String[] item : lines) {
			items.add(new Item(Integer.parseInt(item[0]), item[1], item[2], item[3], Double.parseDouble(item[4]),
					item[5], getCategories(item[6])));
		}
	}

	private void createDependencies(List<String[]> lines) {
		for (int i = 0; i < items.size(); i++) {
			TreeMap<Integer, Integer> value = new TreeMap<>();
			dependencies.put(items.get(i).getId(), value);
		}

		for (String[] dependency : lines) {
			dependencies.get(Integer.parseInt(dependency[0])).put(Integer.parseInt(dependency[1]),
					Integer.parseInt(dependency[2]));
			dependencies.get(Integer.parseInt(dependency[1])).put(Integer.parseInt(dependency[0]),
					Integer.parseInt(dependency[2]));
		}
	}

	String[] getCategories(String s) {
		return s.split("\\|");
	}

	public Item getItem(int id) {
		if (id >= items.size() || id < 1)
			return new Item();
		return items.get(id - 1);
	}

	public List<Item> getItems(int itemFromId, int itemToId) {
		List<Item> tempItems = new ArrayList<>();
		for (int i = itemFromId; i <= itemToId; i++) {
			tempItems.add(items.get(i - 1));
		}
		if (tempItems.size() < 1)
			tempItems.add(new Item());
		return tempItems;
	}

	public List<Item> getItemsBy(String getItemsBy, String params) {
		List<Item> tempItems = new ArrayList<>();
		String[] typesArray = params.split("/");
		for (int i = 0; i < items.size(); i++) {
			for (int j = 0; j < typesArray.length; j++) {
				if (items.get(i).getInfo(getItemsBy).toLowerCase().contains(typesArray[j].toLowerCase())) {
					tempItems.add(items.get(i));
					break;
				}
			}
		}
		if (tempItems.size() < 1)
			tempItems.add(new Item());
		return tempItems;
	}

	public List<Item> getItemsByPrice(double priceMin, double priceMax) {
		List<Item> tempItems = new ArrayList<>();
		for (int i = 0; i < items.size(); i++) {
			if (items.get(i).getPrice() >= priceMin && items.get(i).getPrice() <= priceMax)
				tempItems.add(items.get(i));
		}
		if (tempItems.size() < 1)
			tempItems.add(new Item());
		return tempItems;
	}

	public List<Item> getRecommendation(int id, int size) {
		List<Item> tempItems = new ArrayList<>();

		TreeMap<Integer, Integer> e = dependencies.get(id);
		//e = (TreeMap<Integer, Integer>) sortByValue(e, false);

		for (int i = 0; i < Math.min(size, e.size()); i++) {
			// Integer key = (Integer) e.keySet().toArray()[i];
			// Integer value = e.get(key);
			tempItems.add(items.get((Integer) e.keySet().toArray()[i] - 1));
			// System.out.println(key + " " + value);
		}

		// for (Entry<Integer, Integer> entry : e.entrySet()) {}

		if (tempItems.size() < 1)
			tempItems.add(new Item());
		return tempItems;
	}

	private Map<Integer, Double> sortByValue(Map<Integer, Double> unsortedMap, boolean asc) {
		Map<Integer, Double> sortedMap = new TreeMap<Integer, Double>(new ValueComparator(unsortedMap, asc));
		sortedMap.putAll(unsortedMap);
		return sortedMap;
	}

	public void createDependenciesTable() throws IOException, URISyntaxException {
		/*String fileName = "CSV_Data/dependencies.csv";
		File file = new File(Thread.currentThread().getContextClassLoader().getResource(fileName).toURI());
		/*
		 * if(!file.exists()) { file.createNewFile(); }
		 */
		
		String fileName = "d:/dependencies.csv";
		File file = new File(fileName);
		
		file.setWritable(true);
		FileWriter fw = new FileWriter(file);
		BufferedWriter bw = new BufferedWriter(fw);

		Random clicks = new Random();

		for (int i = 0; i < items.size(); i++) {
			String[] cat = items.get(i).getCategory();

			for (int k = 0; k < items.size(); k++) {
				if(i != k) {
					for (int j = 0; j < cat.length; j++) {

						//if (!items.get(i).getType().equals(items.get(k).getType()) && Arrays.toString(items.get(k).getCategory()).contains(cat[j])) {
						if (Arrays.toString(items.get(k).getCategory()).contains(cat[j])) {
							bw.write(items.get(i).getId() + "," + items.get(k).getId() + "," + (clicks.nextInt(50)/10.));
							bw.newLine();
							break;
						}
					}
				}
			}
		}
		bw.flush();
		bw.close();
		fw.close();
	}

	public void createTestUsers(int n, int lastUserID) throws IOException, URISyntaxException {
		String fileName = "CSV_Data/users.csv";
		File file = new File(Thread.currentThread().getContextClassLoader().getResource(fileName).toURI());
		/*
		 * if(!file.exists()) { file.createNewFile(); }
		 */
		if (!file.exists()) {
			file.createNewFile();
		}
		file.setWritable(true);
		System.out.println(file);
		FileWriter fw = new FileWriter(file);
		BufferedWriter bw = new BufferedWriter(fw);

		for (int i = 1+lastUserID; i < n+lastUserID; i++) {

			bw.write(i+","+createRandomString(12));
			bw.newLine();
		}
		
		bw.flush();
		bw.close();
		fw.close();
	}

	private String createRandomString(int randomStringLength) {
		String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String lower = upper.toLowerCase();
		String digits = "0123456789";

		String allChars = upper + lower + digits;

		StringBuilder stringBuffer = new StringBuilder();
		Random random = new Random();

		while (stringBuffer.length() < randomStringLength) { // length of the random string.
			int index = random.nextInt(allChars.length());
			stringBuffer.append(allChars.charAt(index));
		}
		return stringBuffer.toString();
	}
}
