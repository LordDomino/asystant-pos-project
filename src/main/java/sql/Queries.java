package main.java.sql;

import java.util.List;

import main.java.gui.panels.dvPurchaseView.CheckoutRowData;
import main.java.gui.panels.dvPurchaseView.CheckoutTableData;
import main.java.userAccountSystem.LoginManager;
import main.java.utils.exceptions.NonExistentCustomer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.time.LocalDate;
import java.time.LocalTime;

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
            if (values.get(i) == null) {
                query = query + " " + columnName + " = NULL,";
            } else {
                String value = values.get(i).toString();
                query = query + " " + columnName + " = \"" + value + "\",";
            }    
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

    public static void createOrder(long rfid_no, CheckoutTableData data) throws SQLException, NonExistentCustomer {
        
        final String[] fieldNames = {
            "sales_id",
            "product_code",
            "item_name",
            "quantity",
            "total_price",
            "status",
            "created_by",
            "customer_id"
        };

        ResultSet customerInfo = getCustomerBasedOnRFID(rfid_no);
        customerInfo.next();

        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();

        // All items of the same order has the same sales ID
        final String salesId = date.toString().replace("-", "")
            + "-"
            + time.toString().replace(":", "").substring(0, 6);

        for (CheckoutRowData<?> row : data.getRows()) {   
            final String[] fieldValues = {
                salesId,
                row.getValueAt(0).toString(),
                row.getValueAt(1).toString(),
                row.getValueAt(2).toString(),
                row.getValueAt(3).toString().substring(3, row.getValueAt(3).toString().length()).toString(),
                "1",
                LoginManager.getCurrentUsername(),
                customerInfo.getString("id")
            };

            String query = "INSERT INTO " + DBReferences.TBL_SALES + " ( ";

            List<String> fields = Arrays.asList(fieldNames);
            List<String> values = Arrays.asList(fieldValues);
            
            for (String field : fields) {
                query = query + " `" + field + "`,";
            }

            query = query.substring(0, query.length()-1) + " ) VALUES (";

            for (String value : values) {
                if (value == null) {
                    query = query + " NULL, ";
                } else {
                query = query + " \"" + value + "\", ";
                }
            }

            query = query.substring(0, query.length()-2) + " );";

            System.out.println(query);
            Statement statement = SQLConnector.connection.createStatement();
            statement.executeUpdate(query);
        }
    }

    public static void processOrder() throws SQLException {
        String query = "CALL spRecomputeInventory();";
        Statement statement = SQLConnector.connection.createStatement();
        statement.executeUpdate(query);

        query = "CALL spProcessOrderInventory();";
        statement.executeUpdate(query);
    }

    public static ResultSet getCustomerBasedOnRFID(long rfid_no) throws SQLException, NonExistentCustomer {
        String query = "SELECT * FROM " + DBReferences.TBL_CUSTOMERS +
            " WHERE rfid_no = " + rfid_no + ";";   
     
        Statement statement = SQLConnector.connection.createStatement(
            ResultSet.TYPE_SCROLL_INSENSITIVE,
            ResultSet.CONCUR_READ_ONLY
        );

        ResultSet result = statement.executeQuery(query);
        result.last();
        int size = result.getRow();
        if (size == 0) {
            throw new NonExistentCustomer(rfid_no);
        } else {
            return statement.executeQuery(query);
        }

    }
}

