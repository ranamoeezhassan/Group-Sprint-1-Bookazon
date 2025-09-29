import java.util.ArrayList;

public class Cart {
    private ArrayList<CartItem> items;
    
    public Cart() {
        items = new ArrayList<>();
    }
    
    public void addItem(CartItem item) {
        items.add(item);
    }

    public void addBook(Book book, int quantity) {
        CartItem item = new CartItem(book.getTitle(), book.getPrice(), quantity);
        addItem(item);
    }

    public void removeItem(CartItem item) {
        items.remove(item);
    }

    public void removeBook(Book book) {
        for (CartItem item : items) {
            if (item.getName().equals(book.getTitle())) {
                items.remove(item);
                break;
            }
        }
    }
    
    public void updateQuantity(CartItem item, int quantity) {
        for (CartItem cartItem : items) {
            if (cartItem.equals(item)) {
                cartItem.setQuantity(quantity);
                break;
            }
        }
    }
    
    public void viewCartDetails() {
        System.out.println("Cart Details:");
        for (CartItem item : items) {
            System.out.println(item.getName() + " - Quantity: " + item.getQuantity());
        }
        System.out.println("\n");
    }
    
    public ArrayList<CartItem> getItems() {
        return items;
    }
}
