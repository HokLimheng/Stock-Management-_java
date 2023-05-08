package JDBC;

import org.nocrala.tools.texttablefmt.BorderStyle;;
import org.nocrala.tools.texttablefmt.CellStyle;
import org.nocrala.tools.texttablefmt.ShownBorders;
import org.nocrala.tools.texttablefmt.Table;


import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.util.Scanner;

public class Controller {
    Scanner scanner = new Scanner(System.in);
    Table table_3_column = new Table(3, BorderStyle.DESIGN_TUBES_WIDE, ShownBorders.ALL);
    Table table_1_column = new Table(1, BorderStyle.DESIGN_TUBES_WIDE, ShownBorders.ALL);
    Table table_2_column = new Table(2, BorderStyle.DESIGN_TUBES_WIDE, ShownBorders.ALL);
    CellStyle cellStyle = new CellStyle(CellStyle.HorizontalAlign.center);
    Account account = new Account();
    Validations validations = new Validations();

    CategoryManagement categoryManagement = new CategoryManagement();

    static int totalRow = 10;
//    Account (Users)
    public void GetALLUsers(Connection connection){
        Table table_3_column = new Table(3, BorderStyle.UNICODE_ROUND_BOX_WIDE, ShownBorders.ALL);
//        Table tableHeadline = new Table(1, BorderStyle.UNICODE_BOX, ShownBorders.ALL);
//        tableHeadline.addCell("                  All USERS                  ", cellStyle);
//        System.out.println(tableHeadline.render());
        System.out.println("\n----------------------------------------------------------------------------\n");
        System.out.println("       | All USERS |          ");

        Account account = new Account();
        List<Users> usersList = account.getAllUsers(connection);
        table_3_column.addCell("ID", cellStyle);
        table_3_column.addCell("USERNAME", cellStyle);
        table_3_column.addCell("PASSWORD", cellStyle);


        for(int i=0; i<usersList.size(); i++){
            table_3_column.addCell(usersList.get(i).getID() + "");
            table_3_column.addCell(usersList.get(i).getUSERNAME());
            table_3_column.addCell(usersList.get(i).getPASSWORD() + "");
        }
        System.out.println(table_3_column.render());

        System.out.println("\n----------------------------------------------------------------------------");
    }

    public boolean LogIn(Connection connection){

        Table tableResult = new Table(1, BorderStyle.UNICODE_ROUND_BOX_WIDE, ShownBorders.ALL);
        Table tableHeadline = new Table(1, BorderStyle.UNICODE_ROUND_BOX_WIDE, ShownBorders.ALL);
        tableHeadline.addCell("            | Log In |           ", cellStyle);
        System.out.println(tableHeadline.render());
        System.out.print("Username: ");
        String username = validations.StringValidation();
        System.out.print("\nPassword: ");
        String password = validations.StringValidation();

        boolean loggedIn = account.LogInManager(username, password, connection);

        if(loggedIn){
            tableResult.addCell("     Log In Successfully    ", cellStyle);
            System.out.println(tableResult.render());
        } else{
            tableResult.addCell("     Incorrect Username or Password!    ", cellStyle);
            System.out.println(tableResult.render());
        }
        return loggedIn;
    }

