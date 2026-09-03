import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
class ServiceRequest {
    int id;
    int priority;
    String description;
    int sequence;
    ServiceRequest(int id, int priority, String description, int sequence) {
        this.id = id;
        this.priority = priority;
        this.description = description;
        this.sequence = sequence;
    }
    @Override
    public String toString() {
        return "#" + id + " P" + priority + " " + description;
    }
}
public class ServiceRequestSystem {
    private final Map<Integer, ServiceRequest> byId = new HashMap<>();
    private final PriorityQueue<ServiceRequest> queue = new PriorityQueue<>(
            Comparator.comparingInt((ServiceRequest r) -> r.priority)
                    .thenComparingInt(r -> r.sequence));
    private int sequence;
    boolean submit(int id, int priority, String description) {
        if (id <= 0 || priority < 1 || priority > 5
                || description == null || description.isBlank()) {
            return false;
        }
        if (byId.containsKey(id)) {
            return false;
        }
        ServiceRequest request = new ServiceRequest(id, priority, description, sequence);
        sequence++;
        byId.put(id, request);
        queue.offer(request);
        return true;
    }
    ServiceRequest findById(int id) {
        return byId.get(id);
    }
    boolean cancel(int id) {
        return byId.remove(id) != null;
    }
    ServiceRequest processNext() {
        while (!queue.isEmpty()) {
            ServiceRequest next = queue.poll();
            if (byId.remove(next.id) != null) {
                return next;
            }
        }
        return null;
    }
    int pendingCount() {
        return byId.size();
    }
    public static void main(String[] args) {
        ServiceRequestSystem desk = new ServiceRequestSystem();
        System.out.println("submit 101 P3 印表機卡紙=" + desk.submit(101, 3, "印表機卡紙"));
        System.out.println("submit 102 P1 伺服器當機=" + desk.submit(102, 1, "伺服器當機"));
        System.out.println("submit 103 P3 螢幕閃爍=" + desk.submit(103, 3, "螢幕閃爍"));
        System.out.println("submit 104 P2 網路斷線=" + desk.submit(104, 2, "網路斷線"));
        System.out.println("重複id submit 101=" + desk.submit(101, 1, "重複"));
        System.out.println("優先度0 submit=" + desk.submit(105, 0, "違規"));
        System.out.println("空白描述 submit=" + desk.submit(106, 2, "  "));
        System.out.println("findById 103=" + desk.findById(103));
        System.out.println("findById 999=" + desk.findById(999));
        System.out.println("pending=" + desk.pendingCount());
        System.out.println("cancel 102=" + desk.cancel(102));
        System.out.println("重複cancel 102=" + desk.cancel(102));
        System.out.println("cancel後findById 102=" + desk.findById(102));
        System.out.println("pending=" + desk.pendingCount());
        System.out.println("processNext=" + desk.processNext());
        System.out.println("processNext=" + desk.processNext());
        System.out.println("processNext=" + desk.processNext());
        System.out.println("processNext=" + desk.processNext());
        System.out.println("pending=" + desk.pendingCount());
    }
}
