package AbstractFactory;

public abstract class Pizza {
    protected String name;
    protected Dough dough;
    protected Sauce sauce;
    protected Cheese cheese;

    public abstract void prepare();

    public void bake() {
        System.out.println("25 dakika 180 derecede pişiriliyor.");
    }

    public void cut() {
        System.out.println("Pizzalar dilimleniyor.");
    }

    public void box() {
        System.out.println("Resmi PizzaStore kutusuna yerleştiriliyor.");
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
