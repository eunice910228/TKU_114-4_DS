class SkewNode {
    int value;
    SkewNode left;
    SkewNode right;
    SkewNode(int value) {
        this.value = value;
    }
}
public class SkewedBstReport {
    private SkewNode root;
    private int size;
    void add(int value) {
        root = add(root, value);
    }
    private SkewNode add(SkewNode node, int value) {
        if (node == null) {
            size++;
            return new SkewNode(value);
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
    private int height(SkewNode node) {
        if (node == null) {
            return -1;
        }
        return 1 + Math.max(height(node.left), height(node.right));
    }
    int comparisons(int target) {
        int count = 0;
        SkewNode current = root;
        while (current != null) {
            count++;
            if (target == current.value) {
                return count;
            }
            current = target < current.value ? current.left : current.right;
        }
        return count;
    }
    static void report(String title, int[] order) {
        SkewedBstReport tree = new SkewedBstReport();
        for (int value : order) {
            tree.add(value);
        }
        int total = 0;
        for (int value = 1; value <= 7; value++) {
            total += tree.comparisons(value);
        }
        System.out.println(title + " size=" + tree.size + " height=" + tree.height() + " 搜尋1~7比較總數=" + total + " 搜尋8=" + tree.comparisons(8));
    }
    public static void main(String[] args) {
        report("排序資料插入", new int[]{ 1, 2, 3, 4, 5, 6, 7 });
        report("平衡順序插入", new int[]{ 4, 2, 6, 1, 3, 5, 7 });
        report("空tree", new int[0]);
    }
}
