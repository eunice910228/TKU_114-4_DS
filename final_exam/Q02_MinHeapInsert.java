import java.util.ArrayList;
import java.util.List;
public class Q02_MinHeapInsert {
    private final List<Integer> data = new ArrayList<>();
    public void add(int value) {
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
    private void swap(int first, int second) {
        int temp = data.get(first);
        data.set(first, data.get(second));
        data.set(second, temp);
    }
    public Integer peek() {
        if (data.isEmpty()) {
            return null;
        }
        return data.get(0);
    }
    public int size() {
        return data.size();
    }
    public List<Integer> snapshot() {
        return new ArrayList<>(data);
    }
    public boolean isValidMinHeap() {
        for (int i = 1; i < data.size(); i++) {
            int parent = (i - 1) / 2;
            if (data.get(parent) > data.get(i)) {
                return false;
            }
        }
        return true;
    }
    public static void main(String[] args) {
        Q02_MinHeapInsert heap = new Q02_MinHeapInsert();
        System.out.println("空heap peek=" + heap.peek());
        for (int value : new int[]{ 40, 25, 60, 25, 10 }) {
            heap.add(value);
        }
        System.out.println("peek=" + heap.peek());
        System.out.println("size=" + heap.size());
        System.out.println("snapshot=" + heap.snapshot());
        System.out.println("invariant=" + heap.isValidMinHeap());
        List<Integer> copy = heap.snapshot();
        copy.set(0, 999);
        System.out.println("snapshot不影響內部 peek=" + heap.peek());
    }
}
