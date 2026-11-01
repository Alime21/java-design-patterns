package FactoryMethod;
import java.util.ArrayList;
import java.util.List;

public abstract class Pizza {
    protected String name;
    protected String dough;
    protected String sauce;
    protected List<String> toppings = new ArrayList<>();

    public void prepare() {
        System.out.println("Hazırlanıyor: " + name);
        System.out.println("Hamur yoğruluyor: " + dough);
        System.out.println("Sos ekleniyor: " + sauce);
        System.out.println("Malzemeler: " + String.join(", ", toppings));
    }

    public void bake() { System.out.println("25 dakika 180 derecede pişiriliyor."); }
    public void cut()  { System.out.println("Pizzalar üçgen dilimleniyor."); }
    public void box()  { System.out.println("Resmi PizzaStore kutusuna konuluyor."); }

    public String getName() { return name; }
}
