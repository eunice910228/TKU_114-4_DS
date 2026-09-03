import java.util.Arrays;
final class InventorySnapshot {
    private final String warehouseId;
    private final int[] quantities;
    InventorySnapshot(String warehouseId, int[] quantities) {
        this.warehouseId = (warehouseId == null || warehouseId.isBlank()) ? "UNKNOWN" : warehouseId.trim();
        this.quantities = (quantities == null) ? new int[0] : Arrays.copyOf(quantities, quantities.length);
    }
    int[] getQuantities() {
        return Arrays.copyOf(quantities, quantities.length);
    }
    int totalQuantity() {
        int total = 0;
        for (int q : quantities) {
            total += q;
        }
        return total;
    }
    int outOfStockCount() {
        int count = 0;
        for (int q : quantities) {
            if (q == 0) {
                count++;
            }
        }
        return count;
    }
    @Override
    public String toString() {
        return warehouseId + " " + Arrays.toString(quantities);
    }
}
public class InventorySnapshotPractice {
    public static void main(String[] args) {
        int[] source = { 5, 0, 3, 0 };
        InventorySnapshot snapshot = new InventorySnapshot("W01", source);
        System.out.println(snapshot);
        System.out.println("總數量=" + snapshot.totalQuantity());
        System.out.println("缺貨品項=" + snapshot.outOfStockCount());
        source[0] = 999;
        int[] received = snapshot.getQuantities();
        received[2] = 999;
        System.out.println("改原始陣列後source=" + Arrays.toString(source));
        System.out.println("改getter結果後received=" + Arrays.toString(received));
        System.out.println("快照內部不變=" + snapshot);
        System.out.println("總數量仍為8=" + (snapshot.totalQuantity() == 8));
        InventorySnapshot empty = new InventorySnapshot(null, null);
        System.out.println("null輸入=" + empty);
        System.out.println("總數量=" + empty.totalQuantity() + "，缺貨品項=" + empty.outOfStockCount());
    }
}
