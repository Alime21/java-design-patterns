package FactoryMethod;

public abstract class PizzaStore {
    // İşte "Factory Method" budur! Soyuttur, alt sınıflar karar verir:
    protected abstract Pizza createPizza(String item);

    // Sipariş akışı değişmez (Değişmeyen kısım):
    public Pizza orderPizza(String type) {
        Pizza pizza = createPizza(type);

        pizza.prepare();
        pizza.bake();
        pizza.cut();
        pizza.box();

        return pizza;
    }
}
