
import java.util.ArrayList;

public class User {
    private String name;
    private String subscription;
    private Cart cart;
    private ArrayList<Order> orders;
    private Address shippingAddress;
    private Address billingAddress;

    public User(String name, String subscription) {
        this.name = name;
        this.subscription = subscription;  // normal, gold, platinum, silver membership
        this.cart = new Cart();
        this.orders = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getSubscription() {
        return subscription;
    }

    public void setSubscription(String role) {
        this.subscription = role;
    }

    public void viewCart() {
        cart.viewCartDetails();
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

    public void addToCart(Book book, int quantity) {
        cart.addBook(book, quantity);
    }

    public void removeFromCart(Book book) {
        cart.removeBook(book);
    }

    public void viewOrders() {
        for (Order order : orders) {
            order.printOrderDetails();
        }
    }

    public void checkout() {
        Order order = new Order(cart, this.subscription);
        if (this.shippingAddress != null) {
            order.setShippingAddress(this.shippingAddress);
        }
        if (this.billingAddress != null) {
            order.setBillingAddress(this.billingAddress);
        }
        order.setOrderStatus("Order Placed");
        order.setDateCreated(java.time.LocalDate.now().toString());
        order.setUserName(this.name);
        orders.add(order);
    }
}


