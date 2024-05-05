package main.java.userAccountSystem;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;

import main.java.Main;
import main.java.gui.frames.WF_Dashboard;
import main.java.gui.frames.WF_SuperAdminScreen;
import main.java.sql.DBReferences;
import main.java.sql.SQLConnector;

// import javax.swing.JFrame;

public class LoginManager {

    /**
     * The access level index of the super administrator account.
     */
    public static final int ACCESS_LEVEL_SUPERADMIN = 1;

    /**
     * The access level index of an administrator account
     */
    public static final int ACCESS_LEVEL_ADMIN      = 2;

    /**
     * The access level index of a user account.
     */
    public static final int ACCESS_LEVEL_USER       = 3;

    /**
     * The current attempted access level that the LoginManager will use to
     * facilitate login attempts based on the given access level.
     */
    private static int CURRENT_ACCESS_LEVEL_MODE = 0;

    /**
     * The current target JFrame of the attempted access level that the
     * LoginManager will send in case of a successful login attempt of the
     * given access level.
     */
    private static JFrame CURRENT_ACCESS_LEVEL_TARGET_JFRAME;

    /**
     * The set of illegal characters that are not allowed in account
     * usernames.
     */
    public static final char[] ILLEGAL_CHARS = {'%'};

    public static WF_Dashboard dashboard = Main.app.DASHBOARD_FRAME;

    public static final boolean validateUsername(String username) throws SQLException {
        final String query = "SELECT * FROM " + DBReferences.TBL_USER_ACCOUNTS
                           + " WHERE username = \"" + username + "\" LIMIT 1;";
        final Statement statement = SQLConnector.connection.createStatement();
        final ResultSet result = statement.executeQuery(query);
        
        if (!result.next()) {  // Read the row
            return false;  // No account of the given credentials have been found
        }

        // Reference to the "username" and "password" columns from the result
        final String db_username = result.getString("username");
        // final String db_password = result.getString("password");
        final int db_access_lvl = result.getInt("access_level");

        if (db_username.equals(username)) {
            if (db_access_lvl == ACCESS_LEVEL_ADMIN) {  // Admin access
                setCurrentAccessLevelModeConfig(ACCESS_LEVEL_ADMIN, dashboard);
            } else if (db_access_lvl == ACCESS_LEVEL_USER) {  // User access
                setCurrentAccessLevelModeConfig(ACCESS_LEVEL_USER, dashboard);
            }
            return true;
        } else {
            return false;
        }
    }

    public static final boolean validateSuperAdminUsername(String username) throws SQLException {
        // Query setup
        final String query = "SELECT * FROM " + DBReferences.TBL_USER_ACCOUNTS
                           + " WHERE access_level = " + ACCESS_LEVEL_SUPERADMIN
                           + " LIMIT 1";
        final Statement statement = SQLConnector.connection.createStatement();
        final ResultSet result = statement.executeQuery(query);  // Get the result from the query
        result.next();  // Read the first row

        // Reference to the "username" and "password" columns from the result
        final String SA_username = result.getString("username");
        // final String SA_password = result.getString("password");

        if (SA_username.equals(username)) {
            // Super admin access is permitted only when
            // the username and password matches credentials in DB

            // Finally create a user manager instance
            Main.app.SUPERADMIN_SCREEN = new WF_SuperAdminScreen();
            setCurrentAccessLevelModeConfig(ACCESS_LEVEL_SUPERADMIN, Main.app.SUPERADMIN_SCREEN);
            return true;
        } else {
            return false;
        }
    }
    
    public static final int getCurrentAccessLevelMode() {
        return CURRENT_ACCESS_LEVEL_MODE;
    }

    public static final JFrame getCurrentAccessLevelTargetJFrame() {
        return CURRENT_ACCESS_LEVEL_TARGET_JFRAME;
    }

    public static final boolean isAccountActivated(String username) throws SQLException {
        final String query = "SELECT * FROM " + DBReferences.TBL_USER_ACCOUNTS
                           + " WHERE username = \"" + username + "\" LIMIT 1;";
        final Statement statement = SQLConnector.connection.createStatement();
        final ResultSet result = statement.executeQuery(query);
        
        result.next(); // Read row

        if (result.getString("activated").equals("1")) {
            return true;
        } else {
            return false;
        }
    }

