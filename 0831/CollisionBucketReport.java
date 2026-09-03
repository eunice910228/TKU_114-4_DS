import java.util.ArrayList;
import java.util.List;
public class CollisionBucketReport {
    static void report(int[] keys, int bucketCount) {
        System.out.println("bucket count=" + bucketCount);
        if (keys == null || bucketCount < 1) {
            System.out.println("輸入無效");
            return;
        }
        List<List<Integer>> buckets = new ArrayList<>();
        for (int i = 0; i < bucketCount; i++) {
            buckets.add(new ArrayList<>());
        }
        for (int key : keys) {
            List<Integer> bucket = buckets.get(Math.floorMod(key, bucketCount));
            if (!bucket.contains(key)) {
                bucket.add(key);
            }
        }
        int collisions = 0;
        int longest = 0;
        for (int i = 0; i < bucketCount; i++) {
            List<Integer> bucket = buckets.get(i);
            System.out.println("bucket" + i + "=" + bucket);
            if (bucket.size() > 1) {
                collisions += bucket.size() - 1;
            }
            longest = Math.max(longest, bucket.size());
        }
        System.out.println("collision=" + collisions + " 最長chain=" + longest);
    }
    public static void main(String[] args) {
        report(new int[]{ 10, 14, 7, 3, 18, -6, 14, 22 }, 4);
        report(new int[]{ 10, 14, 7, 3, 18, -6, 14, 22 }, 8);
        report(new int[0], 4);
        report(null, 4);
        report(new int[]{ 1, 2 }, 0);
    }
}
