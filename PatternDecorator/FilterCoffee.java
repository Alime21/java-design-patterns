package PatternDecorator;

public class FilterCoffee implements Beverage {
    @Override
    public String getDescription() {
        return "Filter Coffee";
    }
    
    @Override 
    public double getCost() {
        return 40.0;
    }
}
