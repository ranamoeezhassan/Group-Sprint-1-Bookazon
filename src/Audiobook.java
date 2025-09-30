public class Audiobook implements Media {
    private String title;
    private String narrator;
    private double price;
    private int durationMinutes;

    public Audiobook(String title, String narrator, double price, int durationMinutes) {
        this.title = title;
        this.narrator = narrator;
        this.price = price;
        this.durationMinutes = durationMinutes;
    }

    public String getTitle() {
        return title;
    }

    public double getPrice() {
        return price;
    }

    public String getNarrator() {
        return narrator;
    }

    public int getDurationMinutes() {
        return durationMinutes;
}


    public void printDetails() {
        System.out.println("Audiobook Title: " + title);
        System.out.println("Narrator: " + narrator);
        System.out.println("Duration: " + durationMinutes + " minutes");
        System.out.println("Price: $" + price);
    }
}
