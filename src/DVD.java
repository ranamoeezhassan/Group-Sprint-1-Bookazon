public class DVD implements Media {
    private String title;
    private String director;
    private double price;
    private int durationMinutes;

    public DVD(String title, String director, double price, int durationMinutes) {
        this.title = title;
        this.director = director;
        this.price = price;
        this.durationMinutes = durationMinutes;
    }

    public String getTitle() {
        return title;
    }

    public double getPrice() {
        return price;
    }

    public String getDirector() {
        return director;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public void printDetails() {
        System.out.println("DVD Title: " + title);
        System.out.println("Director: " + director);
        System.out.println("Duration: " + durationMinutes + " minutes");
        System.out.println("Price: $" + price);
    }
}
