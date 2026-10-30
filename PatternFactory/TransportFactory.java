package PatternFactory;

public class TransportFactory {
    public static Transport createTransport(String type) {
        if (type == null || type.isEmpty()) {
            throw new IllegalArgumentException("Taşıma tipi boş olamaz!");
        }

        switch (type.toUpperCase()) {
            case "ROAD":
                return new Truck();
            case "SEA":
                return new Ship();
            case "AIR":
                return new Plane();
            default:
                throw new IllegalArgumentException("Bilinmeyen taşıma tipi: " + type);
        }
    }
}
