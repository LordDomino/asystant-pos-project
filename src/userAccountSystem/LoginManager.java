package userAccountSystem;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;

import gui.windowFrames.WF_SuperAdminPanel;
import sql.SQLConnector;

public class LoginManager {

    /**The current attempted access level that the LoginManager will use to
     * facilitate login attempts based on the given access level.
     */
    private static int CURRENT_ACCESS_LEVEL_MODE = 0;

    /**The current target JFrame of the attempted access level that the
     * LoginManager will send in case of a successful login attempt of the
     * given access level.
     */
    private static JFrame CURRENT_ACCESS_LEVEL_TARGET_JFRAME;

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
            setCurrentAccessLevelModeConfig(1, new WF_SuperAdminPanel());
            return true;
        } else {
            return false;
        }
    }

    private static void setCurrentAccessLevelModeConfig(int accessLevel, JFrame targetJFrame) {
        LoginManager.CURRENT_ACCESS_LEVEL_MODE = accessLevel;
        LoginManager.CURRENT_ACCESS_LEVEL_TARGET_JFRAME = targetJFrame;
    }

    public static int getCurrentAccessLevelMode() {
        return CURRENT_ACCESS_LEVEL_MODE;
    }

    public static JFrame getCurrentAccessLevelTargetJFrame() {
        return CURRENT_ACCESS_LEVEL_TARGET_JFRAME;
    }
}
