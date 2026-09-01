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
        if (bucketCount < 1) {
            throw new IllegalArgumentException("bucketCount 至少為 1");
        }
        buckets = new ArrayList<>();
        for (int i = 0; i < bucketCount; i++) {
            buckets.add(new ArrayList<>());
        }
    }
    private int index(String isbn) {
        return Math.floorMod(isbn.hashCode(), buckets.size());
    }
    boolean put(String isbn, String title) {
        if (isbn == null || isbn.isBlank()) {
            return false;
        }
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
        if (isbn == null || isbn.isBlank()) {
            return null;
        }
        for (BookEntry entry : buckets.get(index(isbn))) {
            if (entry.isbn.equals(isbn)) {
                return entry.title;
            }
        }
        return null;
    }
    boolean remove(String isbn) {
        if (isbn == null || isbn.isBlank()) {
            return false;
        }
        List<BookEntry> chain = buckets.get(index(isbn));
        for (int i = 0; i < chain.size(); i++) {
            if (chain.get(i).isbn.equals(isbn)) {
                chain.remove(i);
                size--;
                return true;
            }
        }
        return false;
    }
    int size() {
        return size;
    }
    double loadFactor() {
        return (double) size / buckets.size();
    }
    void bucketReport() {
        System.out.println("bucket 分布");
        for (int i = 0; i < buckets.size(); i++) {
            System.out.println("  bucket" + i + "=" + buckets.get(i));
        }
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
        System.out.println("remove 9783=" + table.remove("9783"));
        System.out.println("remove 9999=" + table.remove("9999"));
        System.out.println("size=" + table.size());
        System.out.println("null isbn put=" + table.put(null, "測試"));
        System.out.println("空白 isbn get=" + table.get("  "));
        System.out.println("空表 size=" + new BookIsbnHashTable(3).size());
        System.out.println("空表 get=" + new BookIsbnHashTable(3).get("9781"));
        System.out.println("load factor=" + table.loadFactor());
        table.bucketReport();
    }
}
