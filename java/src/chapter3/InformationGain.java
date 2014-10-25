package chapter3;

public class InformationGain {
	
	private double calcLog2(double value) {
		if(value <= 0.) {
			return 0.;
		}
		return Math.log10(value) / Math.log10(2.);
	}
	
	public double calcGain(double positive, double negative) {
		double sum = positive + negative;
		double gain = positive * calcLog2(positive/sum)/sum + negative * calcLog2(negative/sum)/sum;
		return -gain;
	}
	
	public static void main(String[] args) {
		InformationGain ig = new InformationGain();
		System.out.println(ig.calcGain(1,0));
	}
}
