package PatternDecorator;

public class Main {
    public static void main(String[] args) {
        // 1. sade espresso
        Beverage coffee1 = new Espresso();
        System.out.println(coffee1.getDescription() + " TL" + coffee1.getCost());

        // 2. filtre kahve + süt
        Beverage coffee2 = new FilterCoffee();
        coffee2 = new MilkDecorator(coffee2);
        System.out.println(coffee2.getDescription() + " TL" + coffee2.getCost());

        // 3. Espresso + Çift Süt + Karamel
        Beverage coffee3 = new Espresso();
        coffee3 = new MilkDecorator(coffee3);
        coffee3 = new MilkDecorator(coffee3);
        coffee3 = new CaramelDecorator(coffee3);
        System.out.println(coffee3.getDescription() + " TL" + coffee3.getCost());
        
    }

}
