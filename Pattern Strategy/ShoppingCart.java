public class ShoppingCart {
    private double totalAmount;
    private PaymentStrategy paymentStrategy;

    public void addAmount(double amount) {
        totalAmount += amount;
    }

    public void setPaymentStrategy(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }

    public void checkout() {
        if (paymentStrategy == null) {
            throw new IllegalStateException("Payment strategy not set");
        }
        paymentStrategy.pay(totalAmount);
        totalAmount = 0; // Reset total amount after payment    
    }

}
