import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
class StoreProduct implements Comparable<StoreProduct> {
    private final String id;
    private final String name;
    private final int price;
    private final int stock;
    StoreProduct(String id, String name, int price, int stock) {
        this.id = (id == null || id.isBlank()) ? "UNKNOWN" : id;
        this.name = (name == null || name.isBlank()) ? "未命名" : name;
        this.price = Math.max(0, price);
        this.stock = Math.max(0, stock);
    }
    String getId() {
        return id;
    }
    String getName() {
        return name;
    }
    int getPrice() {
        return price;
    }
    int getStock() {
        return stock;
    }
    @Override
    public int compareTo(StoreProduct other) {
        return this.id.compareTo(other.id);
    }
    @Override
    public String toString() {
        return id + " " + name + " 價格=" + price + " 庫存=" + stock;
    }
}
class PriceThenNameComparator implements Comparator<StoreProduct> {
    @Override
    public int compare(StoreProduct a, StoreProduct b) {
        if (a.getPrice() != b.getPrice()) {
            return Integer.compare(a.getPrice(), b.getPrice());
        }
        return a.getName().compareTo(b.getName());
    }
}
public class ProductComparatorPractice {
    static void print(String title, List<StoreProduct> list) {
        System.out.println(title);
        for (StoreProduct p : list) {
            System.out.println(p);
        }
    }
    public static void main(String[] args) {
        List<StoreProduct> products = new ArrayList<>(List.of(
            new StoreProduct("P003", "無線滑鼠", 790, 12),
            new StoreProduct("P001", "機械鍵盤", 2490, 5),
            new StoreProduct("P005", "螢幕支架", 790, 5),
            new StoreProduct("P002", "USB集線器", 450, 30),
            new StoreProduct("P004", "耳機架", 450, 12)
        ));
        print("原始順序", products);
        List<StoreProduct> byId = new ArrayList<>(products);
        byId.sort(null);
        print("natural order依id升冪", byId);
        List<StoreProduct> byPrice = new ArrayList<>(products);
        Comparator<StoreProduct> priceThenName = Comparator.comparingInt(StoreProduct::getPrice)
                .thenComparing(StoreProduct::getName);
        byPrice.sort(priceThenName);
        print("依price升冪同價依name", byPrice);
        List<StoreProduct> byStock = new ArrayList<>(products);
        Comparator<StoreProduct> stockDescThenId = Comparator.comparingInt(StoreProduct::getStock).reversed()
                .thenComparing(StoreProduct::getId);
        byStock.sort(stockDescThenId);
        print("依stock降冪同庫存依id", byStock);
        print("原始list仍維持輸入順序", products);
        List<StoreProduct> manual = new ArrayList<>(products);
        manual.sort(new PriceThenNameComparator());
        System.out.println("手寫Comparator結果與第2組相同=" + manual.toString().equals(byPrice.toString()));
        List<StoreProduct> edge = new ArrayList<>(List.of(
            new StoreProduct(null, null, -100, -5),
            new StoreProduct("P006", "特價品", 0, 0)
        ));
        edge.sort(null);
        print("邊界測試null與負數", edge);
        List<StoreProduct> empty = new ArrayList<>();
        empty.sort(priceThenName);
        System.out.println("空list排序size=" + empty.size());
        List<StoreProduct> single = new ArrayList<>(List.of(products.get(0)));
        single.sort(stockDescThenId);
        System.out.println("單筆list排序size=" + single.size());
    }
}
