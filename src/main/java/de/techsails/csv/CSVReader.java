/**
 *CSVReader reads a CSV file
 *
 *@author yousuf.amanuel
 *@version 22.November.2018
 *
 */
package de.techsails.csv;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CSVReader {

	private ClassLoader classLoader;
	private String line;
	private String cvsSplitBy;
	private List<String[]> lines;
	InputStream is;
	
	public CSVReader(String csvFileName) {
		classLoader = Thread.currentThread().getContextClassLoader();
		is = classLoader.getResourceAsStream(csvFileName);
		line = "";
		cvsSplitBy = ",";
		readFile();
	}

	private void readFile() {
		lines = new ArrayList<>();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                lines.add(line.split(cvsSplitBy));
            }
            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
	}

	public List<String[]> getLines() {
		return lines;
	}

}