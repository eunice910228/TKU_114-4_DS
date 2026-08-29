class Book {
    String isbn;
    String title;
    String author;
    boolean available;
    Book(String isbn, String title, String author) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.available = true;
    }
    @Override
    public String toString() {
        return isbn  + title + author + (available ? "在館" : "借出中");
    }
}

class BookNode {
    Book book;
    BookNode left;
    BookNode right;

    BookNode(Book book) {
        this.book = book;
    }
}
public class LibraryBookBst {
    private BookNode root;

    boolean add(Book book) {
        if (book == null || find(book.isbn) != null) {
            return false;
        }
        root = add(root, book);
        return true;
    }
    private BookNode add(BookNode node, Book book) {
        if (node == null) {
            return new BookNode(book);
        }
        if (book.isbn.compareTo(node.book.isbn) < 0) {
            node.left = add(node.left, book);
        } else {
            node.right = add(node.right, book);
        }
        return node;
    }
    Book find(String isbn) {
        BookNode current = root;
        while (current != null) {
            int compare = isbn.compareTo(current.book.isbn);
            if (compare == 0) {
                return current.book;
            }
            current = (compare < 0) ? current.left : current.right;
        }
        return null;
    }
    void inorderReport() {
        System.out.println("館藏清單（依 ISBN）");
        inorderReport(root);
    }
    private void inorderReport(BookNode node) {
        if (node == null) {
            return;
        }
        inorderReport(node.left);
        System.out.println(node.book);
        inorderReport(node.right);
    }
    public static void main(String[] args) {
        LibraryBookBst library = new LibraryBookBst();
        System.out.println("add520=" + library.add(new Book("520", "資料結構", "林老師")));
        System.out.println("add311=" + library.add(new Book("311", "Java入門", "王老師")));
        System.out.println("add777=" + library.add(new Book("777", "微積分", "陳老師")));
        System.out.println("add250=" + library.add(new Book("250", "計概", "張老師")));
        System.out.println("add666=" + library.add(new Book("666", "數理統計", "李老師")));
        System.out.println("重複add777=" + library.add(new Book("777", "重複", "x")));
        library.inorderReport();
    }
}
