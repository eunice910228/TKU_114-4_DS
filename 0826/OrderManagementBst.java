class MgmtOrder {
    int orderId;
    String customer;
    int amount;
    String status;
    MgmtOrder(int orderId, String customer, int amount) {
        this.orderId = orderId;
        this.customer = customer;
        this.amount = Math.max(0, amount);
        this.status = "NEW";
    }
    @Override
    public String toString() {
        return "#" + orderId + " " + customer + " " + amount + "元 [" + status + "]";
    }
}

class MgmtNode {
    MgmtOrder order;
    MgmtNode left;
    MgmtNode right;
    MgmtNode(MgmtOrder order) {
        this.order = order;
    }
}

public class OrderManagementBst {
    private MgmtNode root;
    boolean add(MgmtOrder order) {
        if (order == null || find(order.orderId) != null) {
            return false;
        }
        root = add(root, order);
        return true;
    }
    private MgmtNode add(MgmtNode node, MgmtOrder order) {
        if (node == null) {
            return new MgmtNode(order);
        }
        if (order.orderId < node.order.orderId) {
            node.left = add(node.left, order);
        } else {
            node.right = add(node.right, order);
        }
        return node;
    }
    MgmtOrder find(int orderId) {
        MgmtNode current = root;
        while (current != null) {
            if (orderId == current.order.orderId) {
                return current.order;
            }
            current = (orderId < current.order.orderId) ? current.left : current.right;
        }
        return null;
    }
    boolean updateStatus(int orderId, String status) {
        MgmtOrder order = find(orderId);
        if (order == null || status == null || status.isBlank()) {
            return false;
        }
        order.status = status;
        return true;
    }
    boolean cancel(int orderId) {
        MgmtOrder order = find(orderId);
        if (order == null || order.status.equals("CANCELLED")) {
            return false;
        }
        order.status = "CANCELLED";
        return true;
    }
    boolean remove(int orderId) {
        MgmtOrder order = find(orderId);
        if (order == null || !order.status.equals("CANCELLED")) {
            return false;
        }
        root = remove(root, orderId);
        return true;
    }
    private MgmtNode remove(MgmtNode node, int orderId) {
        if (node == null) {
            return null;
        }
        if (orderId < node.order.orderId) {
            node.left = remove(node.left, orderId);
        } else if (orderId > node.order.orderId) {
            node.right = remove(node.right, orderId);
        } else {
            if (node.left == null) {
                return node.right;
            }
            if (node.right == null) {
                return node.left;
            }
            MgmtNode successor = node.right;
            while (successor.left != null) {
                successor = successor.left;
            }
            node.order = successor.order;
            node.right = remove(node.right, successor.order.orderId);
        }
        return node;
    }
    void idRangeReport(int low, int high) {
        System.out.println("訂單編號 " + low + " ~ " + high);
        idRangeReport(root, low, high);
    }
    private void idRangeReport(MgmtNode node, int low, int high) {
        if (node == null) {
            return;
        }
        if (node.order.orderId > low) {
            idRangeReport(node.left, low, high);
        }
        if (node.order.orderId >= low && node.order.orderId <= high) {
            System.out.println("  " + node.order);
        }
        if (node.order.orderId < high) {
            idRangeReport(node.right, low, high);
        }
    }
    int totalAmount() {
        return totalAmount(root);
    }
    private int totalAmount(MgmtNode node) {
        if (node == null) {
            return 0;
        }
        int here = node.order.status.equals("CANCELLED") ? 0 : node.order.amount;
        return here + totalAmount(node.left) + totalAmount(node.right);
    }
    public static void main(String[] args) {
        OrderManagementBst system = new OrderManagementBst();
        System.out.println("add 2077=" + system.add(new MgmtOrder(2077, "Eunice", 777)));
        System.out.println("add 7520=" + system.add(new MgmtOrder(7520, "Bsy", 920)));
        System.out.println("add 1378=" + system.add(new MgmtOrder(1378, "Candice", 1080)));
        System.out.println("add 1741=" + system.add(new MgmtOrder(1741, "Doris", 500)));
        System.out.println("add 1742=" + system.add(new MgmtOrder(1742, "Ella", 1600)));
        System.out.println("重複add 2077=" + system.add(new MgmtOrder(2077, "test", 1)));
        System.out.println("負數金額add 5006=" + system.add(new MgmtOrder(5006, "test", -500))
                + "（金額" + system.find(5006).amount + "）");
        System.out.println("updateStatus 7520 SHIPPED=" + system.updateStatus(7520, "SHIPPED"));
        System.out.println("updateStatus 9999=" + system.updateStatus(9999, "SHIPPED"));
        System.out.println("updateStatus 1741空白=" + system.updateStatus(1741, "  "));
        System.out.println("remove 1741（尚未取消）=" + system.remove(1741));
        System.out.println("cancel 1741=" + system.cancel(1741));
        System.out.println("重複cancel 1741=" + system.cancel(1741));
        System.out.println("remove 1741（已取消）=" + system.remove(1741));
        System.out.println("remove 9999=" + system.remove(9999));
        system.idRangeReport(1742, 7520);
        System.out.println("totalAmount（不含 CANCELLED）=" + system.totalAmount());
        System.out.println("cancel 2077（root）=" + system.cancel(2077));
        System.out.println("remove 2077（root 且已取消）=" + system.remove(2077));
        system.idRangeReport(1000, 9999);
        System.out.println("totalAmount=" + system.totalAmount());
    }
}