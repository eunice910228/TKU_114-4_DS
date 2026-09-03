import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
public class Q01_PriorityRecord {
    public record Job(String id, int priority, long sequence) {}
    public static List<String> processOrder(List<Job> jobs) {
        List<String> result = new ArrayList<>();
        if (jobs == null) {
            return result;
        }
        PriorityQueue<Job> queue = new PriorityQueue<>(
                Comparator.comparingInt(Job::priority)
                        .thenComparingLong(Job::sequence)
                        .thenComparing(Job::id));
        for (Job job : jobs) {
            if (job != null) {
                queue.offer(job);
            }
        }
        while (!queue.isEmpty()) {
            result.add(queue.poll().id());
        }
        return result;
    }
    public static void main(String[] args) {
        List<Job> jobs = new ArrayList<>();
        jobs.add(new Job("J3", 2, 5));
        jobs.add(new Job("J1", 1, 9));
        jobs.add(null);
        jobs.add(new Job("J4", 2, 5));
        jobs.add(new Job("J2", 1, 3));
        List<String> order = processOrder(jobs);
        System.out.println("order=" + order);
        System.out.println("輸入未被修改=" + (jobs.size() == 5));
        System.out.println("null輸入=" + processOrder(null));
        System.out.println("empty輸入=" + processOrder(new ArrayList<>()));
        List<String> first = processOrder(jobs);
        first.add("X");
        System.out.println("結果獨立性=" + processOrder(jobs));
    }
}
