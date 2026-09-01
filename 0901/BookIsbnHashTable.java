import java.util.ArrayList;
import java.util.List;
class BookEntry {
    String isbn;
    String title;
    BookEntry(String isbn, String title) {
        this.isbn = isbn;
        this.title = title;
    }
    @Override
    public String toString() {
        return isbn + " " + title;
    }
}
public class BookIsbnHashTable {
    private final List<List<BookEntry>> buckets;
    private int size;
    BookIsbnHashTable(int bucketCount) {
        buckets = new ArrayList<>();
        for (int i = 0; i < bucketCount; i++) {
            buckets.add(new ArrayList<>());
        }
    }
    private int index(String isbn) {
        return Math.floorMod(isbn.hashCode(), buckets.size());
    }
    boolean put(String isbn, String title) {
        List<BookEntry> chain = buckets.get(index(isbn));
        for (BookEntry entry : chain) {
            if (entry.isbn.equals(isbn)) {
                entry.title = title;
                return false;
            }
        }
        chain.add(new BookEntry(isbn, title));
        size++;
        return true;
    }
    String get(String isbn) {
        for (BookEntry entry : buckets.get(index(isbn))) {
            if (entry.isbn.equals(isbn)) {
                return entry.title;
            }
        }
        return null;
    }
    int size() {
        return size;
    }
    public static void main(String[] args) {
        BookIsbnHashTable table = new BookIsbnHashTable(5);
        System.out.println("put 9781=" + table.put("9781", "資料結構"));
        System.out.println("put 9782=" + table.put("9782", "微積分"));
        System.out.println("put 9783=" + table.put("9783", "計概"));
        System.out.println("put 9784=" + table.put("9784", "資料庫"));
        System.out.println("更新 9782=" + table.put("9782", "高等微積分"));
        System.out.println("size=" + table.size());
        System.out.println("get 9782=" + table.get("9782"));
        System.out.println("get 9999=" + table.get("9999"));
        System.out.println("size=" + table.size());
    }
}
