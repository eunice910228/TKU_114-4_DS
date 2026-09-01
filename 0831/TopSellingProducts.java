import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
class Product {
    String id;
    int sales;
    Product(String id, int sales) {
        this.id = id;
        this.sales = sales;
    }
    @Override
    public String toString() {
        return id + "(" + sales + ")";
    }
}
public class TopSellingProducts {
    private final Map<String, Integer> totals = new HashMap<>();
    boolean record(String id, int sales) {
        if (id == null || id.isBlank() || sales < 0) {
            return false;
        }
        totals.put(id, totals.getOrDefault(id, 0) + sales);
        return true;
    }
    int salesOf(String id) {
        return totals.getOrDefault(id, 0);
    }
    List<Product> topK(int k) {
        List<Product> result = new ArrayList<>();
        if (k <= 0) {
            return result;
        }
        PriorityQueue<Product> queue = new PriorityQueue<>(
                Comparator.comparingInt((Product p) -> -p.sales)
                        .thenComparing(p -> p.id));
        for (Map.Entry<String, Integer> entry : totals.entrySet()) {
            queue.offer(new Product(entry.getKey(), entry.getValue()));
        }
        while (!queue.isEmpty() && result.size() < k) {
            result.add(queue.poll());
        }
        return result;
    }
    public static void main(String[] args) {
        TopSellingProducts shop = new TopSellingProducts();
        System.out.println("A01+120=" + shop.record("A01", 120));
        System.out.println("A02+300=" + shop.record("A02", 300));
        System.out.println("A03+300=" + shop.record("A03", 300));
        System.out.println("A04+90=" + shop.record("A04", 90));
        System.out.println("A01+80=" + shop.record("A01", 80));
        System.out.println("A05+250=" + shop.record("A05", 250));
        System.out.println("A06負數銷量=" + shop.record("A06", -10));
        System.out.println("A01合併後銷量=" + shop.salesOf("A01"));
        System.out.println("A77查無商品=" + shop.salesOf("A77"));
        System.out.println("top3=" + shop.topK(3));
        System.out.println("top10=" + shop.topK(10));
        System.out.println("top0=" + shop.topK(0));
        System.out.println("topK負數=" + shop.topK(-3));
        System.out.println("空商品表topK=" + new TopSellingProducts().topK(3));
    }
}
