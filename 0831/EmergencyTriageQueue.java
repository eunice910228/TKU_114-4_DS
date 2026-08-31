import java.util.Comparator;
import java.util.PriorityQueue;
class Patient {
    String chartNo;
    int level;
    long arrival;
    Patient(String chartNo, int level, long arrival) {
        this.chartNo = chartNo;
        this.level = level;
        this.arrival = arrival;
    }
    @Override
    public String toString() {
        return chartNo + " 危急度" + level;
    }
}
public class EmergencyTriageQueue {
    private final PriorityQueue<Patient> queue = new PriorityQueue<>(
            Comparator.comparingInt((Patient p) -> p.level)
                    .thenComparingLong(p -> p.arrival)
                    .thenComparing(p -> p.chartNo));
    private long sequence;
    boolean checkIn(String chartNo, int level) {
        if (chartNo == null || chartNo.isBlank() || level < 1 || level > 5) {
            return false;
        }
        queue.offer(new Patient(chartNo, level, sequence));
        sequence++;
        return true;
    }
    Patient peekNext() {
        return queue.peek();
    }
    Patient callNext() {
        return queue.poll();
    }
    int waitingCount() {
        return queue.size();
    }
    public static void main(String[] args) {
        EmergencyTriageQueue triage = new EmergencyTriageQueue();
        System.out.println("報到P01 危急度3=" + triage.checkIn("P01", 3));
        System.out.println("報到P02 危急度1=" + triage.checkIn("P02", 1));
        System.out.println("報到P03 危急度3=" + triage.checkIn("P03", 3));
        System.out.println("報到P04 危急度2=" + triage.checkIn("P04", 2));
        System.out.println("報到P05 危急度1=" + triage.checkIn("P05", 1));
        System.out.println("危急度0 報到=" + triage.checkIn("P06", 0));
        System.out.println("危急度6 報到=" + triage.checkIn("P07", 6));
        System.out.println("空白病歷號 報到=" + triage.checkIn("  ", 3));
        System.out.println("目前人數=" + triage.waitingCount());
        System.out.println("下一位=" + triage.peekNext());
        System.out.println("叫號順序");
        while (triage.waitingCount() > 0) {
            System.out.println(triage.callNext());
        }
        System.out.println("空佇列 peek=" + triage.peekNext());
        System.out.println("空佇列 call=" + triage.callNext());
        System.out.println("目前人數=" + triage.waitingCount());
    }
}