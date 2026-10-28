public class CreditCardPayment implements PaymentStrategy {
    private String cardNumber;
    private String cvv;

    public CreditCardPayment(String cardNumber, String cvv) {
        this.cardNumber = cardNumber;
        this.cvv = cvv;
    }

    @Override 
    public void pay (double amount) {
        System.out.println(amount + maskCard(cardNumber));
    }

    private String maskCard(String number) {
        return number.substring(number.length() - 4);
    }

}
