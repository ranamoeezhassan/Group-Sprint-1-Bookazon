public class CartItem {
    private Media media;
    private int quantity;

    public CartItem(Media media, int quantity) {
        this.media = media;
        this.quantity = quantity;
    }

    public Media getMedia() {
        return media;
    }

    public String getName() {
        return media.getTitle();
    }

    public double getPrice() {
        return media.getPrice();
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean equals(CartItem item) {
        return this.getName().equals(item.getName());
    }

    public double getTotalPrice() {
        return getPrice() * quantity;
    }
}
