class CheckNode {
    int value;
    CheckNode left;
    CheckNode right;
    CheckNode(int value) {
        this.value = value;
    }
}
public class BstInvariantChecker {
    static boolean isValid(CheckNode root) {
        return isValid(root, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }
    private static boolean isValid(CheckNode node, int low, int high) {
        if (node == null) {
            return true;
        }
        if (node.value <= low || node.value >= high) {
            return false;
        }
        return isValid(node.left, low, node.value) && isValid(node.right, node.value, high);
    }
    static boolean onlyDirectChild(CheckNode node) {
        if (node == null) {
            return true;
        }
        if (node.left != null && node.left.value >= node.value) {
            return false;
        }
        if (node.right != null && node.right.value <= node.value) {
            return false;
        }
        return onlyDirectChild(node.left) && onlyDirectChild(node.right);
    }
    static CheckNode build(int rootValue, int leftValue, int rightValue, int deepValue, boolean deepOnLeftRight) {
        CheckNode root = new CheckNode(rootValue);
        root.left = new CheckNode(leftValue);
        root.right = new CheckNode(rightValue);
        if (deepOnLeftRight) {
            root.left.right = new CheckNode(deepValue);
        } else {
            root.right.left = new CheckNode(deepValue);
        }
        return root;
    }
    static void report(String title, CheckNode root) {
        System.out.println(title + " boundary檢查=" + isValid(root) + " 只查直接child=" + onlyDirectChild(root));
    }
    public static void main(String[] args) {
        report("valid tree", build(50, 30, 70, 40, true));
        report("違規1 左子樹的右孫60大於root50", build(50, 30, 70, 60, true));
        report("違規2 右子樹的左孫45小於root50", build(50, 30, 70, 45, false));
        CheckNode deep = build(50, 30, 70, 40, true);
        deep.left.right.right = new CheckNode(55);
        report("違規3 40的右child55大於祖先root50", deep);
        report("empty tree", null);
        report("single node", new CheckNode(1));
    }
}
