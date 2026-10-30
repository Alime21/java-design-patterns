package PatternFactory;

public class Ship implements Transport {
    @Override 
    public void deliver() {
        System.out.println("Kargo denizyolu ile konteyner gemisinde teslim ediliyor.");
    }


}
