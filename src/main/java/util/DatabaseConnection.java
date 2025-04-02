package util;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=forTest;integratedSecurity=true;encrypt=true;trustServerCertificate=true;";
    public static Connection getConnection() throws SQLException{

        Connection connection = DriverManager.getConnection(URL);
        return connection;
    }
}

