package JDBC;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class StockAlertSystem {
    private static int alertThreshold = 10;
    public StockAlertSystem() {

        this.alertThreshold = alertThreshold;
    }
    public int getAlertThreshold() {
        return alertThreshold;
    }

    public List<Product> checkStockLevel(Connection connection) {
        List<Product> productList = new ArrayList<>();



        Product product = new Product();

//        String sqlStr = "SELECT p.id, p.name, p.price, p.quantity, c.name AS category, p.status FROM product p JOIN category c ON p.category_id = c.id";
        String sqlStr = "SELECT p.id, p.name, p.price, p.quantity, c.name AS category, p.status " +
                "FROM product p JOIN category c ON p.category_id = c.id " +
                "WHERE p.quantity < ?";

        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStr);
            preparedStatement.setInt(1, alertThreshold);
            ResultSet resultSet = preparedStatement.executeQuery();

            int id = 0;
            String name = "";
            Double price = 0.0;
            int QTY = 0;
            String categoryID = "";
            String status = "";

//
            while (resultSet.next()) {
                id = resultSet.getInt("id");
                name = resultSet.getString("name");
                price = resultSet.getDouble("price");
                QTY = resultSet.getInt("quantity");
                categoryID = resultSet.getString("category");

                if(QTY<=0){
                    status = "Out of Stock";
                } else{
                  status = resultSet.getString("status");
                }

                productList.add(new Product(id, name, price, QTY, categoryID, status));
            }

        } catch(Exception exception){
            System.out.println(exception.getMessage());
        }

        return productList;


//        if (product.getQty() < alertThreshold) {
//            System.out.println("ALERT: Stock level for " + product.getName() + " is below the alert threshold of " + alertThreshold + " units.");
//        }
    }



}
