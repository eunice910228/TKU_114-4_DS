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
    }
}
