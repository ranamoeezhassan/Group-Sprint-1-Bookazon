
import java.util.ArrayList;

public class User {
    private static final String DEFAULT_ORDER_STATUS = "Order Placed";
    
    private String name;
    private Subscription subscription;
    private Cart cart;
    private ArrayList<Order> orders;
    private Address shippingAddress;
    private Address billingAddress;
  
    public User(String name, Subscription subscription) {
        this.name = name;
        this.subscription = subscription;  // normal, gold, platinum, silver membership
        this.cart = new Cart();
        this.orders = new ArrayList<>();
        this.shippingAddress = new ShippingAddress("", "", "", null, "", "");
        this.billingAddress = new BillingAddress("", "", "", null, "", "");
    }

    public String getName() {
        return name;
    }

    public Subscription getSubscription() {
        return subscription;
    }

    public void setSubscription(Subscription subscription) {
        this.subscription = subscription;
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


    public void addToCart(Media media, int quantity) {
        cart.addMedia(media, quantity);
    }

    public void removeFromCart(Media media) {
        cart.removeMedia(media);
    }

    public void viewOrders() {
        for (Order order : orders) {
            order.printOrderDetails();
        }
    }

    public void checkout() {
        Order order = new Order(this.cart, this.subscription, this.shippingAddress, this.billingAddress);
        order.setOrderStatus(DEFAULT_ORDER_STATUS);
        order.setDateCreated(java.time.LocalDate.now().toString());
        order.setUserName(this.name);
        orders.add(order);
    }
}


