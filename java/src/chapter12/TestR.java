import java.util.Enumeration;

import org.rosuda.JRI.REXP;
import org.rosuda.JRI.RVector;
import org.rosuda.JRI.Rengine;

public class TestR {
	
	public static void main(String[] args) {
		Rengine rEngine = new Rengine(new String[] { "--vanilla" }, false, null);
        System.out.println("Waiting for R to create the engine.");
 
        if (!rEngine.waitForR()) {
            System.out.println("Cannot load R engine.");
            return;
        }
 
        rEngine.eval("data(iris)", false);
        REXP exp = rEngine.eval("iris");
        RVector vector = exp.asVector();
        System.out.println("Outputting names:");
        for (Enumeration e = vector.getNames().elements(); e.hasMoreElements();) {
            System.out.println(e.nextElement());
        }
	}
}
