class CmpNode {
    int value;
    CmpNode left;
    CmpNode right;

    CmpNode(int value) {
        this.value = value;
    }
}
public class TreeShapeComparison {
    private CmpNode root;

    void add(int value) {
        root = add(root, value);
    }
    private CmpNode add(CmpNode node, int value) {
        if (node == null) {
            return new CmpNode(value);
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
    private int height(CmpNode node) {
        if (node == null) {
            return -1;
        }
        return 1 + Math.max(height(node.left), height(node.right));
    }
    public static void main(String[] args) {
        int[] ascending = new int[15];
        int[] descending = new int[15];
        for (int i = 0; i < 15; i++) {
            ascending[i] = i + 1;
            descending[i] = 15 - i;
        }
        int[] balanced = { 8, 4, 12, 2, 6, 10, 14, 1, 3, 5, 7, 9, 11, 13, 15 };

        int[][] orders = { ascending, descending, balanced };
        String[] titles = { "升冪插入 1~15", "降冪插入 15~1", "接近平衡插入" };
        for (int i = 0; i < orders.length; i++) {
            TreeShapeComparison tree = new TreeShapeComparison();
            for (int value : orders[i]) {
                tree.add(value);
            }
            System.out.println(titles[i] + "height=" + tree.height());
        }
    }
}
