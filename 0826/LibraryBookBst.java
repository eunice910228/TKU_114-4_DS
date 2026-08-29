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
        return isbn + " <" + title + "> " + author + (available ? " 在館" : " 借出中");
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
    boolean borrow(String isbn) {
        Book book = find(isbn);
        if (book == null || !book.available) {
            return false;
        }
        book.available = false;
        return true;
    }
    boolean returnBook(String isbn) {
        Book book = find(isbn);
        if (book == null || book.available) {
            return false;
        }
        book.available = true;
        return true;
    }
    boolean remove(String isbn) {
        Book book = find(isbn);
        if (book == null || !book.available) {
            return false;
        }
        root = remove(root, isbn);
        return true;
    }
    private BookNode remove(BookNode node, String isbn) {
        if (node == null) {
            return null;
        }
        int compare = isbn.compareTo(node.book.isbn);
        if (compare < 0) {
            node.left = remove(node.left, isbn);
        } else if (compare > 0) {
            node.right = remove(node.right, isbn);
        } else {
            if (node.left == null) {
                return node.right;
            }
            if (node.right == null) {
                return node.left;
            }
            BookNode successor = node.right;
            while (successor.left != null) {
                successor = successor.left;
            }
            node.book = successor.book;
            node.right = remove(node.right, successor.book.isbn);
        }
        return node;
    }
    void rangeQuery(String low, String high) {
        System.out.println("ISBN 範圍 " + low + " ~ " + high);
        rangeQuery(root, low, high);
    }
    private void rangeQuery(BookNode node, String low, String high) {
        if (node == null) {
            return;
        }
        if (node.book.isbn.compareTo(low) > 0) {
            rangeQuery(node.left, low, high);
        }
        if (node.book.isbn.compareTo(low) >= 0 && node.book.isbn.compareTo(high) <= 0) {
            System.out.println(node.book);
        }
        if (node.book.isbn.compareTo(high) < 0) {
            rangeQuery(node.right, low, high);
        }
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
        System.out.println("add555=" + library.add(new Book("555", "資料結構", "林老師")));
        System.out.println("add333=" + library.add(new Book("333", "Java入門", "王老師")));
        System.out.println("add777=" + library.add(new Book("777", "微積分", "陳老師")));
        System.out.println("add222=" + library.add(new Book("222", "計概", "張老師")));
        System.out.println("add666=" + library.add(new Book("666", "數理統計", "李老師")));
        System.out.println("重複add777=" + library.add(new Book("777", "重複", "x")));
        library.inorderReport();
        System.out.println("borrow777=" + library.borrow("777"));
        System.out.println("再borrow777=" + library.borrow("777"));
        System.out.println("borrow999=" + library.borrow("999"));
        System.out.println("remove777(借出中)=" + library.remove("777"));
        System.out.println("returnBook777=" + library.returnBook("777"));
        System.out.println("再returnBook777=" + library.returnBook("777"));
        System.out.println("remove777(已還)=" + library.remove("777"));
        System.out.println("remove555（root）=" + library.remove("555"));
        library.rangeQuery("222", "666");
        library.inorderReport();
    }
}