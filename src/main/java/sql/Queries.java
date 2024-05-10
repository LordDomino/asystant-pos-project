package main.java.sql;

import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Queries {

    public static ResultSet getExistingProductsOfProductCode(String productCode) throws SQLException {
        String query = "SELECT * FROM " + DBReferences.TBL_STOCKS_INVENTORY + " WHERE product_code = \"" + productCode + "\";";
        Statement statement = SQLConnector.connection.createStatement();
        ResultSet result = statement.executeQuery(query);
        
        return result;
    }

    public static ResultSet getExistingCustomersByID(String customerId) throws SQLException {
        String query = "SELECT * FROM " + DBReferences.TBL_CUSTOMERS + " WHERE id = \"" + customerId + "\";";
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

    public static void updateProduct(int id, String productCode, String name, String description, String category,
    String unitCost, String stockQuantity, String markupPrice, String unitPrice) throws SQLException {

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

    public static void registerCustomer(String rfid_no, String student_no, String customer_name,
    String amount_deposited, String activated) throws SQLException {

        final String[] fieldNames = {
            "rfid_no",
            "student_no",
            "customer_name",
            "amount_deposited",
            "activated"
        };

        final String[] fieldValues = {
            rfid_no,
            student_no,
            customer_name,
            amount_deposited,
            activated
        };

        String query = "INSERT INTO " + DBReferences.TBL_CUSTOMERS + " (";

        List<String> fields = Arrays.asList(fieldNames);
        List<String> values = Arrays.asList(fieldValues);
        
        for (String field : fields) {
            query = query + " `" + field + "`,";
        }

        query = query.substring(0, query.length()-1) + " ) VALUES (";
        
        for (Object value : values) {
            if (value == null) {
                query = query + " NULL, ";
            } else {
                query = query + " \"" + value + "\", ";
            }
        }
        
        query = query.substring(0, query.length()-2) + " );";

        Statement statement = SQLConnector.connection.createStatement();
        System.out.println(query);
        statement.executeUpdate(query);
    }

    public static void updateCustomer(int id, String rfid_no, String student_no, String customer_name,
    String amount_deposited, String activated) throws SQLException {

        final String[] fieldNames = {
            "rfid_no",
            "student_no",
            "customer_name",
            "amount_deposited",
            "activated"
        };

        final String[] fieldValues = {
            rfid_no,
            student_no,
            customer_name,
            amount_deposited,
            activated
        };

        String query = "UPDATE " + DBReferences.TBL_CUSTOMERS + " SET ";

        List<String> fields = Arrays.asList(fieldNames);
        List<String> values = Arrays.asList(fieldValues);

        for (int i = 0; i < fields.size(); i++) {
            String columnName = fields.get(i);
            String value = values.get(i).toString();
            query = query + " " + columnName + " = \"" + value + "\",";
        }

        query = query.substring(0, query.length()-1);
        query = query + " WHERE id = \"" + id + "\";";

        Statement statement = SQLConnector.connection.createStatement();
        statement.executeUpdate(query);
    }

    public static ArrayList<String> getItemCategories() throws SQLException {
        String query = "SELECT category FROM " + DBReferences.TBL_STOCKS_INVENTORY
                     + " GROUP BY category;";

        Statement statement = SQLConnector.connection.createStatement(
            ResultSet.TYPE_SCROLL_INSENSITIVE,
            ResultSet.CONCUR_READ_ONLY
        );
        ResultSet result = statement.executeQuery(query);

        result.last();
        int rowCount = result.getRow();

        ArrayList<String> categories = new ArrayList<String>();

        result.beforeFirst();
        for (int i = 0; i < rowCount; i++) {
            result.next();
            String category = result.getString("category");
            if (!category.equals("Uncategorized")) {
                categories.add(category);
            }
        }
        Collections.sort(categories, String.CASE_INSENSITIVE_ORDER);
        categories.add(0, "Uncategorized");

        return categories;
    }
}