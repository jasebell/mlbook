package chapter6;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;

public class DataConverter {

	public DataConverter() {
		try {
			FileWriter outputWriter = new FileWriter(
					"/Users/Jason/Downloads/output.dat");
			int txcount = 0;

			BufferedReader csvReader = new BufferedReader(new FileReader(
					"/Users/Jason/Downloads/rawdata.csv"));

			// read the first line in but do nothing with it.
			String thisLine = csvReader.readLine();
			String[] tokens = thisLine.split(",");
			//
			int i;
			while (true) {
				thisLine = csvReader.readLine();
				if (thisLine == null) {
					break;
				}

				tokens = thisLine.split(",");
				i = 0;
				boolean firstElementInRow = true;
				for (String token : tokens) {
					if (token.trim().equals("true")) {
						if (firstElementInRow) {
							firstElementInRow = false;
						} else {
							outputWriter.append(",");
						}
						outputWriter.append(Integer.toString(i));
					}
					i++;
				}
				outputWriter.append("\n");
				txcount++;
			}
			outputWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		DataConverter dc = new DataConverter();

	}
}
