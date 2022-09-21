package utills;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Dbconnect {
    private static String dbUrl = "jdbc:mysql://localhost:3306/test?user=root&password=" +
            "&useUnicode=true&characterEncoding=UTF8"; //Database address
    private static String dbDriver = "com.mysql.cj.jdbc.Driver"; //Database driven

    private static Connection conn = null;

    //Get connection
    public static Connection getConn() {
        if (null == conn) {
            try {
                Class.forName(dbDriver);
                conn = DriverManager.getConnection(dbUrl);
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }
        return conn;
    }

}


