public class EmailSubscriber implements Observer{
    private String email;

    public EmailSubscriber(String email) {
        this.email = email;
    }

    @Override
    public void update(String videoTitle) {
        System.out.println("Email gönderildi (" + email + "): Yeni video yayında -> " + videoTitle);
    }
}
