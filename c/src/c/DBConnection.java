package c;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    public static Connection getConnection() {
        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            String url="jdbc:mysql://localhost:3306/cx";
        	String username="root";
        	String password="";
        	
            con = DriverManager.getConnection(url, username, password);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return con;
    }
}