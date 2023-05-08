package JDBC;

public class Users {
    private int id;
    private String USERNAME;
    private String PASSWORD;

    public Users (int id, String USERNAME, String PASSWORD){
        this.id = id;
        this.USERNAME = USERNAME;
        this.PASSWORD = PASSWORD;
    }

    public int getID(){
        return id;
    }

    public String getUSERNAME(){
        return USERNAME;
    }

    public String getPASSWORD(){
        return PASSWORD;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", Username='" + USERNAME + '\'' +
                ", Password=" + PASSWORD +
                '}';
    }
}
