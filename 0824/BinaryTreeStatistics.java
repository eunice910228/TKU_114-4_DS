class StatNode {
    int value;
    StatNode left;
    StatNode right;
    StatNode(int value) {
        this.value = value;
    }
}
public class BinaryTreeStatistics {
    static int size(StatNode node) {
        if (node == null) {
            return 0;
        }
        return 1 + size(node.left) + size(node.right);
    }
    static int sum(StatNode node) {
        if (node == null) {
            return 0;
        }
        return node.value + sum(node.left) + sum(node.right);
    }
    static Integer maximum(StatNode node) {
        if (node == null) {
            return null;
        }
        int best = node.value;
        Integer left = maximum(node.left);
        if (left != null && left > best) {
            best = left;
        }
        Integer right = maximum(node.right);
        if (right != null && right > best) {
            best = right;
        }
        return best;
    }
    static int leafCount(StatNode node) {
        if (node == null) {
            return 0;
        }
        if (node.left == null && node.right == null) {
            return 1;
        }
        return leafCount(node.left) + leafCount(node.right);
    }
    static int height(StatNode node) {
        if (node == null) {
            return -1;
        }
        return 1 + Math.max(height(node.left), height(node.right));
    }
    static boolean contains(StatNode node, int target) {
        if (node == null) {
            return false;
        }
        if (node.value == target) {
            return true;
        }
        return contains(node.left, target) || contains(node.right, target);
    }
    static void report(String title, StatNode root) {
        System.out.println(title);
        System.out.println("size =" + size(root));
        System.out.println("sum =" + sum(root));
        System.out.println("maximum =" + maximum(root));
        System.out.println("leaf count =" + leafCount(root));
        System.out.println("height =" + height(root));
    }
    public static void main(String[] args) {
        StatNode root = new StatNode(10);
        root.left = new StatNode(-5);
        root.right = new StatNode(20);
        root.left.left = new StatNode(3);
        root.left.right = new StatNode(8);
        root.right.right = new StatNode(-30);
        report("一般 tree", root);
        System.out.println("contains(8) =" + contains(root, 8));
        System.out.println("contains(99) =" + contains(root, 99));
        report("empty tree", null);
        report("single-node tree", new StatNode(-7));
    }
}