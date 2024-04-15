package userAccountSystem;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import sql.SQLConnector;

public class LoginManager {
    public static boolean attemptSuperAdminLogin(String username, String password) throws SQLException {
        // Query setup
        final String query = "SELECT * FROM " + SQLConnector.sqlTbl + " WHERE access_level = 1 LIMIT 1";
        final Statement statement = SQLConnector.connection.createStatement();
        final ResultSet result = statement.executeQuery(query);  // Get the result from the query
        result.next();  // Read the first row

        // Reference to the "username" and "password" from the result
        final String SA_username = result.getString("username");
        final String SA_password = result.getString("password");

        if (SA_username.equals(username) && SA_password.equals(password)) {
            // Super admin access is permitted only when
            // the username and password matches credentials in DB
            return true;
        } else {
            return false;
        }
    }
}
