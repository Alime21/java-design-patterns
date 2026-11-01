package AbstractFactory;

public class CheesePizza extends Pizza {
    private PizzaIngredientFactory ingredientFactory;

    public CheesePizza(PizzaIngredientFactory ingredientFactory) {
        this.ingredientFactory = ingredientFactory;
    }

    @Override 
    public void prepare() {
        System.out.println("Hazırlanıyor: " + name);
        dough = ingredientFactory.createDough();
        sauce = ingredientFactory.createSauce();
        cheese = ingredientFactory.createCheese();
        System.out.println("Kullanılan Malzemeler: " + dough.getName() + ", " + sauce.getName() + ", " + cheese.getName());
    }

}
