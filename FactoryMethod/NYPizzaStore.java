package FactoryMethod;

public class NYPizzaStore extends PizzaStore {
    @Override 
    protected Pizza createPizza(String item) {
            if ("cheese".equalsIgnoreCase(item)) {
                return new NYStyleCheesePizza();
            }
            return null;
    }
}
