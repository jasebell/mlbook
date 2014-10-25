package chapter6;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ExtractProductNames {

	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public ExtractProductNames() {
		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost/apriori","root","");
			PreparedStatement pstmt = con.prepareStatement("INSERT INTO products (id, productname) VALUES (?,?)");
			
			BufferedReader csvfile = new BufferedReader(new FileReader("/Users/Jason/Downloads/rawdata.csv"));
			String productsLine = csvfile.readLine();
			String[] products = productsLine.split(",");

			for(int i = 0; i < products.length; i++) {
				pstmt.clearParameters();
				pstmt.setInt(1, i);
				pstmt.setString(2, products[i].trim());
				pstmt.execute();
				System.out.println("Added: " + products[i] + " into db");
			}
			pstmt.close();
			con.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		ExtractProductNames epn = new ExtractProductNames();
	}

}
