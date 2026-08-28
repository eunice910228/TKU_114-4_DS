class Order {
    int orderId;
    String customer;
    int amount;
    boolean cancelled;
    Order(int orderId, String customer, int amount) {
        this.orderId = orderId;
        this.customer = customer;
        this.amount = Math.max(0, amount);
    }
    @Override
    public String toString() {
        return "#" + orderId + " " + customer + " " + amount + "元"
                + (cancelled ? " [已取消]" : "");
    }
}
class OrderNode {
    Order order;
    OrderNode left;
    OrderNode right;
    OrderNode(Order order) {
        this.order = order;
    }
}
public class OrderBstSystem {
    private OrderNode root;
    boolean add(Order order) {
        if (order == null || find(order.orderId) != null) {
            return false;
        }
        root = add(root, order);
        return true;
    }
    private OrderNode add(OrderNode node, Order order) {
        if (node == null) {
            return new OrderNode(order);
        }
        if (order.orderId < node.order.orderId) {
            node.left = add(node.left, order);
        } else {
            node.right = add(node.right, order);
        }
        return node;
    }
    Order find(int orderId) {
        OrderNode current = root;
        while (current != null) {
            if (orderId == current.order.orderId) {
                return current.order;
            }
            current = (orderId < current.order.orderId) ? current.left : current.right;
        }
        return null;
    }
    boolean cancel(int orderId) {
        Order order = find(orderId);
        if (order == null || order.cancelled) {
            return false;
        }
        order.cancelled = true;
        return true;
    }
    boolean updateAmount(int orderId, int amount) {
        Order order = find(orderId);
        if (order == null || order.cancelled || amount < 0) {
            return false;
        }
        order.amount = amount;
        return true;
    }
    void rangeReport(int low, int high) {
        System.out.println("訂單編號 " + low + "~" + high);
        rangeReport(root, low, high);
    }
    private void rangeReport(OrderNode node, int low, int high) {
        if (node == null) {
            return;
        }
        if (node.order.orderId > low) {
            rangeReport(node.left, low, high);
        }
        if (node.order.orderId >= low && node.order.orderId <= high) {
            System.out.println("  " + node.order);
        }
        if (node.order.orderId < high) {
            rangeReport(node.right, low, high);
        }
    }
    void summary() {
        int[] result = new int[3];
        summary(root, result);
        System.out.println("summary：共 " + result[0] + " 筆，有效金額 "
                + result[1] + " 元，已取消 " + result[2] + " 筆");
    }
    private void summary(OrderNode node, int[] result) {
        if (node == null) {
            return;
        }
        summary(node.left, result);
        result[0] = result[0] + 1;
        if (node.order.cancelled) {
            result[2] = result[2] + 1;
        } else {
            result[1] = result[1] + node.order.amount;
        }
        summary(node.right, result);
    }
    public static void main(String[] args) {
        OrderBstSystem system = new OrderBstSystem();
        System.out.println("add 2077=" + system.add(new Order(2077, "Eunice", 777)));
        System.out.println("add 7520=" + system.add(new Order(7520, "Bsy", 920)));
        System.out.println("add 1378=" + system.add(new Order(1378, "Candice", 1080)));
        System.out.println("add 1741=" + system.add(new Order(1741, "Doris", 500)));
        System.out.println("add 1742=" + system.add(new Order(1742, "Ella", 1600)));
        System.out.println("test add 7520=" + system.add(new Order(7520, "test", 1)));
        System.out.println("find 2077=" + system.find(2077));
        System.out.println("find 1999=" + system.find(1999));
        System.out.println("cancel 1741=" + system.cancel(1741));
        System.out.println("test cancel 1741=" + system.cancel(1741));
        System.out.println("updateAmount 7520 改 7777=" + system.updateAmount(7520, 7777));
        System.out.println("updateAmount 1741 已取消=" + system.updateAmount(1741, 999));
        System.out.println("updateAmount 2077 負數=" + system.updateAmount(2077, -100));
        system.rangeReport(1741, 1742);
        system.summary();
    }
}
