package FactoryMethod;

public class Main {
    public static void main(String[] args) {
        PizzaStore nyStore = new NYPizzaStore();
        PizzaStore chicagoStore = new ChicagoPizzaStore();

        System.out.println("--- Ahmet NY Usulü Sipariş Ediyor ---");
        Pizza p1 = nyStore.orderPizza("cheese");

        System.out.println("\n--- Mehmet Chicago Usulü Sipariş Ediyor ---");
        Pizza p2 = chicagoStore.orderPizza("cheese");
    }
}
