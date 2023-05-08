import JDBC.Controller;
import JDBC.ProductManagement;
import JDBC.Repositary;
import JDBC.Validations;
import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.CellStyle;
import org.nocrala.tools.texttablefmt.ShownBorders;
import org.nocrala.tools.texttablefmt.Table;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Repositary repositary = new Repositary();

        Connection connection = repositary.setConnection();

        Controller controller = new Controller();

        Validations validations = new Validations();

        Table table_3_column = new Table(3, BorderStyle.DESIGN_TUBES_WIDE, ShownBorders.ALL);
        Table table_1_column = new Table(1, BorderStyle.DESIGN_TUBES_WIDE, ShownBorders.ALL);
        Table table_2_column = new Table(2, BorderStyle.DESIGN_TUBES_WIDE, ShownBorders.ALL);
        CellStyle cellStyle = new CellStyle(CellStyle.HorizontalAlign.center);
        Table table = new Table(1, BorderStyle.UNICODE_DOUBLE_BOX_WIDE, ShownBorders.ALL);


        int option;


        do{
            displayFirstPage();

            System.out.print("-> Choose option (1-5): ");

            option = validations.IntValidation();

            switch (option){
                case 1:
                {
                    controller.GetALLUsers(connection);
                    break;
                }
                case 2:
                {

                    if(controller.LogIn(connection)){
                        handleLogin();
                    }
                    break;
                }
                case 3:
                {
                    controller.CreateAccount(connection);
                    break;
                }
                case 4:
                {
                    controller.DeleteAccount(connection);
                    break;
                }
                case 5:
                {

                    String input = "";
                    do{
                        System.out.print("\nAre you sure, you want to exist the application? (Y/N): ");
                        input = validations.StringValidation();
                       switch (input.toLowerCase()){
                           case "y":
                           {
                               option = 6;
                               break;
                           }
                           case "n":
                           {
                               System.out.println("\nReturning to home page.............");
                               break;
                           }
                           default:
                               System.out.println("\nInvalid input, please try again");
                               break;
                       }
                    } while(!input.equalsIgnoreCase("y") && !input.equalsIgnoreCase("n"));
                    break;

                }


            }
        } while(option!=6);

        try {
            connection.close();
        } catch (SQLException e) {
            System.out.println();
        }

    }
    public static void displayFirstPage(){Table table_3_column = new Table(3, BorderStyle.DESIGN_TUBES_WIDE, ShownBorders.ALL);
        Table table_1_column = new Table(1, BorderStyle.DESIGN_TUBES_WIDE, ShownBorders.ALL);
        Table table_2_column = new Table(2, BorderStyle.DESIGN_TUBES_WIDE, ShownBorders.ALL);
        Table table = new Table(1, BorderStyle.UNICODE_DOUBLE_BOX_WIDE, ShownBorders.ALL);

        CellStyle cellStyle = new CellStyle(CellStyle.HorizontalAlign.center);
        System.out.println();
        table.addCell("--------------- Account Management ---------------", cellStyle);
        System.out.println(table.render());
        System.out.println("\n1. Get all User's Accounts\n");
        System.out.println("2. Log In\n");
        System.out.println("3. Create Account\n");
        System.out.println("4. Delete Account\n");
        System.out.println("5. Exist the application\n");
        System.out.println("----------------------------------------------------------------------------\n");

    }

    public static void handleLogin() {

        Validations validations = new Validations();

        int choice;

            do{
                Table table = new Table(1, BorderStyle.UNICODE_ROUND_BOX_WIDE, ShownBorders.ALL);
                System.out.println("----------------------------------------------------------------------------\n");
                table.addCell("     | Main Manu |     ");
                System.out.println(table.render());
                System.out.println();
                System.out.println("1. Category Management \n");
                System.out.println("2. Product Management \n");
                System.out.println("3. Filtering (Search)\n");
                System.out.println("4. Alert Stock\n");
                System.out.println("5. Log out\n");
                System.out.println("----------------------------------------------------------------------------\n");
                System.out.print("-> Choose option (1-5): ");
                choice = validations.IntValidation();

                switch(choice) {
                    case 1: {
                        displayCategoryManagement();
                        break;
                    }
                    case 2: {
                        displayProductManagement();
                        break;

                    }
                    case 3:{
                        displaySearch();
                        break;
                    }
                    case 4:
                    {
                        displayLStockAlert();
                        break;
                    }
                    case 5:
                    {
                        displayLogout();
                        break;
                    }
                    default:
                    {
                        System.out.println("\nInvalid choice please choose again!\n");
                    }
                }
            } while(choice!=5);


    }

    public static void displayCategoryManagement(){


        Repositary repositary = new Repositary();

        Connection connection = repositary.setConnection();

        Controller controller = new Controller();

        Validations validations = new Validations();

        int choice1;
        do{
            Table table = new Table(1, BorderStyle.UNICODE_ROUND_BOX_WIDE, ShownBorders.ALL);
            System.out.println("----------------------------------------------------------------------------\n");
            table.addCell("     | Category Management |     ");
            System.out.println(table.render());
            System.out.println();
            System.out.println("1. Get all Category\n");
            System.out.println("2. Insert Category\n" );
            System.out.println("3. Update Category\n");
            System.out.println("4. Delete Category\n");
            System.out.println("5. Back to Manu");

            System.out.print("\n-> Choose option (1-5): ");
            choice1 = validations.IntValidation();
            System.out.println("----------------------------------------------------------------------------\n");

            switch (choice1){
                case 1:
                {
                    controller.GetAllCategory(connection);
                    break;
                }
                case 2:
                {
                    controller.InsertCategory(connection);
                    break;
                }
                case 3:
                {
                    controller.UpdateCategory(connection);
                    break;
                }
                case 4:
                {
                    controller.DeleteCategory(connection);
                    break;
                }
                case 5:
                {
                    System.out.println("Back to Manu.............\n");
                    break;
                }
            }
        }while (choice1 != 5);
    }

    public static void displayUpdateProduct(){
        Repositary repositary = new Repositary();

        Connection connection = repositary.setConnection();

        Controller controller = new Controller();

        Validations validations = new Validations();
        int choice3;
        do{
            Table table = new Table(1, BorderStyle.UNICODE_ROUND_BOX_WIDE, ShownBorders.ALL);
            System.out.println("----------------------------------------------------------------------------\n");
            table.addCell("      Update Product      ");
            System.out.println(table.render());
            System.out.println("\n1. Update Products name\n");
            System.out.println("2. Update Products price\n");
            System.out.println("3. Update Products quantity\n");
            System.out.println("4. Update Products category\n");
            System.out.println("5. Back\n");

            System.out.print("-> Choose option (1-5): ");
            choice3 = validations.IntValidation();
            switch (choice3){
                case 1:
                {
                    controller.UpdateProductName(connection);
                    break;
                }
                case 2:
                {
                    controller.UpdateProductPrice(connection);
                    break;
                }
                case 3:
                {
                    controller.UpdateProductQTY(connection);
                    break;
                }
                case 4:
                {
                    controller.UpdateProductCategory(connection);
                    break;
                }
                case 5:
                {
                    System.out.println("\nBack.............\n");
                    break;
                }
            }
        } while(choice3 != 5);
    }

    public static void displayProductManagement(){
        Repositary repositary = new Repositary();

        Connection connection = repositary.setConnection();

        Controller controller = new Controller();

        Validations validations = new Validations();

        int choice2;
        do {
            Table table = new Table(1, BorderStyle.UNICODE_ROUND_BOX_WIDE, ShownBorders.ALL);
            System.out.println("----------------------------------------------------------------------------\n");
            table.addCell("     | Product Management |     ");
            System.out.println(table.render());
            System.out.println("\n1. Get all Products\n");
            System.out.println("2. Insert Products\n");
            System.out.println("3. Update Products\n");
            System.out.println("4. Delete Product\n");
            System.out.println("5. Back to Manu\n");

            System.out.print("-> Choose option (1-5): ");
            choice2 = validations.IntValidation();

            switch (choice2) {
                case 1: {
                    controller.GetAllProduct(connection);
                    break;
                }
                case 2: {
                    controller.InsertProduct(connection);
                    break;
                }
                case 3: {
                    displayUpdateProduct();
                    break;
                }
                case 4: {
                    controller.DeleteProduct(connection);
                    break;
                }
                case 5: {
                    System.out.println("\nBack to Manu.............\n");
                    break;
                }
            }
        } while (choice2 != 5);
    }

    public static void displaySearch(){
        Repositary repositary = new Repositary();

        Connection connection = repositary.setConnection();

        Controller controller = new Controller();

        Validations validations = new Validations();

        int choice3;
        do {
            Table table = new Table(1, BorderStyle.UNICODE_ROUND_BOX_WIDE, ShownBorders.ALL);
            System.out.println("----------------------------------------------------------------------------\n");
            table.addCell("     | Filter (Search) |      ");
            System.out.println(table.render());
            System.out.println("1. Search Category\n");
            System.out.println("2. Search Product\n");
            System.out.println("3. Back\n");
            System.out.print("-> Choose option (1-3): ");
            choice3 = validations.IntValidationForSearch();
            System.out.println("----------------------------------------------------------------------------\n");

            switch (choice3){
                case 1:
                {
                    controller.SearchCategory(connection);
                    break;
                }
                case 2:
                {
                    controller.SearchProduct(connection);
                    break;
                }
                case 3:
                {
                    System.out.println("\nBack..............");
                    break;
                }
            }
        } while(choice3 != 3);
    }

    public static void displayLStockAlert(){
        Repositary repositary = new Repositary();

        Connection connection = repositary.setConnection();

        Controller controller = new Controller();

        controller.StockAlert(connection);
    }

    public static void displayLogout(){
        System.out.println("\nLog out....................\n");
    }
}
