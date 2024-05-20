package main.java.utils;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLUtils {


    public static int getResultSetRowCount(ResultSet rs) throws SQLException {
        rs.last();
        final int n = rs.getRow();
        return n;
    }
}
