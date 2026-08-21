interface PricingPolicy {
    int finalPrice(int originalPrice);
    String policyName();
}

class StandardPricing implements PricingPolicy {
    @Override
    public int finalPrice(int originalPrice) {
        return Math.max(0, originalPrice);
    }

    @Override
    public String policyName() { return "原價"; }
}

class VipPricing implements PricingPolicy {
    @Override
    public int finalPrice(int originalPrice) {
        long price = Math.max(0, originalPrice);
        return (int) (price * 85 / 100);
    }

    @Override
    public String policyName() { return "VIP 85折"; }
}

class ThresholdDiscountPricing implements PricingPolicy {
    private static final int THRESHOLD = 2000;
    private static final int DISCOUNT = 300;

    @Override
    public int finalPrice(int originalPrice) {
        int price = Math.max(0, originalPrice);
        return price >= THRESHOLD ? Math.max(0, price - DISCOUNT) : price;
    }

    @Override
    public String policyName() { return "滿2000折300"; }
}

interface NotificationChannel {
    boolean send(String receiver, String message);
    String channelName();
}

abstract class AbstractNotificationChannel implements NotificationChannel {

    @Override
    public final boolean send(String receiver, String message) {
        if (!isValidReceiver(receiver)) {
            return false;
        }
        String text = (message == null) ? "" : message;
        System.out.printf("    %-7s %s -> %s%n", channelName().toUpperCase(), receiver, text);
        return true;
    }

    protected abstract boolean isValidReceiver(String receiver);
}

class EmailChannel extends AbstractNotificationChannel {
    @Override
    protected boolean isValidReceiver(String receiver) {
        if (receiver == null || receiver.isBlank()) {
            return false;
        }
        int at = receiver.indexOf('@');
        return at > 0 && at < receiver.length() - 1;
    }

    @Override
    public String channelName() { return "Email"; }
}

class SmsChannel extends AbstractNotificationChannel {
    @Override
    protected boolean isValidReceiver(String receiver) {
        return isMobileNumber(receiver);
    }

    @Override
    public String channelName() { return "SMS"; }

    static boolean isMobileNumber(String number) {
        if (number == null || number.length() != 10) {
            return false;
        }
        if (!number.startsWith("09")) {
            return false;
        }
        for (char c : number.toCharArray()) {
            if (c < '0' || c > '9') {
                return false;
            }
        }
        return true;
    }
}

class ConsoleChannel extends AbstractNotificationChannel {
    @Override
    protected boolean isValidReceiver(String receiver) {
        return receiver != null && !receiver.isBlank();
    }

    @Override
    public String channelName() { return "Console"; }
}

class CheckoutResult {
    private final String orderId;
    private final int originalPrice;
    private final int finalPrice;
    private final boolean notified;
    private final String note;

    CheckoutResult(String orderId, int originalPrice, int finalPrice,
                   boolean notified, String note) {
        this.orderId = (orderId == null || orderId.isBlank()) ? "UNKNOWN" : orderId;
        this.originalPrice = Math.max(0, originalPrice);
        this.finalPrice = Math.max(0, finalPrice);
        this.notified = notified;
        this.note = (note == null) ? "" : note;
    }

    String getOrderId()   { return orderId; }
    int getOriginalPrice(){ return originalPrice; }
    int getFinalPrice()   { return finalPrice; }
    boolean isNotified()  { return notified; }
    int savedAmount()     { return originalPrice - finalPrice; }

    @Override
    public String toString() {
        return String.format("%-7s 原價=%,7d  實付=%,7d  折抵=%,6d  通知=%s%s",
                orderId, originalPrice, finalPrice, savedAmount(),
                notified ? "成功" : "失敗",
                note.isEmpty() ? "" : "  " + note);
    }
}

class CheckoutService {
    private final PricingPolicy pricing;
    private final NotificationChannel channel;

