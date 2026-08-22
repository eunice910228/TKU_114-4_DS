import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Repository<T> {
    private final List<T> items = new ArrayList<>();
    private final String repositoryName;

    Repository(String repositoryName) {
        this.repositoryName = (repositoryName == null || repositoryName.isBlank())
                ? "repository" : repositoryName;
    }
    boolean add(T item) {
        if (item == null) {
            return false;
        }
        return items.add(item);
    }
    T get(int index) {
        if (index < 0 || index >= items.size()) {
            return null;
        }
        return items.get(index);
    }
    boolean remove(T item) {
        if (item == null) {
            return false;
        }
        return items.remove(item);
    }
    T removeAt(int index) {
        if (index < 0 || index >= items.size()) {
            return null;
        }
        return items.remove(index);
    }
    int size() {
        return items.size();
    }
    boolean isEmpty() {
        return items.isEmpty();
    }
    boolean contains(T item) {
        return item != null && items.contains(item);
    }
    List<T> asList() {
        return Collections.unmodifiableList(items);
    }
    void printAll() {
        System.out.println("  [" + repositoryName + "] size=" + items.size());
        if (items.isEmpty()) {
            System.out.println("    (空)");
            return;
        }
        for (int i = 0; i < items.size(); i++) {
            System.out.println("    [" + i + "] " + items.get(i));
        }
    }
}

class Product {
    private final String id;
    private final String name;
    private final int price;

    Product(String id, String name, int price) {
        this.id = (id == null || id.isBlank()) ? "unknown" : id;
        this.name = (name == null || name.isBlank()) ? "noname" : name;
        this.price = Math.max(0, price);
    }
    String getId() { return id; }
    int getPrice() { return price; }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Product other)) {
            return false;
        }
        return id.equals(other.id);
    }
    @Override
    public int hashCode() {
        return id.hashCode();
    }
    @Override
    public String toString() {
        return String.format("%-6s %-10s %5d 元", id, name, price);
    }
}

public class GenericRepositorySystem {

    static <E> Repository<E> copyOf(String name, Repository<E> source) {
        Repository<E> copy = new Repository<>(name);
        if (source == null) {
            return copy;
        }
        for (E item : source.asList()) {
            copy.add(item);
        }
        return copy;
    }

    static <E extends Comparable<E>> E maxOf(Repository<E> repo) {
        if (repo == null || repo.isEmpty()) {
            return null;
        }
        E best = repo.get(0);
        for (E item : repo.asList()) {
            if (item.compareTo(best) > 0) {
                best = item;
            }
        }
        return best;
    }

    public static void main(String[] args) {
        System.out.println("=== Repository<String> ===");
        Repository<String> names = new Repository<>("姓名清單");
        System.out.println("  add(\"Eunice\") = " + names.add("Eunice"));
        System.out.println("  add(\"Bsy\")    = " + names.add("Bsy"));
        System.out.println("  add(null)     = " + names.add(null));
        names.printAll();
        System.out.println("  get(99)       = " + names.get(99));
        System.out.println("  remove(\"Bsy\") = " + names.remove("Bsy"));
        System.out.println("  remove(\"Zoe\") = " + names.remove("Zoe"));
        names.printAll();

        System.out.println("\n=== Repository<Product> ===");
        Repository<Product> products = new Repository<>("商品清單");
        products.add(new Product("A01", "機械鍵盤", 2490));
        products.add(new Product("A02", "無線滑鼠", 770));
        products.printAll();
        System.out.println("  get(1).getPrice() = " + products.get(1).getPrice());
        System.out.println("  remove(id=A02)    = "
                + products.remove(new Product("A02", "任何名稱", 0)));
        System.out.println("  removeAt(0)       = " + products.removeAt(0));
        System.out.println("  removeAt(5)       = " + products.removeAt(5));
        products.printAll();

        System.out.println("\n=== Generic method ===");
        Repository<String> teamA = new Repository<>("A 組");
        teamA.add("Eunice");
        teamA.add("Bsy");
        teamA.add("Candice");
        copyOf("A 組副本", teamA).printAll();
        products.add(new Product("A03", "螢幕", 5990));
        copyOf("商品副本", products).printAll();
        System.out.println("  copyOf(null) size = " + copyOf("空", null).size());

        Repository<Integer> scores = new Repository<>("分數");
        scores.add(72);
        scores.add(95);
        scores.add(88);
        System.out.println("  maxOf(分數)   = " + maxOf(scores));
        System.out.println("  maxOf(姓名)   = " + maxOf(teamA));
        System.out.println("  maxOf(空清單) = " + maxOf(new Repository<Integer>("空")));

        System.out.println("\n=== 邊界測試 ===");
        List<String> view = names.asList();
        try {
            view.add("testuser");
            System.out.println("  asList() 可竄改");
        } catch (UnsupportedOperationException e) {
            System.out.println("  asList() 唯讀，擋下外部竄改");
        }
        Repository<Integer> empty = new Repository<>("");
        empty.printAll();
        System.out.println("  get(0)         = " + empty.get(0));
        System.out.println("  removeAt(0)    = " + empty.removeAt(0));
        System.out.println("  contains(null) = " + empty.contains(null));
        System.out.println("  Product 空值   = " + new Product(null, "  ", -100));
    }
}