    public static final boolean isAccountPasswordCorrect(String username, String password) throws SQLException {
        final String query = "SELECT * FROM " + DBReferences.TBL_USER_ACCOUNTS
                           + " WHERE username = \"" + username + "\" LIMIT 1;";
        final Statement statement = SQLConnector.connection.createStatement();
        final ResultSet result = statement.executeQuery(query);

        result.next();

        final String SA_password = result.getString("password");

        final int db_access_lvl = result.getInt("access_level");

        if (SA_password.equals(password)) {
            // Access is permitted only when the username's password matches credentials in DB
            if (db_access_lvl == ACCESS_LEVEL_ADMIN) {  // Admin access
                setCurrentAccessLevelModeConfig(ACCESS_LEVEL_ADMIN, dashboard);
            } else if (db_access_lvl == ACCESS_LEVEL_USER) {  // User access
                setCurrentAccessLevelModeConfig(ACCESS_LEVEL_USER, dashboard);
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns {@code true} if the given username is legal and does not
     * contain illegal characters specified in
     * {@code LoginManager.ILLEGAL_CHARS}, otherwise returns
     * {@code false}.
     * @param username the string to check for illegal characters
     * @return whether or not the username is legal and does not contain
     * illegal characters
     */
    public static final boolean isUsernameLegal(String username) {
        for (char c : LoginManager.ILLEGAL_CHARS) {
            if (username.contains(""+c)) {
                // Illegal characters found
                return false;
            }
        }
        return true;
    }

    public static final void incrementIncorrectAttempts(String username) throws SQLException {
        String query = "SELECT * FROM " + DBReferences.TBL_USER_ACCOUNTS
                     + " WHERE username = \"" + username + "\" LIMIT 1;";
        final Statement statement = SQLConnector.connection.createStatement();
        final ResultSet result = statement.executeQuery(query);

        result.next();

        final int loginAttempts = result.getInt("login_attempts");

        if (loginAttempts + 1 >= 3) {
            // Lock the account
            query = "UPDATE " + DBReferences.TBL_USER_ACCOUNTS + " SET login_attempts = 0" // Locked accounts reset back to zero
                  + " WHERE username = \"" + username + "\";";
            statement.executeUpdate(query);
            lockAccount(username);
        } else {
            // Increment login_attempts
            query = "UPDATE " + DBReferences.TBL_USER_ACCOUNTS + " SET login_attempts = " + (loginAttempts + 1)
                  + " WHERE username = \"" + username + "\";";
            statement.executeUpdate(query);
        }
    }

    public static final void resetLoginAttempts(String username) throws SQLException {
        final Statement statement = SQLConnector.connection.createStatement();
        String query = "UPDATE " + DBReferences.TBL_USER_ACCOUNTS + " SET login_attempts = 0";
        query = query + " WHERE username = \"" + username + "\";";
        statement.executeUpdate(query);
    }

    public static final int getRemainingAttempts(String username) throws SQLException {
        final Statement statement = SQLConnector.connection.createStatement();
        String query = "SELECT * FROM " + DBReferences.TBL_USER_ACCOUNTS + " WHERE username = \"" + username + "\" LIMIT 1;";
        ResultSet result = statement.executeQuery(query);
        result.next();
        return (3 - result.getInt("login_attempts"));
    }

    private static final void lockAccount(String username) throws SQLException {
        final Statement statement = SQLConnector.connection.createStatement();
        String query = "UPDATE " + DBReferences.TBL_USER_ACCOUNTS + " SET activated = " + 0; // Locked accounts reset back to zero
        query = query + " WHERE username = \"" + username + "\";";
        statement.executeUpdate(query);
    }

    private static final void setCurrentAccessLevelModeConfig(int accessLevel, JFrame targetJFrame) {
        LoginManager.CURRENT_ACCESS_LEVEL_MODE = accessLevel;
        LoginManager.CURRENT_ACCESS_LEVEL_TARGET_JFRAME = targetJFrame;
    }
}
