import java.util.ArrayList;
import java.util.List;
public class Q04_ChainedHashTable {
    private static class Entry {
        int key;
        String value;
        Entry(int key, String value) {
            this.key = key;
            this.value = value;
        }
    }
    private final List<List<Entry>> buckets;
    private int size;
    public Q04_ChainedHashTable(int bucketCount) {
        if (bucketCount <= 0) {
            throw new IllegalArgumentException("bucketCount 必須大於 0");
        }
        buckets = new ArrayList<>();
        for (int i = 0; i < bucketCount; i++) {
            buckets.add(new ArrayList<>());
        }
    }
    private int index(int key) {
        return Math.floorMod(key, buckets.size());
    }
    public void put(int key, String value) {
        List<Entry> chain = buckets.get(index(key));
        for (Entry entry : chain) {
            if (entry.key == key) {
                entry.value = value;
                return;
            }
        }
        chain.add(new Entry(key, value));
        size++;
    }
    public String get(int key) {
        for (Entry entry : buckets.get(index(key))) {
            if (entry.key == key) {
                return entry.value;
            }
        }
        return null;
    }
    public boolean remove(int key) {
        List<Entry> chain = buckets.get(index(key));
        for (int i = 0; i < chain.size(); i++) {
            if (chain.get(i).key == key) {
                chain.remove(i);
                size--;
                return true;
            }
        }
        return false;
    }
    public int size() {
        return size;
    }
    public int longestChain() {
        int max = 0;
        for (List<Entry> chain : buckets) {
            if (chain.size() > max) {
                max = chain.size();
            }
        }
        return max;
    }
    public static void main(String[] args) {
        Q04_ChainedHashTable table = new Q04_ChainedHashTable(4);
        table.put(10, "台北");
        table.put(14, "台中");
        table.put(18, "新竹");
        table.put(-6, "負數");
        table.put(3, "高雄");
        System.out.println("size=" + table.size());
        System.out.println("longestChain=" + table.longestChain());
        table.put(14, "台中更新");
        System.out.println("更新後size=" + table.size());
        System.out.println("get 14=" + table.get(14));
        System.out.println("get -6=" + table.get(-6));
        System.out.println("get 99=" + table.get(99));
        System.out.println("remove 18=" + table.remove(18));
        System.out.println("remove 99=" + table.remove(99));
        System.out.println("size=" + table.size());
        boolean threw = false;
        try {
            new Q04_ChainedHashTable(0);
        } catch (IllegalArgumentException e) {
            threw = true;
        }
        System.out.println("bucketCount 0 丟例外=" + threw);
    }
}
