package PatternFactory;

public class Main {
    public static void main(String[] args) {
        Transport t1 = TransportFactory.createTransport("ROAD");
        t1.deliver();

        Transport t2 = TransportFactory.createTransport("SEA");
        t2.deliver();

        Transport t3 = TransportFactory.createTransport("AIR");
        t3.deliver();
    }
}
