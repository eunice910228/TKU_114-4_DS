import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
public class LowestKPriceTracker {
    static List<Integer> lowestK(List<Integer> prices, int k) {
        List<Integer> result = new ArrayList<>();
        if (prices == null || k <= 0) {
            return result;
        }
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
        for (Integer price : prices) {
            if (price == null || price < 0) {
                continue;
            }
            maxHeap.offer(price);
            if (maxHeap.size() > k) {
                maxHeap.poll();
            }
        }
        while (!maxHeap.isEmpty()) {
            result.add(maxHeap.poll());
        }
        Collections.reverse(result);
        return result;
    }
    public static void main(String[] args) {
        List<Integer> prices = new ArrayList<>(List.of(450, 120, 890, 75, 300, 120));
        prices.add(null);
        prices.add(-50);
        System.out.println("prices=" + prices);
        System.out.println("lowest3=" + lowestK(prices, 3));
        System.out.println("lowest1=" + lowestK(prices, 1));
        System.out.println("lowest10=" + lowestK(prices, 10));
        System.out.println("k=0=" + lowestK(prices, 0));
        System.out.println("k負數=" + lowestK(prices, -2));
        System.out.println("null輸入=" + lowestK(null, 3));
        System.out.println("空list=" + lowestK(new ArrayList<>(), 3));
    }
}
