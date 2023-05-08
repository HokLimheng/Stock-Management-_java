package JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Scanner;

public class Repositary {

    Scanner scanner = new Scanner(System.in);
    static String URL = "jdbc:postgresql://localhost:5432/Stock_Management";
    static String USERNAME = "postgres";
    static String PASSWORD = "lynheng1505";

//    public void LogIN(){
//        System.out.print("Input Username: ");
//        USERNAME = scanner.nextLine();
//        System.out.print("Input Password: ");
//        PASSWORD = scanner.nextLine();
//    }

    Connection connection = null;

    public Connection setConnection() {
//        1- load driver
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


        return connection;

    }
}