package chapter10;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CoreMethods {

	public CoreMethods() {
		long start = System.currentTimeMillis();
		try {
			BufferedReader in = new BufferedReader(new FileReader(
					"/Users/Jason/tmpdata.csv"));
			String str;
			while ((str = in.readLine()) != null) {
				String[] split = str.split(",");
				double[] values = new double[12];
				// 0 = userid
				// 1 - 13 = months sales. Jan - Jan, Mar - Mar etc
				for (int i = 1; i <= 12; i++) {
					values[i-1] = Double.parseDouble(split[i]);
				}
				
				double mean = getMean(values);
				System.out.println("User id: " + split[0]);
				System.out.println("\tMean: " + mean);
				System.out.println("\tMonth 13 Sales Drop = " + calcSalesDrop(Double.parseDouble(split[13]), mean));
				System.out.println("\tMonths 40% below mean: " + monthsBelow(values, mean));
			}
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		long stop = System.currentTimeMillis();
		System.out.println("Took " + (stop - start) + " milliseconds.");
	}
	
	private int calcSalesDrop(double lastMonth, double mean) {
		return (int)(lastMonth - mean) < 0 ? 0 : (int)(lastMonth - mean); 
	}
	
	private int monthsBelow(double[] data, double mean) {
		int count = 0;
		for(double a : data) {
			if((a < (mean * 0.40))) count++;
		}
		return count;
	}
	
	private double getMean(double[] data) {
		double sum = 0.0;
		for(double a : data) {
			sum += a;
		}
		return sum/data.length;
	}
	
	public static void main(String[] args) {
		CoreMethods coreMethods = new CoreMethods();
	}
}
