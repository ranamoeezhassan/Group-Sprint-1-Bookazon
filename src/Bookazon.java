
import java.util.ArrayList;

public class Bookazon {

    private ArrayList<Book> books;
    private ArrayList<User> users;

    public Bookazon() {
        books = new ArrayList<>();
        users = new ArrayList<>();
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public void addUser(User user) {
        users.add(user);
    }

    public void viewBooks() {
        for (Book book : books) {
            book.printDetails();
        }
    }

    public void viewUsers() {
        for (User user : users) {
            System.out.println(user.getName() + " - Subscription: " + user.getSubscription());
        }
    }

    public void removeBook(Book book) {
        books.remove(book);
    }

    public void removeUser(User user) {
        users.remove(user);
    }

    public void updateBookDetails(Book book, String newTitle, String newAuthor, int newYearPublished, double newPrice, boolean isPaperback) {
        book.setTitle(newTitle);
        book.setAuthor(newAuthor);
        book.setYearPublished(newYearPublished);
        book.setPrice(newPrice);
        book.setPaperback(isPaperback);
    }

    public void updateSubscription(User user, Subscription subscription) {
        user.setSubscription(subscription);
    }

    public Book getBook(int index) {
        return books.get(index);
    }

    public User getUser(int index) {
        return users.get(index);
    }

    public int getBooksCount() {
        return books.size();
    }

    public int getUsersCount() {
        return users.size();
    }

    
    public static void main(String[] args) {
        
        Bookazon bookazon = new Bookazon();
        
        // create books
        bookazon.addBook(new Book("The Great Gatsby", "F. Scott Fitzgerald", 1925, 9.99, true));
        bookazon.addBook(new Book("To Kill a Mockingbird", "Harper Lee", 1960, 7.99, false));
        bookazon.addBook(new Book("1984", "George Orwell", 1949, 8.99, true));

        // create users
        bookazon.addUser(new User("Alice", new Normal()));
        bookazon.addUser(new User("Bob", new Gold()));

        // add books to cart
        bookazon.getUser(0).addToCart(bookazon.getBook(0), 1);
        bookazon.getUser(0).addToCart(bookazon.getBook(1), 2);
        
        // view cart
        bookazon.getUser(0).viewCart();

        // set shipping address and billing address
        bookazon.getUser(0).setShippingAddress(new ShippingAddress("123 Main St", "", "Springfield", "IL", "62701", "USA"));
        bookazon.getUser(0).setBillingAddress(new BillingAddress("456 Elm St", "", "Springfield", "IL", "62702", "USA"));

        // checkout
        bookazon.getUser(0).checkout();

        // view order details
        bookazon.getUser(0).viewOrders();
        
    }
}
