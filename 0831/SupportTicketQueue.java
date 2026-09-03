import java.util.Comparator;
import java.util.PriorityQueue;
class Ticket {
    String id;
    int severity;
    int createdOrder;
    Ticket(String id, int severity, int createdOrder) {
        this.id = id;
        this.severity = severity;
        this.createdOrder = createdOrder;
    }
    @Override
    public String toString() {
        return id + "|" + severity + "|" + createdOrder;
    }
}
public class SupportTicketQueue {
    private final PriorityQueue<Ticket> queue = new PriorityQueue<>(
            Comparator.comparingInt((Ticket t) -> -t.severity)
                    .thenComparingInt(t -> t.createdOrder));
    private int nextOrder = 1;
    boolean submit(String id, int severity) {
        if (id == null || id.isBlank() || severity < 1) {
            return false;
        }
        queue.offer(new Ticket(id, severity, nextOrder));
        nextOrder++;
        return true;
    }
    Ticket next() {
        return queue.poll();
    }
    int size() {
        return queue.size();
    }
    public static void main(String[] args) {
        SupportTicketQueue tickets = new SupportTicketQueue();
        System.out.println("submit T1 sev2=" + tickets.submit("T1", 2));
        System.out.println("submit T2 sev5=" + tickets.submit("T2", 5));
        System.out.println("submit T3 sev2=" + tickets.submit("T3", 2));
        System.out.println("submit T4 sev5=" + tickets.submit("T4", 5));
        System.out.println("submit T5 sev1=" + tickets.submit("T5", 1));
        System.out.println("severity 0 submit=" + tickets.submit("T6", 0));
        System.out.println("處理順序");
        while (tickets.size() > 0) {
            System.out.println(tickets.next());
        }
        System.out.println("空queue next=" + tickets.next());
    }
}