    public void CreateAccount(Connection connection){
        Table tableResult = new Table(1, BorderStyle.UNICODE_ROUND_BOX_WIDE, ShownBorders.ALL);
        Table tableHeadline = new Table(1, BorderStyle.UNICODE_BOX, ShownBorders.ALL);
        tableHeadline.addCell("            | Create new account |            ", cellStyle);
        System.out.println(tableHeadline.render());
        System.out.print("\nUsername: ");
        String newUserName = validations.StringValidation();
        System.out.print("\nPassword: ");
        String newPassword = validations.StringValidation();
        System.out.print("\nConfirm Password: ");
        String confirmPassword = validations.StringValidation();

        if (newPassword == confirmPassword){
            if (account.createAccountManager(newUserName, newPassword, confirmPassword, connection)) {
                tableResult.addCell("  User: " + newUserName + " successfully created  ", cellStyle);
                System.out.println(tableResult.render());
            }
        } else{
            tableResult.addCell("     Fail to create Account, incorrect password     ", cellStyle);
            System.out.println(tableResult.render());
        }
    }
    public void DeleteAccount(Connection connection){
        Table tableHeadline = new Table(1, BorderStyle.UNICODE_ROUND_BOX_WIDE, ShownBorders.ALL);
        tableHeadline.addCell("             | Delete Account |            ", cellStyle);
        System.out.println(tableHeadline.render());
        System.out.print("\nInput User's ID you want to delete: ");
        int id = validations.IntValidation();
        account.deleteAccount(id, connection);
    }

//    Category Management
    public void GetAllCategory(Connection connection){
        Table table_2_column = new Table(2, BorderStyle.UNICODE_ROUND_BOX_WIDE, ShownBorders.ALL);
        CategoryManagement categoryManagement = new CategoryManagement();
        List<Category> categoryList = categoryManagement.getAllCategory(connection);



        table_2_column.addCell("ID", cellStyle);
        table_2_column.addCell("NAME", cellStyle);
        System.out.println("  |ALL CATEGORY| ");

        for(int i=0; i<categoryList.size(); i++){
            table_2_column.addCell(categoryList.get(i).getid() + "");
            table_2_column.addCell(categoryList.get(i).getName());
        }
        System.out.println(table_2_column.render());
    }

    public void InsertCategory(Connection connection){

        Table tableHeadline = new Table(1, BorderStyle.UNICODE_ROUND_BOX_WIDE, ShownBorders.ALL);
        Table tableResult = new Table(1, BorderStyle.UNICODE_ROUND_BOX_WIDE, ShownBorders.ALL);
        tableHeadline.addCell("             Insert Category            ", cellStyle);
        System.out.println(tableHeadline.render());
        System.out.println();
        System.out.print("-> Input Category: ");
        String name = validations.StringValidation();

        categoryManagement.insertCategory(name, connection);
        tableResult.addCell("        Insert Successfully       ", cellStyle);
        System.out.println(tableResult.render());

    }

    public void UpdateCategory(Connection connection){
        Table tableHeadline = new Table(1, BorderStyle.UNICODE_ROUND_BOX_WIDE, ShownBorders.ALL);
        Table tableResult = new Table(1, BorderStyle.UNICODE_ROUND_BOX_WIDE, ShownBorders.ALL);
        tableHeadline.addCell("             Update Category            ", cellStyle);
        System.out.println(tableHeadline.render());

        System.out.print("\n-> Input Category ID to update: ");
        int id = validations.IntValidation();
        System.out.print("\n-> Input new Category: ");
        String name = validations.StringValidation();
        boolean updateCategory = categoryManagement.updateCategory(id, name, connection);
        if(updateCategory) {
            tableResult.addCell("        Update Successfully       ", cellStyle);
            System.out.println(tableResult.render());
        }else {
            tableResult.addCell("        Update Fail       ", cellStyle);
            System.out.println(tableResult.render());
        }
    }

    public void DeleteCategory(Connection connection){
        Table tableHeadline = new Table(1, BorderStyle.UNICODE_ROUND_BOX_WIDE, ShownBorders.ALL);
        tableHeadline.addCell("             | Delete Category |            ", cellStyle);
        System.out.println(tableHeadline.render());
        System.out.print("\nInput Category's ID you want to delete: ");
        int id = validations.IntValidation();
        categoryManagement.deleteCategory(id, connection);
    }

