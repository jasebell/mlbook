package chapter6;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.mahout.common.Pair;
import org.apache.mahout.fpm.pfpgrowth.convertors.string.TopKStringPatterns;

public class DataReader {
	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static Map<Integer, Long> readFrequencyFile(Configuration configuration, String fileName) throws Exception {
		FileSystem fs = FileSystem.get(configuration);
		org.apache.hadoop.io.SequenceFile.Reader frequencyReader = new org.apache.hadoop.io.SequenceFile.Reader(fs, 
				new Path(fileName), configuration);
		Map<Integer, Long> frequency = new HashMap<Integer, Long>();
		Text key = new Text();
		LongWritable value = new LongWritable();
		while(frequencyReader.next(key, value)) {
			frequency.put(Integer.parseInt(key.toString()), value.get());
		}
		return frequency;
	}
	
	public static Map<Integer, String> loadItems() {
		Map<Integer, String> products = new HashMap<Integer, String>();
		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost/apriori","root","");
			PreparedStatement pstmt = con.prepareStatement("SELECT * FROM products");
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				products.put(new Integer(rs.getInt("id")), rs.getString("productname"));
			}
			rs.close();
			pstmt.close();
			con.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return products;
	}
	
	public static void processResults(Configuration configuration, Map<Integer, String> products) throws Exception {
		String frequencyfilename = "";
		String frequencypatternsfilename = "";
		double userMinimumSupport = 3;
		double userMinimumConfidence = 3;
		int transactioncount = 1361;
		
		Map<Integer, Long> frequency = readFrequencyFile(configuration, frequencyfilename);
		
		FileSystem fs = FileSystem.get(configuration);

		org.apache.hadoop.io.SequenceFile.Reader frequentPatternsReader = new org.apache.hadoop.io.SequenceFile.Reader(fs, 
				new Path(fileName), configuration);
		Text key = new Text();
		TopKStringPatterns value = new TopKStringPatterns();

		while(frequentPatternsReader.next(key, value)) {
			long firstFrequencyItem = -1;
			String firstItemId = null;
			List<Pair<List<String>, Long>> patterns = value.getPatterns();
			int i = 0;
			for(Pair<List<String>, Long> pair: patterns) {
				List<String> itemList = pair.getFirst();
				Long occurrence = pair.getSecond();
				if (i == 0) {
					firstFrequencyItem = occurrence;
					firstItemId = itemList.get(0);
				} else {
					double support = (double)occurrence / transactioncount;
					double confidence = (double)occurrence / firstFrequencyItem;
					if (support > userMinimumSupport
							&& confidence > userMinimumConfidence) {
						List<String> listWithoutFirstItem = new ArrayList<String>();
						for(String itemId: itemList) {
							if (!itemId.equals(firstItemId)) {
								listWithoutFirstItem.add(products.get(Integer.parseInt(itemId)));
							}
						}
						String firstItem = products.get(Integer.parseInt(firstItemId));
						listWithoutFirstItem.remove(firstItemId);
						System.out.printf(
							"%s => %s: support=%.3f, confidence=%.3f",
							listWithoutFirstItem,
							firstItem,
							support,
							confidence);

						if (itemList.size() == 2) {
							int otherItemId = -1;
							for(String itemId: itemList) {
								if (!itemId.equals(firstItemId)) {
									otherItemId = Integer.parseInt(itemId);
									break;
								}
							}
							long otherItemOccurrence = frequency.get(otherItemId);

							double lift = calcLift(occurrence, transactioncount, firstFrequencyItem, otherItemOccurrence);
							double conviction = calcConviction(confidence, transactioncount, otherItemOccurrence);
							System.out.printf(
								", lift=%.3f, conviction=%.3f",
								lift, conviction);
						}
						System.out.printf("\n");
					}
				}
				i++;
			}
		}
		frequentPatternsReader.close();
	}
	
	private static double calcLift(double occurrence, int transcationcount, long firstfreq, long otheritemoccurences) {
		return ((double)occurrence * transcationcount) / (firstfreq * otheritemoccurences);
	}
	
	private static double calcConviction(double confidence, int transactioncount, double otheroccurrences) {
		return (1.0 - otheroccurrences / transactioncount) / (1.0 - confidence);
	}
	
	public static void main(String[] args)  throws Exception{
		Configuration configuration = new Configuration();
		processResults(configuration,loadItems());
	}

}
