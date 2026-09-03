import java.util.ArrayList;
import java.util.List;
class TreeNode {
    String value;
    TreeNode left;
    TreeNode right;
    TreeNode(String value) {
        this.value = value;
    }
}
public class BinaryTreeStructureReport {
    static int size(TreeNode node) {
        if (node == null) {
            return 0;
        }
        return 1 + size(node.left) + size(node.right);
    }
    static int leafCount(TreeNode node) {
        if (node == null) {
            return 0;
        }
        if (node.left == null && node.right == null) {
            return 1;
        }
        return leafCount(node.left) + leafCount(node.right);
    }
    static int height(TreeNode node) {
        if (node == null) {
            return -1;
        }
        return 1 + Math.max(height(node.left), height(node.right));
    }
    static void collectLeaves(TreeNode node, List<String> result) {
        if (node == null) {
            return;
        }
        if (node.left == null && node.right == null) {
            result.add(node.value);
            return;
        }
        collectLeaves(node.left, result);
        collectLeaves(node.right, result);
    }
    static void report(String title, TreeNode root) {
        List<String> leaves = new ArrayList<>();
        collectLeaves(root, leaves);
        System.out.println(title);
        System.out.println("root=" + (root == null ? "null" : root.value));
        System.out.println("leaves=" + leaves);
        System.out.println("size=" + size(root) + " leafCount=" + leafCount(root) + " height=" + height(root));
    }
    public static void main(String[] args) {
        TreeNode root = new TreeNode("A");
        root.left = new TreeNode("B");
        root.right = new TreeNode("C");
        root.left.left = new TreeNode("D");
        root.left.right = new TreeNode("E");
        root.right.right = new TreeNode("F");
        root.left.right.left = new TreeNode("G");
        report("七個node的tree", root);
        report("empty tree", null);
        report("single node tree", new TreeNode("X"));
    }
}
