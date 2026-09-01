import java.util.ArrayList;
import java.util.List;
public class StudentIdHashAnalysis {
    private final String[] ids;
    StudentIdHashAnalysis(String[] ids) {
        if (ids == null) {
            throw new IllegalArgumentException("ids 不可為 null");
        }
        this.ids = ids;
    }
    private List<List<String>> distribute(int bucketCount) {
        if (bucketCount < 1) {
            throw new IllegalArgumentException("bucketCount 至少為 1");
        }
        List<List<String>> buckets = new ArrayList<>();
        for (int i = 0; i < bucketCount; i++) {
            buckets.add(new ArrayList<>());
        }
        for (String id : ids) {
            if (id == null) {
                continue;
            }
            buckets.get(Math.floorMod(id.hashCode(), bucketCount)).add(id);
        }
        return buckets;
    }
    private int collisions(List<List<String>> buckets) {
        int total = 0;
        for (List<String> bucket : buckets) {
            if (bucket.size() > 1) {
                total = total + bucket.size() - 1;
            }
        }
        return total;
    }
    private int maxChain(List<List<String>> buckets) {
        int max = 0;
        for (List<String> bucket : buckets) {
            if (bucket.size() > max) {
                max = bucket.size();
            }
        }
        return max;
    }
    int collisions(int bucketCount) {
        return collisions(distribute(bucketCount));
    }
    int maxChain(int bucketCount) {
        return maxChain(distribute(bucketCount));
    }
    double averageChain(int bucketCount) {
        return (double) ids.length / bucketCount;
    }
    void report(int bucketCount) {
        System.out.println("bucket count=" + bucketCount);
        List<List<String>> buckets = distribute(bucketCount);
        for (int i = 0; i < buckets.size(); i++) {
            System.out.println("bucket" + i + " 共" + buckets.get(i).size()
                    + "筆 " + buckets.get(i));
        }
        System.out.println("collision總數=" + collisions(buckets));
        System.out.println("最大chain=" + maxChain(buckets));
        System.out.println("平均chain=" + averageChain(bucketCount));
    }
    public static void main(String[] args) {
        String[] ids = { "A01", "A02", "A03", "A04", "A05",
                "B01", "B02", "B03", "C01", "C02" };
        StudentIdHashAnalysis analysis = new StudentIdHashAnalysis(ids);
        analysis.report(4);
        analysis.report(8);
        StudentIdHashAnalysis emptyCase = new StudentIdHashAnalysis(new String[0]);
        System.out.println("空學號表 collision=" + emptyCase.collisions(4)
                + "，最大chain=" + emptyCase.maxChain(4));
        StudentIdHashAnalysis withNull = new StudentIdHashAnalysis(new String[]{ "A01", null, "A02" });
        System.out.println("含null元素 最大chain=" + withNull.maxChain(4));
        boolean threw = false;
        try {
            new StudentIdHashAnalysis(null);
        } catch (IllegalArgumentException e) {
            threw = true;
        }
        System.out.println("ids為null 丟例外=" + threw);
        System.out.println("比較：bucket 4 的 collision=" + analysis.collisions(4)
                + "，bucket 8 的 collision=" + analysis.collisions(8));
    }
}