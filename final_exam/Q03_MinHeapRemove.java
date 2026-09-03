import java.util.ArrayList;
import java.util.List;
public class Q03_MinHeapRemove {
    private final List<Integer> data = new ArrayList<>();
    public Q03_MinHeapRemove(List<Integer> values) {
        if (values != null) {
            for (Integer value : values) {
                if (value != null) {
                    data.add(value);
                }
            }
        }
        for (int i = data.size() / 2 - 1; i >= 0; i--) {
            siftDown(i);
        }
    }
    private void siftDown(int index) {
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
    private void swap(int first, int second) {
        int temp = data.get(first);
        data.set(first, data.get(second));
        data.set(second, temp);
    }
    public Integer removeMin() {
        if (data.isEmpty()) {
            return null;
        }
        int min = data.get(0);
        int last = data.remove(data.size() - 1);
        if (!data.isEmpty()) {
            data.set(0, last);
            siftDown(0);
        }
        return min;
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
    public static void main(String[] args) {
        List<Integer> values = new ArrayList<>(List.of(50, 20, 80, 10, 30));
        values.add(null);
        values.add(60);
        Q03_MinHeapRemove heap = new Q03_MinHeapRemove(values);
        System.out.println("heapify後 peek=" + heap.peek());
        System.out.println("size=" + heap.size());
        System.out.println("snapshot=" + heap.snapshot());
        System.out.print("依序取出=");
        while (heap.size() > 0) {
            System.out.print(heap.removeMin() + " ");
        }
        System.out.println();
        System.out.println("空heap removeMin=" + heap.removeMin());
        System.out.println("空heap peek=" + heap.peek());
        Q03_MinHeapRemove single = new Q03_MinHeapRemove(List.of(7));
        System.out.println("單元素 removeMin=" + single.removeMin() + "，之後size=" + single.size());
        Q03_MinHeapRemove empty = new Q03_MinHeapRemove(null);
        System.out.println("null輸入 size=" + empty.size());
    }
}
