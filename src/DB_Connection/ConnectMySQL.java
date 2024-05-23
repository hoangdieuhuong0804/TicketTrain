package DB_Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectMySQL {
	public static Connection getConnection() {
		Connection c = null;
		
		try {
			// Đăng ký MySQL Driver với DriverManager
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			
			// Tạo các thông số kết nối
			String url = "jdbc:mySQL://localhost:3306/tickets_train_hotel";
			String user = "root";
			String pass = ""; 
			
			// Tạo kết nối
			c = DriverManager.getConnection(url, user, pass);
			
			return c;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static void closeConnection(Connection c) {
		try {
			if(c!= null) {
				c.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
