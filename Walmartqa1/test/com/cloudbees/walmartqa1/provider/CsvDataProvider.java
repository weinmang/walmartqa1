package com.cloudbees.walmartqa1.provider;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.testng.annotations.DataProvider;

public class CsvDataProvider {

	@DataProvider(name = "csvReader")
	public static Object[][] provideTestData(Method m) {

		String methodName = m.getName();
		String className = m.getDeclaringClass().getName();
		String fileDir  = System.getProperty("datafile.dir", ".");
		String fileName = System.getProperty("data.file", "testng_data.csv");
		List<HashMap<String, String>> hashArray = new ArrayList<HashMap<String, String>>();

		Path path = Paths.get(fileDir,fileName);
		try (BufferedReader testDataFile = Files.newBufferedReader(path, StandardCharsets.US_ASCII)){
			int lineNum = 0;
			while (testDataFile.ready()) {
				String line = testDataFile.readLine();
				// Line number tracker
				lineNum++;
				// Handle comments with Hashes
				if (line.startsWith("#")) {
					continue;
				}
				// Split on commas - handle 0 or even number of quotes before comma
				// by adding look ahead pattern (?=([^\"]*\"[^\"]*\")*[^\"]*$) 
				String[] data = line.split(",");

				// Expected at least two tokens (test class and test method) per line to 
				// identify ownership of parameter pairs
				if (data.length < 2) {
					continue;
				}
				// Filter data and transform into Object array.
				if (className.equalsIgnoreCase(data[0])
						&& methodName.equalsIgnoreCase(data[1])) {
					HashMap<String, String> map = new HashMap<String, String>();
					String key = null;
					String value = null;
					// Require tokens presented as key value pairs.
					try {
						for (int i = 2; i < data.length; i++) {
							key = data[i].replaceAll("^\"|\"$", "");
							value = data[++i].replaceAll("^\"|\"$", "");
							map.put(key, value.trim());
						}
					} catch (ArrayIndexOutOfBoundsException ex) {
						System.err.println("ArrayIndexOutOfBoundsException - Likely cause by an ill defined key value pair on line:"
								+ lineNum + " in " + fileName);
					}
				}
			}
		} catch (IOException e) {
			System.err.println("Unexpected reader error: "+e.getMessage());
		}
		Object[] array = hashArray.toArray();
		int size = array.length;
		// Convert One dimensional array to two dimensional
		Object[][] data = new Object[size][1];
		for (int i = 0; i < size; i++) {
			data[i][0] = array[i];
		}
		return data;
	}
}
