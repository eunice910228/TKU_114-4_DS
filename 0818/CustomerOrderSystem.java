    //宣告顧客
class Customer {
    private final String id;
    private final String name;
    Customer(String id, String name) {
        this.id = (id == null || id.isBlank()) ? "UNKNOWN" : id.trim();
        this.name = (name == null || name.isBlank()) ? "Unknown" : name.trim();
    }
    String label() {
        return id + " " + name;
    }
}
    //宣告訂單項目
class OrderItem {
    private final String productName;
    private final int unitPrice;
    private final int quantity;
    OrderItem(String productName, int unitPrice, int quantity) {
        this.productName = (productName == null || productName.isBlank())
                ? "Unknown" : productName.trim();
        this.unitPrice = Math.max(0, unitPrice);
        this.quantity = Math.max(0, quantity);
    }
    int subtotal() {
        return unitPrice * quantity;
    }
    int getQuantity() {
        return quantity;
    }
    @Override
    public String toString() {
        return "  " + productName + " x" + quantity
                + " @" + unitPrice + " = " + subtotal();
    }
}
    //宣告顧客訂單
class CustomerOrder {
    private final String orderId;
    private final Customer customer;
    private final OrderItem[] items;
    private int itemCount;
    CustomerOrder(String orderId, Customer customer, int capacity) {
        this.orderId = (orderId == null || orderId.isBlank())
                ? "UNKNOWN" : orderId.trim();
        this.customer = customer;
        this.items = new OrderItem[Math.max(1, capacity)];
        this.itemCount = 0;
    }
    boolean addItem(OrderItem item) {
        if (item == null || itemCount >= items.length) {
            return false;
        }
        items[itemCount] = item;
        itemCount++;
        return true;
    }
    int totalAmount() {
        int total = 0;
        for (int i = 0; i < itemCount; i++) {
            total += items[i].subtotal();
        }
        return total;
    }
    int totalQuantity() {
        int total = 0;
        for (int i = 0; i < itemCount; i++) {
            total += items[i].getQuantity();
        }
        return total;
    }
    void printSummary() {
        String who = (customer == null) ? "未指定顧客" : customer.label();
        System.out.println("訂單 " + orderId + " | 顧客：" + who);
        for (int i = 0; i < itemCount; i++) {
            System.out.println(items[i]);
        }
        System.out.println("  品項種類：" + itemCount
                + "，商品件數：" + totalQuantity()
                + "，總金額：" + totalAmount());
    }
}
    //測試訂單系統
public class CustomerOrderSystem {
    public static void main(String[] args) {
        Customer Eunice = new Customer("B01", "Eunice");

        CustomerOrder order = new CustomerOrder("AB1215", Eunice, 3);
        System.out.println("加入品項 1：" + order.addItem(new OrderItem("Mango", 120, 2)));
        System.out.println("加入品項 2：" + order.addItem(new OrderItem("Blueberry", 215, 1)));
        System.out.println("加入品項 3：" + order.addItem(new OrderItem("Banana", 80, 3)));
        System.out.println("加入品項 4（超出容量）：" + order.addItem(new OrderItem("Passion fruit", 200, 1)));
        System.out.println();
        order.printSummary();
        System.out.println();
        CustomerOrder second = new CustomerOrder("AC0819", Eunice, 2);
        second.addItem(new OrderItem("pear", 100, 1));
        second.printSummary();
    }
}
