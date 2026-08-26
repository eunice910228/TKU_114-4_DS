class ExpNode {
    int value;
    ExpNode left;
    ExpNode right;
    ExpNode(int value) {
        this.value = value;
    }
}
public class BstShapeExperiment {
    private ExpNode root;

    void add(int value) {
        root = add(root, value);
    }
    private ExpNode add(ExpNode node, int value) {
        if (node == null) {
            return new ExpNode(value);
        }
        if (value < node.value) {
            node.left = add(node.left, value);
        } else if (value > node.value) {
            node.right = add(node.right, value);
        }
        return node;
    }
    int height() {
        return height(root);
    }
    private int height(ExpNode node) {
        if (node == null) {
            return -1;
        }
        return 1 + Math.max(height(node.left), height(node.right));
    }
    public static void main(String[] args) {
        int[] ascending = new int[15];
        for (int i = 0; i < 15; i++) {
            ascending[i] = i + 1;
        }
        int[] balanced = { 8, 4, 12, 2, 6, 10, 14, 1, 3, 5, 7, 9, 11, 13, 15 };

        BstShapeExperiment sorted = new BstShapeExperiment();
        for (int value : ascending) {
            sorted.add(value);
        }
        BstShapeExperiment shaped = new BstShapeExperiment();
        for (int value : balanced) {
            shaped.add(value);
        }
        System.out.println("升冪插入height=" + sorted.height());
        System.out.println("平衡順序插入height=" + shaped.height());
    }
}
