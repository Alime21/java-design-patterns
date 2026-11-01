package AbstractFactory;

public class Main {
    public static void main(String[] args) {
        PizzaStore nyStore = new NYPizzaStore();

        System.out.println("--- New York Peynirli Pizza Siparişi ---");
        Pizza pizza = nyStore.orderPizza("cheese");
        System.out.println("\nTamamlanan Sipariş: " + pizza.getName());
    }
}