    public void SearchCategory(Connection connection){
        Table tableHeadline = new Table(1, BorderStyle.UNICODE_ROUND_BOX_WIDE, ShownBorders.ALL);
        Table table_1_column = new Table(1, BorderStyle.UNICODE_ROUND_BOX_WIDE, ShownBorders.ALL);
        Table table_2_column = new Table(2, BorderStyle.UNICODE_ROUND_BOX_WIDE, ShownBorders.ALL);
        CategoryManagement categoryManagement = new CategoryManagement();

        tableHeadline.addCell("             Search Category            ", cellStyle);
        System.out.println(tableHeadline.render());
        System.out.print("\nInput Category's name to search: ");
        String name = validations.StringValidation();

        List<Category> categoryList = categoryManagement.searchCategory(name, connection);

        if (categoryList.size() == 0){
            table_1_column.addCell("             No Category found                ");
            System.out.println(table_1_column.render());
        } else{
            table_2_column.addCell("ID", cellStyle);
            table_2_column.addCell("Name", cellStyle);

            for (Category category : categoryList){
                table_2_column.addCell(category.getid()+"");
                table_2_column.addCell(category.getName());
            }
        }
        System.out.println(table_2_column.render());
    }

//    Product Management
    public void GetAllProduct(Connection connection){
        ProductManagement productManagement = new ProductManagement();

        int page = 1;
        while(true) {
            Table table_6_column = new Table(6, BorderStyle.UNICODE_ROUND_BOX_WIDE, ShownBorders.ALL);
            List<Product> productList = productManagement.getAllProduct(connection, page);
            System.out.println("\n----------------------------------------------------------------------------");
            System.out.println("                             | All PRODUCTS "+ page + " |                             ");

            table_6_column.addCell("ID", cellStyle);
            table_6_column.addCell("Name", cellStyle);
            table_6_column.addCell("Price$", cellStyle);
            table_6_column.addCell("Quantity", cellStyle);
            table_6_column.addCell("Category", cellStyle);
            table_6_column.addCell("Status", cellStyle);

            for (int i = 0; i < productList.size(); i++) {
                table_6_column.addCell(productList.get(i).getId() + "");
                table_6_column.addCell(productList.get(i).getName());
                table_6_column.addCell(productList.get(i).getPrice() + "$");
                table_6_column.addCell(productList.get(i).getQty() + "");
                table_6_column.addCell(productList.get(i).getCategoryID() + "");
                table_6_column.addCell(productList.get(i).getStatus() + "");

            }
            System.out.println(table_6_column.render());

            System.out.print("     |<-Previous(1)-----(2)Next->| Press Any key to exit: ");
            int opt = validations.IntValidation();
            switch (opt){
                case 1:
                    if(page>1){
                        page--;
                    }
                    break;
                case 2:
                    if(productList.size()==totalRow){
                        page = page + 1;
                    }else {
                        page=1;
                    }
                    break;
                default:
                    System.out.println("Back to main menu...");

            }
            if(opt>2 || opt <1){
                break;
            }
        }
    }

//    public void GetAllProduct(Connection connection) {
//        int page = 1;
//
//        while (true) {
//            Table table_6_column = new Table(6, BorderStyle.UNICODE_ROUND_BOX_WIDE, ShownBorders.ALL);
//            ProductManagement productManagement = new ProductManagement();
//            List<Product> productList = productManagement.getAllProduct(connection, page);
//
//            System.out.println("\n----------------------------------------------------------------------------");
//            System.out.println("                             | All PRODUCTS " + page + " |                             ");
//
//            table_6_column.addCell("ID", cellStyle);
//            table_6_column.addCell("Name", cellStyle);
//            table_6_column.addCell("Price$", cellStyle);
//            table_6_column.addCell("Quantity", cellStyle);
//            table_6_column.addCell("Category", cellStyle);
//            table_6_column.addCell("Status", cellStyle);
//
//            for (Product product : productList) {
//                table_6_column.addCell(String.valueOf(product.getId()));
//                table_6_column.addCell(product.getName());
//                table_6_column.addCell(product.getPrice() + "$");
//                table_6_column.addCell(String.valueOf(product.getQty()));
//                table_6_column.addCell(product.getCategoryID());
//                table_6_column.addCell(product.getStatus());
//            }
//
//            System.out.println(table_6_column.render());
//
//            System.out.print("<-Previous(1)-----(2)Next->  Press Any key to exit: ");
//            int opt = validations.IntValidation();
//            switch (opt) {
//                case 1:
//                    if (page > 1) {
//                        page--;
//                    }
//                    break;
//                case 2:
//                    if (productList.size() == totalRow) {
//                        page++;
//                    }
//                    break;
//                default:
//                    System.out.println("Back to main menu...");
//                    return;
//            }
//        }
//    }


