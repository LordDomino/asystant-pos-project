package main.java.sql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Queries {
    public static ResultSet getExistingProductsOfProductCode(String productCode) throws SQLException {
        String query = "SELECT * FROM " + DBReferences.TBL_STOCKS_INVENTORY + " WHERE product_code = \"" + productCode + "\";";
        Statement statement = SQLConnector.connection.createStatement();
        ResultSet result = statement.executeQuery(query);
        
        return result;
    }

    public static void insertNewProduct(String productCode, String name,
    String description, String category, String unitCost, String stockQuantity,
    String markupPrice, String unitPrice) throws SQLException {

        final String[] fieldNames = {
            "product_code",
            "name",
            "description",
            "category",
            "unit_cost",
            "stock_quantity",
            "markup_price",
            "unit_price"
        };
        
        String query = "INSERT INTO " + DBReferences.TBL_STOCKS_INVENTORY + " (";
        
        for (String field : fieldNames) {
            query = query + " `" + field + "`,";
        }
        
        query = query.substring(0, query.length()-1) + " ) VALUES (";
        query = query + " \"" + productCode      + "\", ";
        query = query + " \"" + name             + "\", ";
        query = query + " \"" + description      + "\", ";
        query = query + " \"" + category         + "\", ";
        query = query + " \"" + unitCost         + "\", ";
        query = query + " \"" + stockQuantity    + "\", ";
        query = query + " \"" + markupPrice      + "\", ";
        query = query + " \"" + unitPrice        + "\");";

        Statement statement = SQLConnector.connection.createStatement();
        System.out.println(query);
        statement.executeUpdate(query);
    }
}
