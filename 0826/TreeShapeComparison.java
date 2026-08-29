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
    int comparisons(int target) {
        int count = 0;
        CmpNode current = root;
        while (current != null) {
            count++;
            if (target == current.value) {
                return count;
            }
            current = (target < current.value) ? current.left : current.right;
        }
        return count;
    }
    static void compare(String title, int[] insertOrder) {
        TreeShapeComparison tree = new TreeShapeComparison();
        for (int value : insertOrder) {
            tree.add(value);
        }
        int total = 0;
        for (int value = 1; value <= 15; value++) {
            total = total + tree.comparisons(value);
        }
        int missing = tree.comparisons(16);
        System.out.println(title);
        System.out.println("height=" + tree.height());
        System.out.println("搜尋全部15個key的比較總數=" + total);
        System.out.println("搜尋missing key16的比較次數=" + missing);
    }
    public static void main(String[] args) {
        int[] ascending = new int[15];
        int[] descending = new int[15];
        for (int i = 0; i < 15; i++) {
            ascending[i] = i + 1;
            descending[i] = 15 - i;
        }
        int[] balanced = { 8, 4, 12, 2, 6, 10, 14, 1, 3, 5, 7, 9, 11, 13, 15 };
        compare("升冪插入1~15", ascending);
        compare("降冪插入15~1", descending);
        compare("接近平衡插入", balanced);
    }
}
