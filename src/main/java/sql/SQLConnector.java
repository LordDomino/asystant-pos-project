package main.java.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLConnector {

    public static Connection connection;

    public static final String[] FIELDS_user_accounts = {"username", "password", "access_level", "activated"};

    /**
     * Registers the class from the JAR library and establishes a port
     * connection to access MySQL.
     */
    public static void establishSQLConnection() throws SQLException {
        try {
            // Here we register the referenced external library, which
            // is the .jar file, to allow us to use its internal
            // classes for accessing the SQL database.
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        // The connection with the SQL database server is then
        // established using the class provided by the jar library.
        SQLConnector.connection = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/" + DBReferences.DB, 
            "root",
            ""
        );
    }
}
