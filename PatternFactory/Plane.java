package PatternFactory;

public class Plane implements Transport {
    @Override 
    public void deliver() {
        System.out.println("Kargo havayolu ile kargo uçağında hızlıca teslim ediliyor.");
    }

}
