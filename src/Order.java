import java.util.ArrayList;

public class Order {
    private String dateCreated;
    private String dateShipped;
    private String userName;
    private String orderStatus;
    private Address shippingAddress;
    private Address billingAddress;
    private ArrayList<CartItem> items;
    private double orderPrice;

    public Order(Cart cart, String subscription) {
        this.items = cart.getItems();
        this.orderPrice = calculatePrice(subscription);
    }

    public void setShippingAddress(Address address) {
        this.shippingAddress = address;
    }

    public Address getShippingAddress() {
        return shippingAddress;
    }

    public void setBillingAddress(Address address) {
        this.billingAddress = address;
    }

    public Address getBillingAddress() {
        return billingAddress;
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
        if (shippingAddress != null) {
            System.out.println("Shipping Address: " + shippingAddress.getLine1() + ", " + shippingAddress.getLine2() + ", " + shippingAddress.getCity() + ", " + shippingAddress.getState() + ", " + shippingAddress.getZip() + ", " + shippingAddress.getCountry());
        } else {
            System.out.println("Shipping Address: N/A");
        }
        if (billingAddress != null) {
            System.out.println("Billing Address: " + billingAddress.getLine1() + ", " + billingAddress.getLine2() + ", " + billingAddress.getCity() + ", " + billingAddress.getState() + ", " + billingAddress.getZip() + ", " + billingAddress.getCountry());
        } else {
            System.out.println("Billing Address: N/A");
        }
        System.out.println("Order Price: $" + orderPrice);
    }

    public double calculatePrice(String subscription) {
        double totalPrice = 0.0;

        for (CartItem item : items) {
            totalPrice += item.getTotalPrice();
        }

        if ("gold".equals(subscription)) {
            totalPrice *= 0.15; // 15% discount for prime members
        } else if ("platinum".equals(subscription)) {
            totalPrice *= 0.10; // 10% discount for platinum members
        } else if ("silver".equals(subscription)) {
            totalPrice *= 0.05; // 5% discount for silver members
        }

        return totalPrice;
    }
}
