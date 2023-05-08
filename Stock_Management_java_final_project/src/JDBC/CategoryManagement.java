package JDBC;

import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.CellStyle;
import org.nocrala.tools.texttablefmt.ShownBorders;
import org.nocrala.tools.texttablefmt.Table;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryManagement {

    Table table_1_column = new Table(1, BorderStyle.UNICODE_ROUND_BOX_WIDE, ShownBorders.ALL);
    CellStyle cellStyle = new CellStyle(CellStyle.HorizontalAlign.center);
    public List<Category> getAllCategory(Connection connection) {
        List<Category> categoryList = new ArrayList<>();
        String sqlStr = "SELECT * from category";

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlStr);

            int id = 0;
            String name = "";
            while (resultSet.next()) {
                id = resultSet.getInt("id");
                name = resultSet.getString("Name");
                categoryList.add(new Category(id, name));
            }

            statement.close();
            resultSet.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return categoryList;
    }

    public void insertCategory(String name, Connection connection) {

        String sqlStr = "insert into category(name) values (?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStr);

            preparedStatement.setString(1, name);

            preparedStatement.executeUpdate();

            preparedStatement.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean updateCategory(int id, String name, Connection connection) {

        String sqlStr = "update category set name = ? where id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStr);

            preparedStatement.setInt(2, id);
            preparedStatement.setString(1, name);
            int rowsUpdated = preparedStatement.executeUpdate();
            preparedStatement.close();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public void deleteCategory(int id, Connection connection) {

        String sqlStr = "delete from category where id = ?";
        String sqlQry = "select name from category where id = ?";
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
                    System.out.println("\nDeleting category: \"" + name + "\"...............");
                    preparedStatement.executeUpdate();
                    table_1_column.addCell("      Category: " + name + " successfully deleted      ", cellStyle);
                    System.out.println(table_1_column.render());
                }
            } else if (!resultSet.next()) {
                table_1_column.addCell("       There is no category id: " + id + "       ", cellStyle);
                System.out.println(table_1_column.render());
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<Category> searchCategory(String categoryName, Connection connection) {
        List<Category> categoryList = new ArrayList<>();

        String sqlStr = "select * from category where lower(name) = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStr);
            preparedStatement.setString(1, categoryName.toLowerCase());
            ResultSet resultSet = preparedStatement.executeQuery();

            int id = 0;
            String name = "";

            while (resultSet.next()) {
                id = resultSet.getInt("id");
                name = resultSet.getString("name");
                categoryList.add(new Category(id, name));
            }


            preparedStatement.close();
            resultSet.close()   ;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return categoryList;
    }

    
}
