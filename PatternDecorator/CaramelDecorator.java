package PatternDecorator;

public class CaramelDecorator extends BeverageDecorator {

    public CaramelDecorator(Beverage beverage) {
        super(beverage);
    }

    @Override 
    public String getDescription() {
        return beverage.getDescription() + " + Karamel";
    }

    @Override 
    public double getCost() {
        return beverage.getCost() + 15.0;
    }

}
