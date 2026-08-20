interface DeliveryMethod {
    // 依訂單金額與重量計算運費
    int shippingFee(int orderAmount, double weightKg);
    // 預估到貨說明
    String estimate();
    // 配送方式名稱
    String methodName();
}

    // 宅配：基本運費 + 超重加價；滿額免運
class HomeDelivery implements DeliveryMethod {
    private static final int BASE_FEE = 120;
    private static final int FREE_THRESHOLD = 1500;
    @Override
    public int shippingFee(int orderAmount, double weightKg) {
        int amount = Math.max(0, orderAmount);
        double kg = Math.max(0.0, weightKg);
        if (amount >= FREE_THRESHOLD) {
            return 0;
        }
        int fee = BASE_FEE;
        if (kg > 5.0) {  
            fee = fee + (int) Math.ceil(kg - 5.0) * 30;
        }
        return fee;
    }
    @Override
    public String estimate() {
        return "預計 2～3 個工作天送達";
    }
    @Override
    public String methodName() {
        return "宅配";
    }
}

    // 超商取貨：固定運費，但有重量與尺寸上限
class ConvenienceStorePickup implements DeliveryMethod {
    private static final int FLAT_FEE = 60;
    private static final double WEIGHT_LIMIT = 5.0;
    @Override
    public int shippingFee(int orderAmount, double weightKg) {
        double kg = Math.max(0.0, weightKg);
        if (kg > WEIGHT_LIMIT) {
            return -1; 
        }
        return Math.max(0, orderAmount) >= 1000 ? 0 : FLAT_FEE;
    }
    @Override
    public String estimate() {
        return "預計 3～5 個工作天到貨";
    }
    @Override
    public String methodName() {
        return "超商取貨";
    }
}

    // 門市自取：不收運費
class StorePickup implements DeliveryMethod {
    @Override
    public int shippingFee(int orderAmount, double weightKg) {
        return 0;
    }
    @Override
    public String estimate() {
        return "備貨完成後當日可取";
    }
    @Override
    public String methodName() {
        return "門市自取";
    }
}

class OrderService {
    private DeliveryMethod delivery;
    OrderService(DeliveryMethod delivery) {
        this.delivery = (delivery == null) ? new StorePickup() : delivery;
    }
    void changeDelivery(DeliveryMethod newDelivery) {
        if (newDelivery != null) {
            this.delivery = newDelivery;
        }
    }
    void placeOrder(String orderId, int orderAmount, double weightKg) {
        String id = (orderId == null || orderId.isBlank()) ? "UNKNOWN" : orderId;
        int amount = Math.max(0, orderAmount);
        double kg = Math.max(0.0, weightKg);
        int fee = delivery.shippingFee(amount, kg);
        System.out.println("訂單 " + id + "（" + delivery.methodName() + "）");
        System.out.printf("  商品金額 %d 元，重量 %.1f 公斤%n", amount, kg);
        if (fee < 0) {
            System.out.println("  無法使用此配送方式：超過重量上限，請改選宅配");
            return;
        }
        System.out.println("  運費 " + (fee == 0 ? "0 元（免運）" : fee + " 元"));
        System.out.println("  應付總額 " + (amount + fee) + " 元");
        System.out.println("  " + delivery.estimate());
    }
}

public class DeliveryStrategySystem {
    public static void main(String[] args) {
        System.out.println("=== 同一份訂單，三種配送方式 ===");
        DeliveryMethod[] methods = {
            new HomeDelivery(), new ConvenienceStorePickup(), new StorePickup()
        };
        for (DeliveryMethod m : methods) {
            new OrderService(m).placeOrder("A1150819001", 800, 2.0);
            System.out.println();
        }
        System.out.println("=== 滿額免運 ===");
        new OrderService(new HomeDelivery()).placeOrder("A1150819002", 1600, 3.0);
        System.out.println();
        System.out.println("=== 重物：宅配加價，超商拒收 ===");
        new OrderService(new HomeDelivery()).placeOrder("A1150819003", 900, 8.5);
        System.out.println();
        new OrderService(new ConvenienceStorePickup()).placeOrder("A1150819004", 900, 8.5);
        System.out.println();
        System.out.println("=== 更改配送方式 ===");
        OrderService service = new OrderService(new ConvenienceStorePickup());
        service.placeOrder("A1150819004", 500, 6.0);
        service.changeDelivery(new HomeDelivery());
        service.placeOrder("A1150819004", 500, 6.0);
        System.out.println();
    }
}
