public class EBook implements Media {
    private String title;
    private String author;
    private double price;
    private int numPages;

    public EBook(String title, String author, double price, int numPages) {
        this.title = title;
        this.author = author;
        this.price = price;
        this.numPages = numPages;
    }

    public String getTitle() { 
    return title; 
    }

    public double getPrice() { 
        return price; 
    }

    public String getAuthor() { 
        return author; 
    }

    public int getNumPages() { 
        return numPages; 
    }


    public void printDetails() {
        System.out.println("E-Book Title: " + title);
        System.out.println("Author: " + author);
        System.out.println("Pages: " + numPages);
        System.out.println("Price: $" + price);
    }
}
