package chapter10;

import java.io.IOException;

import org.apache.pig.EvalFunc;
import org.apache.pig.data.Tuple;

public class PigCalcMonthsBelow extends EvalFunc<Integer> {

	@Override
	public Integer exec(Tuple tuple) throws IOException {
		System.out.println("exec called.....");
		// assume we're reading in the whole line so customer
		// id will be at position tuple.get(0)
		
		double[] months = new double[12];
		for(int count = 1; count <= 12; count++) {
			months[count-1] = (Double)tuple.get(count);
		}
		
		double month13 = (Double)tuple.get(14);
		
		return monthsBelow(months, getMean(months));
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
}
