public class MobileAppSubscriber implements Observer {
    private String username;

    public MobileAppSubscriber (String username) {
        this.username = username;
    }

    @Override 
    public void update(String videoTitle) {
        System.out.println("Mobil uygulama bildirimi (" + username + "): Yeni video yayında -> " + videoTitle);
    }

}
