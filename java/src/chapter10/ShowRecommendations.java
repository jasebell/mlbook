package chapter10;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class ShowRecommendations {
	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private Map<Integer, String> recommendations = new HashMap<Integer, String>();

	public ShowRecommendations(int customerid) {
		initMap(); // load the recommendations in to the hash map
		try {
			String rec = recommendations.get(new Integer(customerid));
			String output = generateRecommendation(rec);
			System.out.println("Customer: " + customerid + "\n" + output);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String generateRecommendation(String input) throws SQLException {
		StringBuilder sb = new StringBuilder();
		System.out.println("Working on " + input);
		String tempstring = input.substring(1,input.length()-1);
		String[] products = tempstring.split(",");
		System.out.println("products = " + products.length);
		Connection con = DriverManager.getConnection(
				"jdbc:mysql://localhost/mahoutratings", "root", "");
		PreparedStatement pstmt = con
				.prepareStatement("SELECT albumname, albumartist FROM stockitems WHERE id=?");
		ResultSet rs = null;
		for (int i = 0; i < products.length; i++) {
			String[] itemSplit = products[i].split(":");
			pstmt.setInt(1, Integer.parseInt(itemSplit[0]));
			rs = pstmt.executeQuery();
			if (rs.next()) {
				sb.append("Album: " + rs.getString("albumname") + " by "
						+ rs.getString("albumartist") + " rating: "
						+ itemSplit[1] + "\n");
			}
		}

		rs.close();
		pstmt.close();
		con.close();

		return sb.toString();
	}

	private void initMap() {
		try {
			BufferedReader in = new BufferedReader(new FileReader(
					"/Users/Jason/mahouttest.txt"));
			String str;
			while ((str = in.readLine()) != null) {
				String[] split = str.split("[ ]+");
				System.out.println("adding: " + split[0] + "=" + split[1]);
				recommendations.put(Integer.parseInt(split[0]), split[1]);
			}
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		if (args.length < 1) {
			System.out.println("Usage: ShowRecommendations [customerid]");
		} else {
			ShowRecommendations sr = new ShowRecommendations(Integer.parseInt(args[0]));
		}
	}
}
