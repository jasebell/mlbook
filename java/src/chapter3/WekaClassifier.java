package chapter3;

class WekaClassifier {
	public static double classify(Object[] i)
		    throws Exception {

		    double p = Double.NaN;
		    p = WekaClassifier.N32ec89882(i);
		    return p;
		  }
		  static double N32ec89882(Object []i) {
		    double p = Double.NaN;
		    if (i[0] == null) {
		      p = 0;
		    } else if (i[0].equals("end_rack")) {
		      p = 0;
		    } else if (i[0].equals("cd_spec")) {
		    p = WekaClassifier.N473959d63(i);
		    } else if (i[0].equals("std_rack")) {
		    p = WekaClassifier.N63915224(i);
		    } 
		    return p;
		  }
		  static double N473959d63(Object []i) {
		    double p = Double.NaN;
		    if (i[2] == null) {
		      p = 0;
		    } else if (((Double) i[2]).doubleValue() <= 80.0) {
		      p = 0;
		    } else if (((Double) i[2]).doubleValue() > 80.0) {
		      p = 1;
		    } 
		    return p;
		  }
		  static double N63915224(Object []i) {
		    double p = Double.NaN;
		    if (i[3] == null) {
		      p = 0;
		    } else if (i[3].equals("TRUE")) {
		      p = 0;
		    } else if (i[3].equals("FALSE")) {
		      p = 1;
		    } 
		    return p;
		  }
}
