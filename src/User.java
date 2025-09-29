
import java.util.ArrayList;

public class User {
    private static final String DEFAULT_ORDER_STATUS = "Order Placed";
    
    private String name;
    private Subscription subscription;
    private Cart cart;
    private ArrayList<Order> orders;
    private ShippingAddress shippingAddress;
    private BillingAddress billingAddress;
    

    public User(String name, Subscription subscription) {
        this.name = name;
        this.subscription = subscription;  // normal, gold, platinum, silver membership
        this.cart = new Cart();
        this.orders = new ArrayList<>();
        this.shippingAddress = new ShippingAddress("", "", "", State.Alabama, "", "");
        this.billingAddress = new BillingAddress("", "", "", State.Arizona, "", "");
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

    public void setShippingAddress(String line1, String line2, String city, State state, String zip, String country){
        shippingAddress.setLine1(line1);
        shippingAddress.setLine2(line2);
        shippingAddress.setCity(city);
        shippingAddress.setState(state);
        shippingAddress.setZip(zip);
        shippingAddress.setCountry(country);
    }

    public void setBillingAddress(String line1, String line2, String city, State state, String zip, String country){
        billingAddress.setLine1(line1);
        billingAddress.setLine2(line2);
        billingAddress.setCity(city);
        billingAddress.setState(state);
        billingAddress.setZip(zip);
        billingAddress.setCountry(country);
    }


    public void addToCart(Book book, int quantity) {
        cart.addItem(new CartItem(book.getTitle(), book.getPrice(), quantity));
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
        Order order = new Order(cart, this.subscription, shippingAddress, billingAddress);
        order.setOrderStatus("Order Placed");
        order.setDateCreated("2024-01-01");
        order.setOrderStatus(DEFAULT_ORDER_STATUS);
        order.setDateCreated(java.time.LocalDate.now().toString());
        order.setUserName(this.name);
        orders.add(order);
    }
}


