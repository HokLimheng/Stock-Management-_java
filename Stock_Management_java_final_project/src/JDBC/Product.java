package JDBC;

public class Product {
    private int id;
    private String name;
    private double price;
    private int qty;
    private String categoryID;
    private String status;

    public Product(int id, String name, double price, int qty, String categoryID, String status) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.qty = qty;
        this.categoryID = categoryID;
        this.status = status;
    }

    public Product() {

    }

    public int getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public int getQty(){
        return qty;
    }

    public double getPrice(){
        return price;
    }

    public String getCategoryID(){
        return categoryID;
    }

    public String getStatus(){
        return status;
    }

}
