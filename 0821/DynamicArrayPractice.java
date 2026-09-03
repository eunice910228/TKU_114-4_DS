class DynamicArray<T> {
    private Object[] data;
    private int size;
    DynamicArray(int capacity) {
        data = new Object[Math.max(1, capacity)];
    }
    private void grow() {
        Object[] bigger = new Object[data.length * 2];
        for (int i = 0; i < size; i++) {
            bigger[i] = data[i];
        }
        data = bigger;
    }
    void add(T value) {
        add(size, value);
    }
    void add(int index, T value) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("index " + index);
        }
        if (size == data.length) {
            grow();
        }
        for (int i = size; i > index; i--) {
            data[i] = data[i - 1];
        }
        data[index] = value;
        size++;
    }
    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("index " + index);
        }
    }
    @SuppressWarnings("unchecked")
    T get(int index) {
        checkIndex(index);
        return (T) data[index];
    }
    @SuppressWarnings("unchecked")
    T set(int index, T value) {
        checkIndex(index);
        T old = (T) data[index];
        data[index] = value;
        return old;
    }
    @SuppressWarnings("unchecked")
    T remove(int index) {
        checkIndex(index);
        T removed = (T) data[index];
        for (int i = index; i < size - 1; i++) {
            data[i] = data[i + 1];
        }
        size--;
        data[size] = null;
        return removed;
    }
    int size() {
        return size;
    }
    int capacity() {
        return data.length;
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(data[i]);
        }
        return sb.append("]").toString();
    }
}
public class DynamicArrayPractice {
    static void tryBad(DynamicArray<String> list, int index) {
        try {
            list.remove(index);
            System.out.println("remove(" + index + ")=成功");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("remove(" + index + ")=" + e.getMessage());
        }
    }
    public static void main(String[] args) {
        DynamicArray<String> names = new DynamicArray<>(2);
        names.add("Eunice");
        names.add("Bsy");
        System.out.println("加2筆 " + names + " size=" + names.size() + " capacity=" + names.capacity());
        names.add("Candice");
        System.out.println("加第3筆擴充 " + names + " capacity=" + names.capacity());
        names.add(1, "Doris");
        System.out.println("index1插入 " + names);
        System.out.println("get(1)=" + names.get(1));
        System.out.println("set(0,Ella)舊值=" + names.set(0, "Ella") + " " + names);
        System.out.println("remove(1)=" + names.remove(1) + " " + names + " size=" + names.size());
        tryBad(names, -1);
        tryBad(names, names.size());
        DynamicArray<Integer> numbers = new DynamicArray<>(1);
        for (int i = 1; i <= 5; i++) {
            numbers.add(i * 10);
        }
        System.out.println("Integer " + numbers + " capacity=" + numbers.capacity());
        System.out.println("Integer remove(0)=" + numbers.remove(0) + " " + numbers);
        DynamicArray<String> empty = new DynamicArray<>(2);
        tryBad(empty, 0);
    }
}
