package sql;

import java.sql.Connection;
import java.sql.DriverManager;

public class SQLConnector {

    public static final String sqlDb = "asystant_pos";
    public static final String sqlTbl = "user_accounts";
    public static Connection connection;

    /**
     * Registers the class from the JAR library and establishes a port
     * connection to access MySQL.
     */
    public static void establishSQLConnection() {
        try {
            // Here we register the referenced external library, which
            // is the .jar file, to allow us to use its internal
            // classes for accessing the SQL database.
            Class.forName("com.mysql.cj.jdbc.Driver");
    
            // The connection with the SQL database server is then
            // established using the class provided by the jar library.
            SQLConnector.connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/" + SQLConnector.sqlDb, 
                "root",
                ""
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