    public void InsertProduct(Connection connection){

        ProductManagement productManagement = new ProductManagement();

        Table tableResult = new Table(1, BorderStyle.UNICODE_ROUND_BOX_WIDE, ShownBorders.ALL);
        Table tableHeadline = new Table(1, BorderStyle.UNICODE_ROUND_BOX_WIDE, ShownBorders.ALL);
        tableHeadline.addCell("             | Insert Product |            ", cellStyle);
        System.out.println(tableHeadline.render());

        System.out.print("\n-> Input product: ");
        String name = validations.StringValidation();
        System.out.print("\n-> Input price: ");
        double price = validations.DoubleValidation();
        System.out.print("\n-> Input quantity: ");
        int qty = validations.IntValidation();
        System.out.print("\n-> Input categoryID: ");
        int categoryID = validations.IntValidation();

        productManagement.insertProduct(name, price, qty, categoryID,connection);
        tableResult.addCell("        Insert Successfully       ", cellStyle);
        System.out.println(tableResult.render());

    }

    public void UpdateProductName(Connection connection){

        ProductManagement productManagement = new ProductManagement();

        Table tableResult = new Table(1, BorderStyle.UNICODE_ROUND_BOX_WIDE, ShownBorders.ALL);
        Table tableHeadline = new Table(1, BorderStyle.UNICODE_ROUND_BOX_WIDE, ShownBorders.ALL);
        tableHeadline.addCell("             | Update Product Name |            ", cellStyle);
        System.out.println(tableHeadline.render());

        System.out.print("\n-> Input Product ID to update: ");
        int id = validations.IntValidation();
        System.out.print("\n-> Input new Product Name: ");
        String name = validations.StringValidation();
        boolean updateName = productManagement.updateProductName(id, name, connection);
        if(updateName) {
            tableResult.addCell("        Update Successfully       ", cellStyle);
            System.out.println(tableResult.render());
        }else {
            tableResult.addCell("        Update Fail       ", cellStyle);
            System.out.println(tableResult.render());
        }
    }

    public void UpdateProductPrice(Connection connection){
        ProductManagement productManagement = new ProductManagement();

        Table tableResult = new Table(1, BorderStyle.UNICODE_ROUND_BOX_WIDE, ShownBorders.ALL);
        Table tableHeadline = new Table(1, BorderStyle.UNICODE_ROUND_BOX_WIDE, ShownBorders.ALL);
        tableHeadline.addCell("             | Update Product Price |            ", cellStyle);
        System.out.println(tableHeadline.render());

        System.out.print("\n-> Input Product ID to update: ");
        int id = validations.IntValidation();
        System.out.print("\n-> Input new Product price: ");
        Double price = validations.DoubleValidation();
        boolean updatePrice = productManagement.updateProductPrice(id, price, connection);
        if(updatePrice) {
            tableResult.addCell("        Update Product price Successfully       ", cellStyle);
            System.out.println(tableResult.render());
        }else {
            tableResult.addCell("        Update Fail       ", cellStyle);
            System.out.println(tableResult.render());
        }
    }

    public void UpdateProductQTY(Connection connection){
        ProductManagement productManagement = new ProductManagement();

        Table tableResult = new Table(1, BorderStyle.UNICODE_ROUND_BOX_WIDE, ShownBorders.ALL);
        Table tableHeadline = new Table(1, BorderStyle.UNICODE_ROUND_BOX_WIDE, ShownBorders.ALL);
        tableHeadline.addCell("             | Update Product Quantity |            ", cellStyle);
        System.out.println(tableHeadline.render());

        System.out.print("\n-> Input Product ID to update: ");
        int id = validations.IntValidation();
        System.out.print("\n-> Input new Product quantity: ");
        int qty = validations.IntValidation();
        boolean updateQTY =  productManagement.updateProductQTY(id, qty, connection);
        if(updateQTY) {
            tableResult.addCell("        Update Product Quantity Successfully       ", cellStyle);
            System.out.println(tableResult.render());
        }else {
            tableResult.addCell("        Update Fail       ", cellStyle);
            System.out.println(tableResult.render());
        }
    }

