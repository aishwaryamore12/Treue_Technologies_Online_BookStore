import java.util.*;

class Book{
    private String title;
    private String author;
    private double price;
    private String category;
    private List<Review> reviews;

    public Book(String title, String author, double price, String category) {
        this.title = title;
        this.author = author;
        this.price = price;
        this.category = category;
        this.reviews = new ArrayList<>();
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public double getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }

    public void addReview(Review review) {
        reviews.add(review);
    }

    public double getAverageRating() {
        if (reviews.isEmpty()) {
            return 0.0;
        }
        double totalRating = 0.0;
        for (Review review : reviews) {
            totalRating += review.getRating();
        }
        return totalRating / reviews.size();
    }

    public void displayDetails() {
        System.out.println("Title: " + title);
        System.out.println("Author: " + author);
        System.out.println("Category: " + category);
        System.out.println("Price: $" + price);
        System.out.println("Average Rating: " + getAverageRating());
    }
}

class Review {
    private String user;
    private String text;
    private int rating;

    public Review(String user, String text, int rating) {
        this.user = user;
        this.text = text;
        this.rating = rating;
    }

    public int getRating() {
        return rating;
    }

    @Override
    public String toString() {
        return "User: " + user + "\nRating: " + rating + "\nReview: " + text;
    }
}

class User {
    private String username;
    private String password;
    private List<Book> cart;
    private List<Order> orderHistory;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.cart = new ArrayList<>();
        this.orderHistory = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public void addToCart(Book book) {
        cart.add(book);
    }

    public void removeFromCart(Book book) {
        cart.remove(book);
    }

    public void checkout() {
        if (!cart.isEmpty()) {
            Order order = new Order(this, cart);
            orderHistory.add(order);
            cart.clear();
            System.out.println("Order placed successfully!");
        } else {
            System.out.println("Your cart is empty. Add books to your cart before checkout.");
        }
    }

    public List<Order> getOrderHistory() {
        return orderHistory;
    }
}

class Order {
    private User user;
    private List<Book> books;

    public Order(User user, List<Book> books) {
        this.user = user;
        this.books = books;
    }

    public User getUser() {
        return user;
    }

    public List<Book> getBooks() {
        return books;
    }
}

class OnlineBookstore {
    private List<Book> books;
    private List<User> users;

    public OnlineBookstore() {
        books = new ArrayList<>();
        users = new ArrayList<>();
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public Book findBook(String title) {
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                return book;
            }
        }
        return null;
    }

    public void addUser(User user) {
        users.add(user);
    }

    public User findUser(String username) {
        for (User user : users) {
            if (user.getUsername().equalsIgnoreCase(username)) {
                return user;
            }
        }
        return null;
    }

    public List<Book> getBooksByCategory(String category) {
        List<Book> result = new ArrayList<>();
        for (Book book : books) {
            if (book.getCategory().equalsIgnoreCase(category)) {
                result.add(book);
            }
        }
        return result;
    }

    public void displayBookCategories() {
        Set<String> categories = new HashSet<>();
        for (Book book : books) {
            categories.add(book.getCategory());
        }
        System.out.println("Available Categories:");
        for (String category : categories) {
            System.out.println(category);
        }
    }
}

class Bookworm {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        OnlineBookstore bookstore = new OnlineBookstore();

        // Sample data
        bookstore.addBook(new Book("The Great Gatsby", "F. Scott Fitzgerald", 10.99, "Fiction"));
        bookstore.addBook(new Book("To Kill a Mockingbird", "Harper Lee", 12.99, "Fiction"));
        bookstore.addBook(new Book("Java Programming", "John Doe", 29.99, "Programming"));
        // Add more books...

        while (true) {
            System.out.println("Welcome to the Online Book Store!");
            System.out.println("1. Register");
            System.out.println("2. Browse Books");
            System.out.println("3. Search Books");
            System.out.println("4. Exit");
            System.out.print("Select an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter your username: ");
                    String newUser = scanner.nextLine();
                    System.out.print("Enter your password: ");
                    String newPassword = scanner.nextLine();
                    bookstore.addUser(new User(newUser, newPassword));
                    System.out.println("User registered successfully!");
                    break;
                case 2:
                    System.out.println("Available Categories:");
                    bookstore.displayBookCategories();
                    System.out.print("Select a category: ");
                    String category = scanner.nextLine();
                    System.out.println("Books in " + category + " category:");
                    List<Book> booksByCategory = bookstore.getBooksByCategory(category);
                    for (int i = 0; i < booksByCategory.size(); i++) {
                        System.out.println((i + 1) + ". " + booksByCategory.get(i).getTitle());
                    }
                    break;
                case 3:
                    System.out.print("Enter book title: ");
                    String title = scanner.nextLine();
                    Book book = bookstore.findBook(title);
                    if (book != null) {
                        book.displayDetails();
                        System.out.println("1. Add to Cart");
                        System.out.println("2. Back");
                        System.out.print("Select an option: ");
                        int bookChoice = scanner.nextInt();
                        scanner.nextLine(); // Consume newline
                        if (bookChoice == 1) {
                            System.out.print("Enter your username: ");
                            String username = scanner.nextLine();
                            User user = bookstore.findUser(username);
                            if (user != null) {
                                user.addToCart(book);
                                System.out.println(book.getTitle() + " added to cart!");
                            } else {
                                System.out.println("User not found.");
                            }
                        }
                    } else {
                        System.out.println("Book not found.");
                    }
                    break;
                case 4:
                    System.out.println("Thank you for using the Online Book Store. Goodbye!");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
            }
        }
    }
}
