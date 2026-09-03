import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
public class IntegerMinHeap {
    private final List<Integer> data = new ArrayList<>();
    private void swap(int first, int second) {
        int temp = data.get(first);
        data.set(first, data.get(second));
        data.set(second, temp);
    }
    void add(int value) {
        data.add(value);
        int index = data.size() - 1;
        while (index > 0) {
            int parent = (index - 1) / 2;
            if (data.get(parent) <= data.get(index)) {
                break;
            }
            swap(parent, index);
            index = parent;
        }
    }
    int peek() {
        if (data.isEmpty()) {
            throw new NoSuchElementException("heap 是空的");
        }
        return data.get(0);
    }
    int removeMin() {
        if (data.isEmpty()) {
            throw new NoSuchElementException("heap 是空的");
        }
        int min = data.get(0);
        int last = data.remove(data.size() - 1);
        if (!data.isEmpty()) {
            data.set(0, last);
            int index = 0;
            while (true) {
                int left = index * 2 + 1;
                int right = index * 2 + 2;
                int smallest = index;
                if (left < data.size() && data.get(left) < data.get(smallest)) {
                    smallest = left;
                }
                if (right < data.size() && data.get(right) < data.get(smallest)) {
                    smallest = right;
                }
                if (smallest == index) {
                    break;
                }
                swap(index, smallest);
                index = smallest;
            }
        }
        return min;
    }
    int size() {
        return data.size();
    }
    boolean isEmpty() {
        return data.isEmpty();
    }
    public static void main(String[] args) {
        IntegerMinHeap heap = new IntegerMinHeap();
        for (int value : new int[]{ 42, 7, 19, 7, 88, 3, 56 }) {
            heap.add(value);
        }
        System.out.println("size=" + heap.size() + " peek=" + heap.peek());
        List<Integer> removed = new ArrayList<>();
        while (!heap.isEmpty()) {
            removed.add(heap.removeMin());
        }
        System.out.println("依序取出=" + removed);
        boolean nonDecreasing = true;
        for (int i = 1; i < removed.size(); i++) {
            if (removed.get(i) < removed.get(i - 1)) {
                nonDecreasing = false;
            }
        }
        System.out.println("非遞減=" + nonDecreasing);
        try {
            heap.peek();
        } catch (NoSuchElementException e) {
            System.out.println("空heap peek丟例外=" + e.getMessage());
        }
        try {
            heap.removeMin();
        } catch (NoSuchElementException e) {
            System.out.println("空heap removeMin丟例外=" + e.getMessage());
        }
    }
}