    public void UpdateProductCategory(Connection connection){
        ProductManagement productManagement = new ProductManagement();

        Table tableResult = new Table(1, BorderStyle.UNICODE_ROUND_BOX_WIDE, ShownBorders.ALL);
        Table tableHeadline = new Table(1, BorderStyle.UNICODE_ROUND_BOX_WIDE, ShownBorders.ALL);
        tableHeadline.addCell("             | Update Product Category |            ", cellStyle);
        System.out.println(tableHeadline.render());

        System.out.print("\n-> Input Product ID to update: ");
        int id = validations.IntValidation();
        System.out.print("\n-> Input new Product Category: ");
        int category = validations.IntValidation();
        boolean updateCategory = productManagement.updateProductCategory(id, category, connection);
        if(updateCategory) {
            tableResult.addCell("        Update Product Category Successfully       ", cellStyle);
            System.out.println(tableResult.render());
        }else {
            tableResult.addCell("        Update Fail       ", cellStyle);
            System.out.println(tableResult.render());
        }
    }

    public void DeleteProduct(Connection connection){
        ProductManagement productManagement = new ProductManagement();
        Table tableHeadline = new Table(1, BorderStyle.UNICODE_ROUND_BOX_WIDE, ShownBorders.ALL);
        tableHeadline.addCell("             | Delete Product |            ", cellStyle);
        System.out.println(tableHeadline.render());

        System.out.print("\nInput Product's ID you want to delete: ");
        int id = validations.IntValidation();
        productManagement.DeleteProduct(id, connection);
    }

    public void SearchProduct(Connection connection){
        Table table_6_column = new Table(6, BorderStyle.UNICODE_ROUND_BOX_WIDE, ShownBorders.ALL);
        Table tableHeadline = new Table(1, BorderStyle.UNICODE_ROUND_BOX_WIDE, ShownBorders.ALL);
        Table tableResult = new Table(1, BorderStyle.UNICODE_ROUND_BOX_WIDE, ShownBorders.ALL);
        ProductManagement productManagement = new ProductManagement();
        tableHeadline.addCell("            | Search Product |           ", cellStyle);
        System.out.println(tableHeadline.render());

        System.out.print("\nInput Product's name to search: ");
        String name = validations.StringValidation();

        List<Product> productList = productManagement.searchProduct(name, connection);

        if (productList.size() == 0){
            tableResult.addCell("             No product found                ");
            System.out.println(tableResult.render());
        } else{
            table_6_column.addCell("ID", cellStyle);
            table_6_column.addCell("Name", cellStyle);
            table_6_column.addCell("Price", cellStyle);
            table_6_column.addCell("Quantity", cellStyle);
            table_6_column.addCell("Category", cellStyle);
            table_6_column.addCell("Status", cellStyle);

            for (Product product : productList){
                table_6_column.addCell(product.getId()+"");
                table_6_column.addCell(product.getName());
                table_6_column.addCell(product.getPrice() + "");
                table_6_column.addCell(product.getQty() + "");
                table_6_column.addCell(product.getCategoryID() + "");
                table_6_column.addCell(product.getStatus() + "");

            }

            System.out.println(table_6_column.render());

        }
    }

    public void StockAlert(Connection connection){
        Table table_6_column = new Table(6, BorderStyle.UNICODE_ROUND_BOX_WIDE, ShownBorders.ALL);

        StockAlertSystem stockAlertSystem = new StockAlertSystem();

        System.out.println("\n------------------------| Stock Alert |------------------------");

        List<Product> productList = stockAlertSystem.checkStockLevel(connection);

        if (productList.size() == 0){
            table_1_column.addCell("             No product found                ");
            System.out.println(table_1_column.render());
        } else{
            table_6_column.addCell("ID", cellStyle);
            table_6_column.addCell("Name", cellStyle);
            table_6_column.addCell("Price", cellStyle);
            table_6_column.addCell("Quantity", cellStyle);
            table_6_column.addCell("Category", cellStyle);
            table_6_column.addCell("Status", cellStyle);

            for (Product product : productList){
                table_6_column.addCell(product.getId()+"");
                table_6_column.addCell(product.getName());
                table_6_column.addCell(product.getPrice() + "");
                table_6_column.addCell(product.getQty() + "");
                table_6_column.addCell(product.getCategoryID() + "");
                table_6_column.addCell(product.getStatus() + "");

            }

            System.out.println(table_6_column.render());
            System.out.println();
        }
    }

}
