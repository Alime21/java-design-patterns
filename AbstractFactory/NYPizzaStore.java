package AbstractFactory;

public class NYPizzaStore extends PizzaStore {
    @Override
    protected Pizza createPizza(String type) {
        Pizza pizza = null;
        PizzaIngredientFactory ingredientFactory = new NYPizzaIngredientFactory();

        if ("cheese".equalsIgnoreCase(type)) {
            pizza = new CheesePizza(ingredientFactory);
            pizza.setName("New York Tarzı Peynirli Pizza");
        }
        return pizza;
    }
}
