import java.util.ArrayList;
import java.util.List;
class StringEntry {
    String key;
    String value;
    StringEntry(String key, String value) {
        this.key = key;
        this.value = value;
    }
    @Override
    public String toString() {
        return key + "=" + value;
    }
}
public class ResizableStringMap {
    private List<List<StringEntry>> buckets;
    private int size;
    ResizableStringMap(int bucketCount) {
        buckets = makeBuckets(Math.max(1, bucketCount));
    }
    private List<List<StringEntry>> makeBuckets(int count) {
        List<List<StringEntry>> result = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            result.add(new ArrayList<>());
        }
        return result;
    }
    private int index(String key, int bucketCount) {
        return Math.floorMod(key.hashCode(), bucketCount);
    }
    double loadFactor() {
        return (double) size / buckets.size();
    }
    int bucketCount() {
        return buckets.size();
    }
    private void resize() {
        List<List<StringEntry>> old = buckets;
        buckets = makeBuckets(old.size() * 2 + 1);
        for (List<StringEntry> chain : old) {
            for (StringEntry entry : chain) {
                buckets.get(index(entry.key, buckets.size())).add(entry);
            }
        }
    }
    boolean put(String key, String value) {
        if (key == null || key.isBlank()) {
            return false;
        }
        List<StringEntry> chain = buckets.get(index(key, buckets.size()));
        for (StringEntry entry : chain) {
            if (entry.key.equals(key)) {
                entry.value = value;
                return false;
            }
        }
        chain.add(new StringEntry(key, value));
        size++;
        if (loadFactor() > 0.75) {
            resize();
        }
        return true;
    }
    String get(String key) {
        if (key == null) {
            return null;
        }
        for (StringEntry entry : buckets.get(index(key, buckets.size()))) {
            if (entry.key.equals(key)) {
                return entry.value;
            }
        }
        return null;
    }
    int size() {
        return size;
    }
    public static void main(String[] args) {
        ResizableStringMap map = new ResizableStringMap(4);
        String[] names = { "Eunice", "Bsy", "Candice", "Doris", "Ella", "Fay" };
        for (int i = 0; i < names.length; i++) {
            map.put(names[i], "M0" + (i + 1));
            System.out.println("put " + names[i] + " size=" + map.size() + " bucket=" + map.bucketCount() + " load=" + map.loadFactor());
        }
        System.out.println("get Candice=" + map.get("Candice"));
        System.out.println("get Zoe=" + map.get("Zoe"));
        System.out.println("更新Bsy=" + map.put("Bsy", "M99") + " get=" + map.get("Bsy"));
        System.out.println("null key put=" + map.put(null, "x"));
        System.out.println("空map get=" + new ResizableStringMap(3).get("a"));
    }
}
