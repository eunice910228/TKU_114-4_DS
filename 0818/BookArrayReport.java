class Book {
    private String isbn;
    private String title;
    private int price;
    private int stock;
    Book(String isbn, String title, int price, int stock) {
        this.isbn = (isbn == null || isbn.isBlank()) ? "UNKNOWN" : isbn.trim();
        this.title = (title == null || title.isBlank()) ? "Untitled" : title.trim();
        this.price = Math.max(0, price);
        this.stock = Math.max(0, stock);
    }
    int getPrice() {
        return price;
    }
    int getStock() {
        return stock;
    }
    int stockValue() {
        return price * stock;
    }
    @Override
    public String toString() {
        return isbn + " " + title + " price=" + price + " stock=" + stock;
    }
}
public class BookArrayReport {
    public static void main(String[] args) {
        Book[] books = {
            new Book("B001", "Java入門", 480, 12),
            new Book("B002", "資料結構", 650, 3),
            new Book("B003", "微積分", 520, 0),
            new Book("B004", "數理統計", 700, 8)
        };
        System.out.println("所有書籍");
        for (Book book : books) {
            System.out.println(book);
        }
        int totalValue = 0;
        for (Book book : books) {
            totalValue += book.stockValue();
        }
        System.out.println("庫存總價值=" + totalValue);
        Book mostExpensive = books[0];
        for (Book book : books) {
            if (book.getPrice() > mostExpensive.getPrice()) {
                mostExpensive = book;
            }
        }
        System.out.println("價格最高=" + mostExpensive);
        System.out.println("庫存不足（<=3）");
        for (Book book : books) {
            if (book.getStock() <= 3) {
                System.out.println(book);
            }
        }
    }
}
