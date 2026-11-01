package FactoryMethod;

public class ChicagoStyleCheesePizza extends Pizza {
    public ChicagoStyleCheesePizza() {
        name = "Chicago Usulü Derin Tepsi Peynirli Pizza";
        dough = "Ekstra Kalın Hamur";
        sauce = "Erik Domates Sosu";
        toppings.add("Bol Mozzarella Peyniri");
    }

    @Override
    public void cut() { // Chicago kare dilimler!
        System.out.println("Pizzalar kare şeklinde dilimleniyor.");
    }

}
