
import java.util.ArrayList;

public class User {
    private static final String DEFAULT_ORDER_STATUS = "Order Placed";
    
    private String name;
    private String subscription;
    private Cart cart;
    private ArrayList<Order> orders;
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

    public void addToCart(Book book, int quantity) {
        cart.addItem(new CartItem(book.getTitle(), book.getPrice(), quantity));
    }

    public void removeFromCart(Book book) {
        for (CartItem item : cart.getItems()) {
            if (item.getName().equals(book.getTitle())) {
                cart.getItems().remove(item);
                break;
            }
        }
    }

    public void viewOrders() {
        for (Order order : orders) {
            order.printOrderDetails();
        }
    }

    public void checkout() {
        Order order = new Order(this.cart, this.subscription);
        order.setShippingAddress(this.shippingAddressLine1, this.shippingAddressLine2, 
                                this.shippingAddressCity, this.shippingAddressState, 
                                this.shippingAddressZip, this.shippingAddressCountry);
        order.setBillingAddress(this.billingAddressLine1, this.billingAddressLine2, 
                               this.billingAddressCity, this.billingAddressState, 
                               this.billingAddressZip, this.billingAddressCountry);
        order.setOrderStatus(DEFAULT_ORDER_STATUS);
        order.setDateCreated(java.time.LocalDate.now().toString());
        order.setUserName(this.name);
        orders.add(order);
    }
}


