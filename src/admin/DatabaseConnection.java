package admin;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
	
	public static Connection connectDB() {
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			Connection connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/product_catalog_system", "root" , "adminpogi123#@!");
			
//			System.out.println("Connected");
			return connect;
			
		} catch(Exception e) {e.printStackTrace();}
		return null;
	}
}

//TableColumn<productData, String> availabilityColumn = new TableColumn<>("Availability");
//availabilityColumn.setCellValueFactory(new PropertyValueFactory<>("availability"));



