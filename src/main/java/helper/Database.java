package helper;
import java.sql.*;

public class Database {
    public Connection getDb() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/sonoo","root","root");
    }
}
