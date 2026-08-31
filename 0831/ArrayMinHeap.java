import java.util.Arrays;
public class ArrayMinHeap {
    private int[] data;
    private int size;
    ArrayMinHeap(int capacity) {
        data = new int[Math.max(1, capacity)];
    }
    private void swap(int first, int second) {
        int temp = data[first];
        data[first] = data[second];
        data[second] = temp;
    }
    private void grow() {
        data = Arrays.copyOf(data, data.length * 2);
    }
    void add(int value) {
        if (size == data.length) {
            grow();
        }
        data[size] = value;
        int index = size;
        size++;
        while (index > 0) {
            int parent = (index - 1) / 2;
            if (data[parent] <= data[index]) {
                break;
            }
            swap(parent, index);
            index = parent;
        }
    }
    int peek() {
        if (size == 0) {
            return -1;
        }
        return data[0];
    }
    int remove() {
        if (size == 0) {
            return -1;
        }
        int min = data[0];
        size--;
        data[0] = data[size];
        int index = 0;
        while (true) {
            int left = index * 2 + 1;
            int right = index * 2 + 2;
            int smaller = index;
            if (left < size && data[left] < data[smaller]) {
                smaller = left;
            }
            if (right < size && data[right] < data[smaller]) {
                smaller = right;
            }
            if (smaller == index) {
                break;
            }
            swap(index, smaller);
            index = smaller;
        }
        return min;
    }
    int size() {
        return size;
    }
    boolean isEmpty() {
        return size == 0;
    }
    int capacity() {
        return data.length;
    }
    int[] snapshot() {
        return Arrays.copyOf(data, size);
    }
    public static void main(String[] args) {
        ArrayMinHeap heap = new ArrayMinHeap(4);
        int[] responseTimes = { 128, 76, 340, 92, 55, 210, 33, 187, 401, 68,
                145, 29, 512, 84, 260, 47, 99, 173, 61, 305 };
        System.out.println("初始容量=" + heap.capacity());
        for (int time : responseTimes) {
            heap.add(time);
            if (heap.size() == heap.capacity()) {
                System.out.println("加入第" + heap.size() + "筆後容量已滿=" + heap.capacity());
            }
        }
        System.out.println("加入" + responseTimes.length + "筆後 size=" + heap.size()
                + "，容量=" + heap.capacity());
        System.out.println("最短回應時間=" + heap.peek());
        System.out.println("snapshot=" + Arrays.toString(heap.snapshot()));
        System.out.print("由小到大取出=");
        while (!heap.isEmpty()) {
            System.out.print(heap.remove() + " ");
        }
        System.out.println();
        System.out.println("空heap isEmpty=" + heap.isEmpty());
        System.out.println("空heap peek=" + heap.peek());
        System.out.println("空heap remove=" + heap.remove());
    }
}