    CheckoutService(PricingPolicy pricing, NotificationChannel channel) {
        this.pricing = (pricing == null) ? new StandardPricing() : pricing;
        this.channel = (channel == null) ? new ConsoleChannel() : channel;
    }

    String comboName() {
        return pricing.policyName() + " + " + channel.channelName();
    }

    CheckoutResult checkout(String orderId, int originalPrice, String receiver) {
        String id = (orderId == null || orderId.isBlank()) ? "UNKNOWN" : orderId;
        if (originalPrice < 0) {
            return new CheckoutResult(id, 0, 0, false, "金額不合法");
        }
        int amount = pricing.finalPrice(originalPrice);
        boolean ok = channel.send(receiver, "訂單號碼=" + id + ", 金額=" + amount);
        return new CheckoutResult(id, originalPrice, amount, ok,
                ok ? "" : "通知失敗（收件資訊不合法）");
    }
}

public class FlexibleCheckoutSystem {
    public static void main(String[] args) {
        PricingPolicy[] policies = {
            new StandardPricing(), new VipPricing(), new ThresholdDiscountPricing()
        };
        NotificationChannel[] channels = {
            new EmailChannel(), new SmsChannel(), new ConsoleChannel()
        };
        String[] receivers = { "Eunice@123.com", "0912345678", "counter-01" };

        System.out.println("=== 3 種計價 × 3 種管道 = 9 種組合 ===");
        int orderNo = 1000;
        for (int p = 0; p < policies.length; p++) {
            for (int c = 0; c < channels.length; c++) {
                CheckoutService service = new CheckoutService(policies[p], channels[c]);
                System.out.println("  [" + service.comboName() + "]");
                CheckoutResult r = service.checkout("O" + (++orderNo), 2400, receivers[c]);
                System.out.println("    " + r);
            }
        }

        System.out.println();
        System.out.println("=== 同一組合、不同金額 ===");
        CheckoutService thresholdConsole =
                new CheckoutService(new ThresholdDiscountPricing(), new ConsoleChannel());
        for (int price : new int[]{ 1999, 2000, 2001, 5000 }) {
            System.out.println("    " + thresholdConsole.checkout("O" + (++orderNo), price, "counter-01"));
        }

        System.out.println();
        System.out.println("=== 測試通知失敗 ===");
        CheckoutService vipEmail = new CheckoutService(new VipPricing(), new EmailChannel());
        System.out.println("    " + vipEmail.checkout("O2001", 2000, "not-an-email"));
        System.out.println("    " + vipEmail.checkout("O2002", 2000, null));

        System.out.println();
        System.out.println("=== 金額邊界測試 ===");
        System.out.println("    " + vipEmail.checkout(null, -500, "eunice@123.com"));
        System.out.println("    " + vipEmail.checkout("O2003", 0, "eunice@123.com"));
        CheckoutService vipConsole = new CheckoutService(new VipPricing(), new ConsoleChannel());
        System.out.println("    " + vipConsole.checkout("O2004", 100_000_000, "counter-01"));
        System.out.println("    " + vipConsole.checkout("O2005", Integer.MAX_VALUE, "counter-01"));

        System.out.println();
        System.out.println("=== 收件資訊邊界測試 ===");
        String[] bad = { null, "", "   ", "abc", "@", "a@", "@b", "091234567", "0812345678" };
        for (NotificationChannel c : channels) {
            StringBuilder sb = new StringBuilder();
            for (String r : bad) {
                if (!c.send(r, "test")) {
                    sb.append(r == null ? "null" : "\"" + r + "\"").append(' ');
                }
            }
            System.out.printf("    %-7s 拒絕：%s%n", c.channelName(), sb.toString().trim());
        }

        System.out.println();
        System.out.println("=== 傳 null 時的預設組合 ===");
        CheckoutService fallback = new CheckoutService(null, null);
        System.out.println("  [" + fallback.comboName() + "]");
        System.out.println("    " + fallback.checkout("O2006", 1200, "counter-01"));
    }
}