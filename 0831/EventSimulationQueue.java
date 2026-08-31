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
    boolean cancel(int eventSequence) {
        if (eventSequence < 0 || eventSequence >= sequence) {
            return false;
        }
        if (cancelled.contains(eventSequence)) {
            return false;
        }
        cancelled.add(eventSequence);
        return true;
    }
    void run() {
        System.out.println("執行紀錄");
        while (!events.isEmpty()) {
            SimEvent event = events.poll();
            if (cancelled.contains(event.sequence)) {
                System.out.println("跳過已取消 " + event);
                continue;
            }
            System.out.println("執行 " + event);
        }
    }
    public static void main(String[] args) {
        EventSimulationQueue sim = new EventSimulationQueue();
        int e0 = sim.schedule(45, "資料庫備份");
        int e1 = sim.schedule(5, "病毒掃描");
        int e2 = sim.schedule(20, "憑證更新");
        int e3 = sim.schedule(45, "日誌清理");
        int e4 = sim.schedule(90, "主機重啟");
        System.out.println("排入事件編號=" + e0 + "," + e1 + "," + e2 + "," + e3 + "," + e4);
        System.out.println("負數時間 schedule=" + sim.schedule(-5, "錯誤排程"));
        System.out.println("空白類型 schedule=" + sim.schedule(10, "  "));
        System.out.println("cancel 事件" + e3 + "=" + sim.cancel(e3));
        System.out.println("重複 cancel 事件" + e3 + "=" + sim.cancel(e3));
        System.out.println("cancel 不存在事件99=" + sim.cancel(99));
        sim.run();
    }
}