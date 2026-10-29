package PatternDecorator;

public class MilkDecorator extends BeverageDecorator {
    
    public MilkDecorator(Beverage beverage) {
        super(beverage);
    }

    @Override 
    public String getDescription() {
        return beverage.getDescription() + " + Süt";
    }

    @Override
    public double getCost() {
        return beverage.getCost() + 10.0;
    }

}
