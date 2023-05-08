package JDBC;
import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.CellStyle;
import org.nocrala.tools.texttablefmt.ShownBorders;
import org.nocrala.tools.texttablefmt.Table;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Account {
    Scanner scanner = new Scanner(System.in);

    Validations validations = new Validations();

    public List<Users> getAllUsers(Connection connection){
        List<Users> usersList = new ArrayList<>();
        String sqlStr = "SELECT * from users";

        try{
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlStr);
            while(resultSet.next()){
                int id = resultSet.getInt("id");
                String userName = resultSet.getString("Username");
                String PASSWORD = resultSet.getString("Password");
                usersList.add(new Users(id, userName, PASSWORD));
            }
            statement.close();
            resultSet.close();

        } catch (SQLException e){
            System.out.println(e.getMessage());
        }

        return usersList;
    }

    public boolean LogInManager(String username, String password, Connection connection){
        boolean logInSuccessfully = false;

        String sqlStr = "SELECT * FROM users WHERE username = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStr);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                String storedPassword = resultSet.getString("password");
                if(storedPassword.equals(password)){
                    logInSuccessfully = true;
                }
            }
            preparedStatement.close();;
            resultSet.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return logInSuccessfully;
    }

    public boolean createAccountManager(String username, String password,String confirmPassword, Connection connection){
        boolean accountCreated = false;

        String sqlStr = "insert into users(username, password) values (?, ?)";

        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStr);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            int rowAffected = preparedStatement.executeUpdate();

            if (password.equals(confirmPassword)){
                if(rowAffected>0){
                    accountCreated = true;
                }
            }
            preparedStatement.close();

        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return accountCreated;
    }

    public void deleteAccount(int id, Connection connection) {
        Table table_1_column = new Table(1, BorderStyle.UNICODE_ROUND_BOX_WIDE, ShownBorders.ALL);
        CellStyle cellStyle = new CellStyle(CellStyle.HorizontalAlign.center);

        String sqlStr = "delete from users where id = ?";
        String sqlQry = "select username from users where id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStr);
            preparedStatement.setInt(1, id);
            PreparedStatement preparedStatementQry = connection.prepareStatement(sqlQry);
            preparedStatementQry.setInt(1, id);
            ResultSet resultSet = preparedStatementQry.executeQuery();

            if (resultSet.next()) {
                String name = resultSet.getString("username");
                if (name != null) {
                    System.out.println("\nDeleting user: \"" + name + "\"...............");
                    preparedStatement.executeUpdate();
                    table_1_column.addCell("      User: " + name + " successfully deleted      ", cellStyle);
                    System.out.println(table_1_column.render());
                }
            } else if (!resultSet.next()) {
                table_1_column.addCell("       There is no user id: " + id + "       ", cellStyle);
                System.out.println(table_1_column.render());
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


}
