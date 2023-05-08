package JDBC;
import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.CellStyle;
import org.nocrala.tools.texttablefmt.ShownBorders;
import org.nocrala.tools.texttablefmt.Table;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductManagement {
    static int totalRow = 10;
    Validations validations = new Validations();
    public List<Product> getAllProduct(Connection connection, int page) {
        List<Product> productList = new ArrayList<>();
//        String sqlStr = "SELECT p.id, p.name, p.price, p.quantity, c.name AS category, p.status FROM product p JOIN category c ON p.category_id = c.id";
        String sqlStr = "SELECT p.id, p.name, p.price, p.quantity, c.name AS category, p.status FROM product p JOIN category c ON p.category_id = c.id LIMIT ? OFFSET ?";
        try{
            PreparedStatement statement = connection.prepareStatement(sqlStr);
//            limit
            statement.setInt(1, totalRow);
//            offset
            statement.setInt(2,(page-1)*totalRow);
//            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()){
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                double price = resultSet.getDouble("price");
                int qty = resultSet.getInt("quantity");
                String categoryID = resultSet.getString("category");
                String status;
                if (qty<=0){
                    status = "Out of Stock";
                }else{
                    status = resultSet.getString("status");
                }

                // create a new Product object with the Category object
                productList.add(new Product(id, name, price, qty, categoryID, status));
            }

            statement.close();
            resultSet.close();

        } catch (SQLException e){
            System.out.println(e.getMessage());
        }

        return productList;
    }

    public void insertProduct(String name, double price, int qty, int categoryID, Connection connection) {

        String inStock = "In Stock";
        String outStock = " Out of Stock";
        String sqlStr = "insert into product(name, price, quantity, category_id, status) values (?, ?, ?, ?, ?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStr);

            preparedStatement.setString(1, name);
            preparedStatement.setDouble(2, price);
            preparedStatement.setInt(3, qty);
            preparedStatement.setInt(4, categoryID);
            boolean status = qty > 0;
            if (status){
                preparedStatement.setString(5, inStock);
            }else{
                preparedStatement.setString(5, outStock);
            }

            preparedStatement.executeUpdate();

            preparedStatement.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean updateProductName(int productID, String productName, Connection connection){
        String sqlStr = "update product set name = ? where id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStr);
            preparedStatement.setString(1, productName);
            preparedStatement.setInt(2, productID);
            int rowsUpdated = preparedStatement.executeUpdate();
            preparedStatement.close();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean updateProductPrice(int productID, double productPrice, Connection connection){
        String sqlStr = "update product set price = ? where id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStr);
            preparedStatement.setDouble(1, productPrice);
            preparedStatement.setInt(2, productID);
            int rowsUpdated = preparedStatement.executeUpdate();
            preparedStatement.close();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean updateProductQTY(int productID, int productQTY, Connection connection){
        String sqlStr = "update product set quantity = ? where id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStr);
            preparedStatement.setInt(1, productQTY);
            preparedStatement.setInt(2, productID);
            preparedStatement.executeUpdate();
            int rowsUpdated = preparedStatement.executeUpdate();
            preparedStatement.close();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean updateProductCategory(int productID, int productCategory, Connection connection){
        String sqlStr = "update product set category_id = ? where id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStr);
            preparedStatement.setInt(1, productCategory);
            preparedStatement.setInt(2, productID);
            preparedStatement.executeUpdate();
            int rowsUpdated = preparedStatement.executeUpdate();
            preparedStatement.close();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public void DeleteProduct(int id, Connection connection) {
        Table table_1_column = new Table(1, BorderStyle.UNICODE_ROUND_BOX_WIDE, ShownBorders.ALL);
        CellStyle cellStyle = new CellStyle(CellStyle.HorizontalAlign.center);

        String sqlStr = "delete from product where id = ?";
        String sqlQry = "select name from product where id = ?";
        boolean deleteSuccessfully = false;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStr);
            preparedStatement.setInt(1, id);
            PreparedStatement preparedStatementQry = connection.prepareStatement(sqlQry);
            preparedStatementQry.setInt(1, id);
            ResultSet resultSet = preparedStatementQry.executeQuery();

            if (resultSet.next()) {
                String name = resultSet.getString("name");
                if (name != null) {
                    System.out.println("\nDeleting Product: \"" + name + "\"...............");
                    preparedStatement.executeUpdate();
                    table_1_column.addCell("Product: " + name + " successfully deleted", cellStyle);
                    System.out.println(table_1_column.render());
                }
            } else if (!resultSet.next()) {
                table_1_column.addCell("       There is no Product id: " + id + "       ", cellStyle);
                System.out.println(table_1_column.render());
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<Product> searchProduct(String productName, Connection connection) {
        List<Product> productList = new ArrayList<>();

        String sqlStr = "SELECT p.id, p.name, p.price, p.quantity, c.name AS category, p.status FROM product p JOIN category c ON p.category_id = c.id WHERE LOWER(p.name) LIKE LOWER(?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStr);
            preparedStatement.setString(1,productName.toLowerCase());
            ResultSet resultSet = preparedStatement.executeQuery();

            int id = 0;
            String name = "";
            Double price = 0.0;
            int QTY = 0;
            String categoryID = "";
            String status = "";

            while (resultSet.next()) {
                id = resultSet.getInt("id");
                name = resultSet.getString("name");
                price = resultSet.getDouble("price");
                QTY = resultSet.getInt("quantity");
                categoryID = resultSet.getString("category");
                if(QTY<=0){
                    status = "Out of Stock";
                }else {
                    status = resultSet.getString("status");
                }
                productList.add(new Product(id, name, price, QTY, categoryID, status));
            }

            preparedStatement.close();
            resultSet.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return productList;
    }



}
