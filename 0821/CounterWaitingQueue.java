import java.util.ArrayDeque;
import java.util.Deque;
class Customer {
    String name;
    int number;
    Customer(String name, int number) {
        this.name = name;
        this.number = number;
    }
    @Override
    public String toString() {
        return number + "號 " + name;
    }
}
public class CounterWaitingQueue {
    private final Deque<Customer> queue = new ArrayDeque<>();
    private int nextNumber = 1;
    boolean join(String name) {
        if (name == null || name.isBlank()) {
            return false;
        }
        queue.offer(new Customer(name, nextNumber));
        nextNumber++;
        return true;
    }
    Customer peekNext() {
        return queue.peek();
    }
    Customer serveNext() {
        return queue.poll();
    }
    int waitingCount() {
        return queue.size();
    }
    public static void main(String[] args) {
        CounterWaitingQueue counter = new CounterWaitingQueue();
        System.out.println("空隊列peek=" + counter.peekNext());
        System.out.println("空隊列serve=" + counter.serveNext());
        System.out.println("join Eunice=" + counter.join("Eunice"));
        System.out.println("join Bsy=" + counter.join("Bsy"));
        System.out.println("join Candice=" + counter.join("Candice"));
        System.out.println("空白name join=" + counter.join("  "));
        System.out.println("等候數=" + counter.waitingCount());
        System.out.println("下一位=" + counter.peekNext());
        System.out.println("服務=" + counter.serveNext());
        System.out.println("服務=" + counter.serveNext());
        System.out.println("等候數=" + counter.waitingCount());
        System.out.println("服務=" + counter.serveNext());
        System.out.println("服務=" + counter.serveNext());
        System.out.println("等候數=" + counter.waitingCount());
    }
}
