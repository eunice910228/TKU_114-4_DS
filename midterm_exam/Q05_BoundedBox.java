import java.util.ArrayList;
import java.util.List;

public class Q05_BoundedBox<T extends Comparable<T>> {
    private final int capacity;
    private final List<T> items = new ArrayList<>();
    public Q05_BoundedBox(int capacity) {
        if (capacity < 1) {
            throw new IllegalArgumentException("capacity 必須至少為 1");
        }
        this.capacity = capacity;
    }
    public boolean add(T value) {
        if (value == null || items.size() >= capacity) {
            return false;
        }
        items.add(value);
        return true;
    }
    public int size() {
        return items.size();
    }
    public boolean isFull() {
        return items.size() >= capacity;
    }
    public T minimum() {
        T best = null;
        for (T value : items) {
            if (best == null || value.compareTo(best) < 0) {
                best = value;
            }
        }
        return best;
    }
    public T maximum() {
        T best = null;
        for (T value : items) {
            if (best == null || value.compareTo(best) > 0) {
                best = value;
            }
        }
        return best;
    }
    public int countGreaterThan(T threshold) {
        if (threshold == null) {
            return 0;
        }
        int count = 0;
        for (T value : items) {
            if (value.compareTo(threshold) > 0) {
                count++;
            }
        }
        return count;
    }
    public List<T> snapshot() {
        return new ArrayList<>(items);
    }
    public static void main(String[] args) {
        Q05_BoundedBox<Integer> box = new Q05_BoundedBox<>(3);
        System.out.println(box.add(40));
        System.out.println(box.add(10));
        System.out.println(box.add(30));
        System.out.println(box.add(20));
        System.out.println(box.minimum());
        System.out.println(box.maximum());
        System.out.println(box.countGreaterThan(25));
        System.out.println(box.snapshot());
		//System.out.println(box.isFull());
    }
}
