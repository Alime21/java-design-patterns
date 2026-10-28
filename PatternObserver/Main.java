public class Main {
    public static void main(String[] args) {
        YoutubeChannel channel = new YoutubeChannel("yazılım");
        
        // Aboneleri oluşturuyoruz
        Observer ali = new EmailSubscriber("ali@example.com");
        Observer veli = new MobileAppSubscriber("veli33");
        Observer mehmet = new EmailSubscriber("mehmet@outlook.com");

        // Aboneleri kanala kaydediyoruz
        channel.registerObserver(ali);
        channel.registerObserver(veli);
        channel.registerObserver(mehmet);

        // 1. Video yüklendi -> 3 kişiye de bildirim gider
        channel.uploadVideo("Java ile Strategy Pattern Eğitimi");

        // mehmet bildirim almak istemiyor, abonelikten çıkıyor
        channel.removeObserver(mehmet);

        // 2. Video yüklendi -> 2 kişiye bildirim gider
        channel.uploadVideo("Observer Pattern Nedir ve Nasıl Kullanılır?");
    }
