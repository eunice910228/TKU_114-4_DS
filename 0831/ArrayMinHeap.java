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
    int size() {
        return size;
    }
    int capacity() {
        return data.length;
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
        System.out.println();
    }
}
