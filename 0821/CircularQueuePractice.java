import java.util.Arrays;
class CircularQueue<T> {
    private final Object[] data;
    private int front;
    private int rear;
    private int size;
    CircularQueue(int capacity) {
        data = new Object[Math.max(1, capacity)];
    }
    boolean enqueue(T value) {
        if (size == data.length) {
            return false;
        }
        data[rear] = value;
        rear = (rear + 1) % data.length;
        size++;
        return true;
    }
    @SuppressWarnings("unchecked")
    T dequeue() {
        if (size == 0) {
            return null;
        }
        T value = (T) data[front];
        data[front] = null;
        front = (front + 1) % data.length;
        size--;
        return value;
    }
    int size() {
        return size;
    }
    String state() {
        return Arrays.toString(data) + " front=" + front + " rear=" + rear + " size=" + size;
    }
}
public class CircularQueuePractice {
    public static void main(String[] args) {
        CircularQueue<String> queue = new CircularQueue<>(4);
        String[] ops = { "enqueue A", "enqueue B", "enqueue C", "dequeue", "dequeue",
                "enqueue D", "enqueue E", "enqueue F", "dequeue", "enqueue G" };
        for (String op : ops) {
            String result;
            if (op.startsWith("enqueue")) {
                result = String.valueOf(queue.enqueue(op.substring(8)));
            } else {
                result = String.valueOf(queue.dequeue());
            }
            System.out.println(op + "=" + result + " " + queue.state());
        }
        System.out.print("FIFO取出=");
        while (queue.size() > 0) {
            System.out.print(queue.dequeue() + " ");
        }
        System.out.println();
        System.out.println("空queue dequeue=" + queue.dequeue());
    }
}
