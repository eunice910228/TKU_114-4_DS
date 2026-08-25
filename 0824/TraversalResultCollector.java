import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
class CollectNode {
    String value;
    CollectNode left;
    CollectNode right;
    CollectNode(String value) {
        this.value = value;
    }
}
public class TraversalResultCollector {
    static List<String> preorder(CollectNode root) {
        List<String> result = new ArrayList<>();
        preorder(root, result);
        return result;
    }
    private static void preorder(CollectNode node, List<String> result) {
        if (node == null) {
            return;
        }
        result.add(node.value);
        preorder(node.left, result);
        preorder(node.right, result);
    }
    static List<String> inorder(CollectNode root) {
        List<String> result = new ArrayList<>();
        inorder(root, result);
        return result;
    }
    private static void inorder(CollectNode node, List<String> result) {
        if (node == null) {
            return;
        }
        inorder(node.left, result);
        result.add(node.value);
        inorder(node.right, result);
    }
    static List<String> postorder(CollectNode root) {
        List<String> result = new ArrayList<>();
        postorder(root, result);
        return result;
    }
    private static void postorder(CollectNode node, List<String> result) {
        if (node == null) {
            return;
        }
        postorder(node.left, result);
        postorder(node.right, result);
        result.add(node.value);
    }
    static List<String> levelOrder(CollectNode root) {
        List<String> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
        Deque<CollectNode> queue = new ArrayDeque<>();
        queue.offerLast(root);
        while (!queue.isEmpty()) {
            CollectNode node = queue.pollFirst();
            result.add(node.value);
            if (node.left != null) {
                queue.offerLast(node.left);
            }
            if (node.right != null) {
                queue.offerLast(node.right);
            }
        }
        return result;
    }
    static void report(String title, CollectNode root) {
        System.out.println(title);
        System.out.println("  preorder =" + preorder(root));
        System.out.println("  inorder =" + inorder(root));
        System.out.println("  postorder =" + postorder(root));
        System.out.println("  levelOrder =" + levelOrder(root));
    }
    public static void main(String[] args) {
        report("empty tree", null);
        report("single-node tree", new CollectNode("X"));
        CollectNode skewed = new CollectNode("A");
        skewed.left = new CollectNode("B");
        skewed.left.left = new CollectNode("C");
        skewed.left.left.left = new CollectNode("D");
        report("left-skewed tree", skewed);
        CollectNode complete = new CollectNode("A");
        complete.left = new CollectNode("B");
        complete.right = new CollectNode("C");
        complete.left.left = new CollectNode("D");
        complete.left.right = new CollectNode("E");
        complete.right.left = new CollectNode("F");
        complete.right.right = new CollectNode("G");
        report("complete tree", complete);
    }
}