package chapter10;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PopulateStockTable {
	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws SQLException {
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost/mahoutratings", "root", "");
		PreparedStatement pstmt = con.prepareStatement("INSERT INTO stockitems VALUES (null, ?,?)");
		for (int i = 1; i <= 2000; i++) {
			pstmt.setString(1, "AlbumArtist_" + i);
			pstmt.setString(2, "AlbumName_" + i);
			pstmt.execute();
		}
		pstmt.close();
		con.close();
	}

}
