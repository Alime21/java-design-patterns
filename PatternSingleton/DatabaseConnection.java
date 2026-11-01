public class DatabaseConnection {
    // 1. Tek örneği tutacak statik değişken (Lazy Initialization - İhtiyaç duyulunca oluşturma)
    private static DatabaseConnection instance;

    // 2. Dışarıdan "new DatabaseConnection()" yapılmasını engellemek için private constructor
    private DatabaseConnection() {
        System.out.println(">>> Veritabanı bağlantısı kuruldu (Ağır kaynak tüketimi gerçekleşti).");
    }

    // 3. Küresel erişim noktası (Thread-safe yaklaşım için synchronized eklenebilir)
    public static DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    public void executeQuery(String query) {
        System.out.println("Sorgu çalıştırılıyor: " + query);
    }
}
