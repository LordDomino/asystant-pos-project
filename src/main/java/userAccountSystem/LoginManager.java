package main.java.userAccountSystem;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;

import main.java.gui.windowFrames.WF_Dashboard;
import main.java.gui.windowFrames.WF_SuperAdminScreen;
import main.java.sql.DBReferences;
import main.java.sql.SQLConnector;

// import javax.swing.JFrame;

public class LoginManager {

    /**The access level index of the super administrator account. */
    public static final int ACCESS_LEVEL_SUPERADMIN = 1;

    /**The access level index of an administrator account */
    public static final int ACCESS_LEVEL_ADMIN      = 2;

    /**The access level index of a user account. */
    public static final int ACCESS_LEVEL_USER       = 3;

    /**The current attempted access level that the LoginManager will use to
     * facilitate login attempts based on the given access level.
     */
    private static int CURRENT_ACCESS_LEVEL_MODE = 0;

    /**The current target JFrame of the attempted access level that the
     * LoginManager will send in case of a successful login attempt of the
     * given access level.
     */
    private static JFrame CURRENT_ACCESS_LEVEL_TARGET_JFRAME;

    /**The set of illegal characters that are not allowed in account
     * usernames.
     */
    public static final char[] ILLEGAL_CHARS = {'%'};

    // public static WF_Dashboard dashboard = Main.app.;

    public static final boolean validateUsername(String username) throws SQLException {
        final String query = "SELECT * FROM " + DBReferences.TBL_USER_ACCOUNTS + " WHERE username = \"" + username + "\" LIMIT 1;";
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
                setCurrentAccessLevelModeConfig(ACCESS_LEVEL_ADMIN, new WF_Dashboard());
            } else if (db_access_lvl == ACCESS_LEVEL_USER) {  // User access
                setCurrentAccessLevelModeConfig(ACCESS_LEVEL_USER, new WF_Dashboard());
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
            setCurrentAccessLevelModeConfig(ACCESS_LEVEL_SUPERADMIN, new WF_SuperAdminScreen());
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
        final String query = "SELECT * FROM " + DBReferences.TBL_USER_ACCOUNTS + " WHERE username = \"" + username + "\" LIMIT 1;";
        final Statement statement = SQLConnector.connection.createStatement();
        final ResultSet result = statement.executeQuery(query);
        
        result.next(); // Read row

        System.out.println(result.getString("activated").toString());
        if (result.getString("activated").equals("1")) {
            return true;
        } else {
            return false;
        }
    }

    public static final boolean isAccountPasswordCorrect(String username, String password) throws SQLException {
        final String query = "SELECT * FROM " + DBReferences.TBL_USER_ACCOUNTS + " WHERE username = \"" + username + "\" LIMIT 1;";
        final Statement statement = SQLConnector.connection.createStatement();
        final ResultSet result = statement.executeQuery(query);

        result.next();

        final String SA_password = result.getString("password");

        final int db_access_lvl = result.getInt("access_level");

        if (SA_password.equals(password)) {
            // Access is permitted only when the username's password matches credentials in DB
            if (db_access_lvl == ACCESS_LEVEL_ADMIN) {  // Admin access
                setCurrentAccessLevelModeConfig(ACCESS_LEVEL_ADMIN, new WF_Dashboard());
            } else if (db_access_lvl == ACCESS_LEVEL_USER) {  // User access
                setCurrentAccessLevelModeConfig(ACCESS_LEVEL_USER, new WF_Dashboard());
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

    private static final void setCurrentAccessLevelModeConfig(int accessLevel, JFrame targetJFrame) {
        LoginManager.CURRENT_ACCESS_LEVEL_MODE = accessLevel;
        LoginManager.CURRENT_ACCESS_LEVEL_TARGET_JFRAME = targetJFrame;
    }
}
