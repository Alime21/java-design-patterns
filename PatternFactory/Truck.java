package PatternFactory;

public class Truck implements Transport {
    @Override 
    public void deliver() {
        System.out.println("Kargo karayolu ile kamyon kasasında teslim ediliyor.");
    }

}
