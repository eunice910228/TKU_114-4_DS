class ArrayStack<T> {
    private final Object[] data;
    private int size;
    ArrayStack(int capacity) {
        data = new Object[Math.max(1, capacity)];
    }
    boolean push(T value) {
        if (isFull()) {
            return false;
        }
        data[size] = value;
        size++;
        return true;
    }
    @SuppressWarnings("unchecked")
    T pop() {
        if (isEmpty()) {
            return null;
        }
        size--;
        T value = (T) data[size];
        data[size] = null;
        return value;
    }
    @SuppressWarnings("unchecked")
    T peek() {
        if (isEmpty()) {
            return null;
        }
        return (T) data[size - 1];
    }
    int size() {
        return size;
    }
    boolean isEmpty() {
        return size == 0;
    }
    boolean isFull() {
        return size == data.length;
    }
}
public class GenericArrayStackDemo {
    public static void main(String[] args) {
        ArrayStack<String> names = new ArrayStack<>(3);
        System.out.println("空stack pop=" + names.pop());
        System.out.println("push Eunice=" + names.push("Eunice"));
        System.out.println("push Bsy=" + names.push("Bsy"));
        System.out.println("push Candice=" + names.push("Candice"));
        System.out.println("滿了再push=" + names.push("Doris"));
        System.out.println("isFull=" + names.isFull() + " size=" + names.size());
        System.out.println("peek=" + names.peek());
        System.out.println("pop=" + names.pop());
        System.out.println("pop=" + names.pop());
        System.out.println("size=" + names.size());
        ArrayStack<Integer> numbers = new ArrayStack<>(2);
        numbers.push(7);
        numbers.push(9);
        System.out.println("Integer peek=" + numbers.peek());
        System.out.println("Integer pop=" + numbers.pop());
        System.out.println("Integer pop=" + numbers.pop());
        System.out.println("Integer isEmpty=" + numbers.isEmpty());
        System.out.println("Integer 空stack peek=" + numbers.peek());
    }
}
