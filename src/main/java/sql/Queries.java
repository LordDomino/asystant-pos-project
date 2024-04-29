package main.java.sql;

import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

public class Queries {

    public static ResultSet getExistingProductsOfProductCode(String productCode) throws SQLException {
        String query = "SELECT * FROM " + DBReferences.TBL_STOCKS_INVENTORY + " WHERE product_code = \"" + productCode + "\";";
        Statement statement = SQLConnector.connection.createStatement();
        ResultSet result = statement.executeQuery(query);
        
        return result;
    }

    public static ResultSet getExistingCustomersOfrfidNo(String rfid_no) throws SQLException {
        String query = "SELECT * FROM " + DBReferences.TBL_CUSTOMERS + " WHERE rfid_no = \"" + rfid_no + "\";";
        Statement statement = SQLConnector.connection.createStatement();
        System.out.println(query);
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
        statement.executeUpdate(query);
    }

    public static void updateProduct(int id, String productCode, String name,
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

        final String[] fieldValues = {
            productCode,
            name,
            description,
            category,
            unitCost,
            stockQuantity,
            markupPrice,
            unitPrice
        };

        String query = "UPDATE " + DBReferences.TBL_STOCKS_INVENTORY + " SET ";

        List<String> fields = Arrays.asList(fieldNames);
        List<String> values = Arrays.asList(fieldValues);

        for (int i = 0; i < fields.size(); i++) {
            String columnName = fields.get(i);
            Object value = values.get(i);
            query = query + " " + columnName + " = \"" + value + "\",";
        }

        query = query.substring(0, query.length()-1);
        query = query + " WHERE id = \"" + id + "\";";

        Statement statement = SQLConnector.connection.createStatement();
        statement.executeUpdate(query);
    }


public static void registerCustomer(String rfid_no, String student_no,
String customer_name, String amount_deposited) throws SQLException {

    final String[] fieldNames = {
        "rfid_no",
        "student_no",
        "customer_name",
        "amount_deposited"
    };
    
    String query = "INSERT INTO " + DBReferences.TBL_CUSTOMERS + " (";
    
    for (String field : fieldNames) {
        query = query + " `" + field + "`,";
    }
    
    query = query.substring(0, query.length()-1) + " ) VALUES (";
    query = query + " \"" + rfid_no             + "\", ";
    query = query + " \"" + student_no             + "\", ";
    query = query + " \"" + customer_name      + "\", ";
    query = query + " \"" + amount_deposited        + "\");";

    Statement statement = SQLConnector.connection.createStatement();
    System.out.println(query);
    statement.executeUpdate(query);
}

public static void updateCustomer(int id, String rfid_no, String student_no,
String customer_name, String amount_deposited) throws SQLException {

    final String[] fieldNames = {
        "rfid_no",
        "student_no",
        "customer_name",
        "amount_deposited"
    };

    final String[] fieldValues = {
        rfid_no,
        student_no,
        customer_name,
        amount_deposited
    };

    String query = "UPDATE " + DBReferences.TBL_CUSTOMERS + " SET ";

    List<String> fields = Arrays.asList(fieldNames);
    List<String> values = Arrays.asList(fieldValues);

    for (int i = 0; i < fields.size(); i++) {
        String columnName = fields.get(i);
        Object value = values.get(i);
        query = query + " " + columnName + " = \"" + value + "\",";
    }

    query = query.substring(0, query.length()-1);
    query = query + " WHERE id = \"" + id + "\";";

    Statement statement = SQLConnector.connection.createStatement();
    System.out.println(query);
    statement.executeUpdate(query);
}


}
