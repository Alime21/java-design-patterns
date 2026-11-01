package FactoryMethod;

public class ChicagoPizzaStore extends PizzaStore {
    @Override 
    protected Pizza createPizza(String item) {
        if ("cheese".equalsIgnoreCase(item)) {
            return new ChicagoStyleCheesePizza();
        }
        return null;
    }
}
