class RangeNode {
    int value;
    RangeNode left;
    RangeNode right;
    RangeNode(int value) {
        this.value = value;
    }
}
public class BstRangeReport {
    private RangeNode root;
    void add(int value) {
        root = add(root, value);
    }
    private RangeNode add(RangeNode node, int value) {
        if (node == null) {
            return new RangeNode(value);
        }
        if (value < node.value) {
            node.left = add(node.left, value);
        } else if (value > node.value) {
            node.right = add(node.right, value);
        }
        return node;
    }
    Integer min() {
        if (root == null) {
            return null;
        }
        RangeNode current = root;
        while (current.left != null) {
            current = current.left;
        }
        return current.value;
    }
    Integer max() {
        if (root == null) {
            return null;
        }
        RangeNode current = root;
        while (current.right != null) {
            current = current.right;
        }
        return current.value;
    }
    void printRange(int low, int high) {
        System.out.print("range " + low + "~" + high + "=");
        if (low > high) {
            System.out.println("low大於high，無結果");
            return;
        }
        printRange(root, low, high);
        System.out.println();
    }
    private void printRange(RangeNode node, int low, int high) {
        if (node == null) {
            return;
        }
        if (node.value > low) {
            printRange(node.left, low, high);
        }
        if (node.value >= low && node.value <= high) {
            System.out.print(node.value + " ");
        }
        if (node.value < high) {
            printRange(node.right, low, high);
        }
    }
    public static void main(String[] args) {
        BstRangeReport tree = new BstRangeReport();
        for (int value : new int[]{ 50, 30, 70, 20, 40, 60, 80 }) {
            tree.add(value);
        }
        System.out.println("min=" + tree.min() + " max=" + tree.max());
        tree.printRange(30, 60);
        tree.printRange(20, 20);
        tree.printRange(90, 99);
        tree.printRange(60, 30);
        BstRangeReport empty = new BstRangeReport();
        System.out.println("empty min=" + empty.min() + " max=" + empty.max());
        empty.printRange(1, 100);
    }
}
