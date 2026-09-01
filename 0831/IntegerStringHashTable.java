import java.util.ArrayList;
import java.util.List;
class IntEntry {
    int key;
    String value;
    IntEntry(int key, String value) {
        this.key = key;
        this.value = value;
    }
    @Override
    public String toString() {
        return key + "=" + value;
    }
}
public class IntegerStringHashTable {
    private final List<List<IntEntry>> buckets;
    private int size;
    IntegerStringHashTable(int bucketCount) {
        if (bucketCount < 1) {
            throw new IllegalArgumentException("bucketCount 至少為 1");
        }
        buckets = new ArrayList<>();
        for (int i = 0; i < bucketCount; i++) {
            buckets.add(new ArrayList<>());
        }
    }
    private int index(int key) {
        return Math.floorMod(key, buckets.size());
    }
    boolean put(int key, String value) {
        List<IntEntry> chain = buckets.get(index(key));
        for (IntEntry entry : chain) {
            if (entry.key == key) {
                entry.value = value;
                return false;
            }
        }
        chain.add(new IntEntry(key, value));
        size++;
        return true;
    }
    String get(int key) {
        for (IntEntry entry : buckets.get(index(key))) {
            if (entry.key == key) {
                return entry.value;
            }
        }
        return null;
    }
    boolean containsKey(int key) {
        for (IntEntry entry : buckets.get(index(key))) {
            if (entry.key == key) {
                return true;
            }
        }
        return false;
    }
    boolean remove(int key) {
        List<IntEntry> chain = buckets.get(index(key));
        for (int i = 0; i < chain.size(); i++) {
            if (chain.get(i).key == key) {
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
    void bucketReport() {
        System.out.println("bucket分布");
        for (int i = 0; i < buckets.size(); i++) {
            System.out.println("bucket" + i + "=" + buckets.get(i));
        }
    }
    public static void main(String[] args) {
        IntegerStringHashTable table = new IntegerStringHashTable(4);
        System.out.println("put 10=" + table.put(10, "台北"));
        System.out.println("put 14=" + table.put(14, "台中"));
        System.out.println("put 7=" + table.put(7, "台南"));
        System.out.println("put 3=" + table.put(3, "高雄"));
        System.out.println("put 18=" + table.put(18, "新竹"));
        System.out.println("size=" + table.size());
        System.out.println("更新14=" + table.put(14, "台中更新"));
        System.out.println("更新後size=" + table.size());
        System.out.println("get 14=" + table.get(14));
        System.out.println("get 99=" + table.get(99));
        System.out.println("containsKey 7=" + table.containsKey(7));
        System.out.println("containsKey 99=" + table.containsKey(99));
        System.out.println("remove 7=" + table.remove(7));
        System.out.println("remove 99=" + table.remove(99));
        System.out.println("size=" + table.size());
        System.out.println("put 負數key -6=" + table.put(-6, "負數測試"));
        System.out.println("get -6=" + table.get(-6));
        System.out.println("put value為null的key 21=" + table.put(21, null));
        System.out.println("containsKey 21=" + table.containsKey(21));
        System.out.println("get 21=" + table.get(21));
        System.out.println("空table get=" + new IntegerStringHashTable(3).get(1));
        System.out.println("空table size=" + new IntegerStringHashTable(3).size());
        boolean threw = false;
        try {
            new IntegerStringHashTable(0);
        } catch (IllegalArgumentException e) {
            threw = true;
        }
        System.out.println("bucketCount 0 丟例外=" + threw);
        table.bucketReport();
    }
}
}