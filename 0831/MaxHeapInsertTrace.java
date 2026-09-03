import java.util.ArrayList;
import java.util.List;
public class MaxHeapInsertTrace {
    private final List<Integer> data = new ArrayList<>();
    void add(int value) {
        data.add(value);
        int index = data.size() - 1;
        while (index > 0) {
            int parent = (index - 1) / 2;
            if (data.get(parent) >= data.get(index)) {
                break;
            }
            int temp = data.get(parent);
            data.set(parent, data.get(index));
            data.set(index, temp);
            index = parent;
        }
    }
    Integer peekMax() {
        return data.isEmpty() ? null : data.get(0);
    }
    List<Integer> snapshot() {
        return new ArrayList<>(data);
    }
    public static void main(String[] args) {
        MaxHeapInsertTrace heap = new MaxHeapInsertTrace();
        System.out.println("空heap peekMax=" + heap.peekMax());
        for (int value : new int[]{ 25, 40, 10, 50, 30, 50 }) {
            heap.add(value);
            System.out.println("add " + value + "=" + heap.snapshot());
        }
        System.out.println("root=" + heap.peekMax());
    }
}
