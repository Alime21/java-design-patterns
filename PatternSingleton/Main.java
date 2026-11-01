public class Main {
    public static void main(String[] args) {

        DatabaseConnection conneection1 = DatabaseConnection.getInstance();
        conneection1.executeQuery("SELECT * FROM users");

        DatabaseConnection conneection2 = DatabaseConnection.getInstance();
        conneection2.executeQuery("SELECT * FROM products");
    }

}
