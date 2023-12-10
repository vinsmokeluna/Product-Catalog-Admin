package admin;

import java.sql.*;
public class database {

	public static void main(String[] args) throws Exception {
		
		String url = "jdbc:mysql://localhost:3306/product_catalog_system";
		String username = "root";
		String password = "adminpogi123#@!";
		try {
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			Connection conn = DriverManager.getConnection(url,username,password);
		
		
		Statement statement = conn.createStatement();
		ResultSet rs = statement.executeQuery("SELECT * from stocks");
		
		while(rs.next()) {
			System.out.println(rs.getInt(1) + " " + rs.getString(2) + " " + rs.getString(3) + " " + rs.getInt(4) + " " + rs.getInt(5) + " " + rs.getInt(6) + " " + rs.getString(7) + " ");
		}
		conn.close();
		
		} catch(Exception e) {
			System.out.print(e);
			
		
		
		}
		
		
		}

}
