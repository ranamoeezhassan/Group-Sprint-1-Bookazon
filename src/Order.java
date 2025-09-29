import java.util.ArrayList;

public class Order {
    private final double GOLD_DISCOUNT = 0.85;
    private final double PLATINUM_DISCOUNT = 0.90;
    private final double SILVER_DISCOUNT = 0.95;
    private String dateCreated;
    private String dateShipped;
    private String userName;
    private String orderStatus;
    private ShippingAddress shippingAddress;
    private BillingAddress billingAddress;
    private ArrayList<CartItem> items;
    private double orderPrice;

    public Order(Cart cart, String subscription, ShippingAddress shippingAddress, BillingAddress billingAddress) {
        this.items = cart.getItems();
        this.orderPrice = calculatePrice(subscription);
        this.shippingAddress = shippingAddress;
        this.billingAddress = billingAddress;
    }


    public void setOrderStatus(String status) {
        this.orderStatus = status;
    }

    public void setDateCreated(String date) {
        this.dateCreated = date;
    }

    public void setDateShipped(String date) {
        this.dateShipped = date;
    }

    public void setUserName(String name) {
        this.userName = name;
    }

    public void printOrderDetails() {
        System.out.println("Order Details:");
        System.out.println("Date Created: " + dateCreated);
        System.out.println("Date Shipped: " + dateShipped);
        System.out.println("User Name: " + userName);
        System.out.println("Order Status: " + orderStatus);
        System.out.println("Shipping Address: " + shippingAddress.getFullAddress());
        System.out.println("Billing Address: " + billingAddress.getFullAddress());
        System.out.println("Order Price: $" + orderPrice);
    }

    public double calculatePrice(String subscription) {
        double totalPrice = 0.0;

        for (CartItem item : items) {
            totalPrice += item.getTotalPrice();
        }
      
        if (subscription.equals("gold")) {
            totalPrice *= GOLD_DISCOUNT; // 15% discount for prime members
        } else if (subscription.equals("platinum")) {
            totalPrice *= PLATINUM_DISCOUNT; // 10% discount for platinum members
        } else if (subscription.equals("silver")) {
            totalPrice *= SILVER_DISCOUNT; // 5% discount for silver members
        } 

        return totalPrice;
    }
}
