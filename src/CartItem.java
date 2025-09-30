public class CartItem {
    private String itemName;
    private double price;
    private int quantity;

    public CartItem(String itemName, double price, int quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }

    public String getName() {
        return itemName;
    }

    public double getPrice() {
        return price;
    }

    public void printDetails() {
        System.out.printf(
            "Item: %s, Price: $%.2f, Quantity: %d, Total: $%.2f%n",
            itemName, price, quantity, getTotalPrice()
        );
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean equals(CartItem item) {
        return this.itemName.equals(item.getName());
    }

    public double getTotalPrice() {
        return price * quantity;
    }
}
