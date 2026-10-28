public class Main {
    public static void main(String[] args) {
        ShoppingCart cart = new ShoppingCart();
        cart.addAmount(1500.0);

        cart.setPaymentStrategy(new CreditCardPayment("1234567890123456","321"));
        cart.checkout();

        cart.addAmount(300.0);

        cart.setPaymentStrategy(new CryptoPayment("fkekjfcjekjmek"));
        cart.checkout();

        cart.addAmount(500.0);

        cart.setPaymentStrategy(new PayPalPayment("user@example.com"));
        cart.checkout();
    }
}
