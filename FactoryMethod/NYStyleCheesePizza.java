package FactoryMethod;

public class NYStyleCheesePizza extends Pizza {
    public NYStyleCheesePizza() {
        name = "New York Usulü Peynirli Pizza";
        dough = "İnce Çıtır Hamur";
        sauce = "Marinara Sos";
        toppings.add("Rendelenmiş Reggiano Peyniri");
    }
}
