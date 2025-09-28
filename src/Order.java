import java.util.ArrayList;

public class Order {
    private String dateCreated;
    private String dateShipped;
    private String userName;
    private String orderStatus;
    private String shippingAddressLine1;
    private String shippingAddressLine2;
    private String shippingAddressCity;
    private String shippingAddressState;
    private String shippingAddressZip;
    private String shippingAddressCountry;
    private String billingAddressLine1;
    private String billingAddressLine2;
    private String billingAddressCity;
    private String billingAddressState;
    private String billingAddressZip;
    private String billingAddressCountry;
    private ArrayList<CartItem> items;
    private double orderPrice;

    public Order(Cart cart, String subscription) {
        this.items = cart.getItems();
        this.orderPrice = calculatePrice(subscription);
    }

    public void setShippingAddress(String line1, String line2, String city, String state, String zip, String country) {
        this.shippingAddressLine1 = line1;
        this.shippingAddressLine2 = line2;
        this.shippingAddressCity = city;
        this.shippingAddressState = state;
        this.shippingAddressZip = zip;
        this.shippingAddressCountry = country;
    }

    public void setBillingAddress(String line1, String line2, String city, String state, String zip, String country) {
        this.billingAddressLine1 = line1;
        this.billingAddressLine2 = line2;
        this.billingAddressCity = city;
        this.billingAddressState = state;
        this.billingAddressZip = zip;
        this.billingAddressCountry = country;
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
        System.out.println("Shipping Address: " + shippingAddressLine1 + ", " + shippingAddressLine2 + ", " + shippingAddressCity + ", " + shippingAddressState + ", " + shippingAddressZip + ", " + shippingAddressCountry);
        System.out.println("Billing Address: " + billingAddressLine1 + ", " + billingAddressLine2 + ", " + billingAddressCity + ", " + billingAddressState + ", " + billingAddressZip + ", " + billingAddressCountry);
        System.out.println("Order Price: $" + orderPrice);
    }

    public double calculatePrice(String subscription) {
        double totalPrice = 0.0;

        for (CartItem item : items) {
            totalPrice += item.getTotalPrice();
        }

        if (subscription == "gold") {
            totalPrice *= 0.85; // 15% discount for gold members (1 - 0.15 = 0.85)
        } else if (subscription == "platinum") {
            totalPrice *= 0.90; // 10% discount for platinum members (1 - 0.10 = 0.90)
        } else if (subscription == "silver") {
            totalPrice *= 0.95; // 5% discount for silver members (1 - 0.05 = 0.95)
        } 

        return totalPrice;
    }
}
