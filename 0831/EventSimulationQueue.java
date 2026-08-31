import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
class SimEvent {
    int time;
    String type;
    int sequence;
    SimEvent(int time, String type, int sequence) {
        this.time = time;
        this.type = type;
        this.sequence = sequence;
    }
    @Override
    public String toString() {
        return "t=" + time + " " + type;
    }
}
public class EventSimulationQueue {
    private final PriorityQueue<SimEvent> events = new PriorityQueue<>(
            Comparator.comparingInt((SimEvent e) -> e.time)
                    .thenComparingInt(e -> e.sequence));
    private final List<Integer> cancelled = new ArrayList<>();
    private int sequence;
    int schedule(int time, String type) {
        if (type == null || type.isBlank() || time < 0) {
            return -1;
        }
        events.offer(new SimEvent(time, type, sequence));
        sequence++;
        return sequence - 1;
    }
    public static void main(String[] args) {
        EventSimulationQueue sim = new EventSimulationQueue();
        int e0 = sim.schedule(30, "開幕");
        int e1 = sim.schedule(10, "報到");
        int e2 = sim.schedule(20, "彩排");
        int e3 = sim.schedule(20, "音控測試");
        int e4 = sim.schedule(50, "散場");
        System.out.println("排入事件編號=" + e0 + "," + e1 + "," + e2 + "," + e3 + "," + e4);
        System.out.println("負數時間 schedule=" + sim.schedule(-5, "錯誤事件"));
    }
}
