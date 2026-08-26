import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

class CaseNode {
    String value;
    CaseNode left;
    CaseNode right;

    CaseNode(String value) {
        this.value = value;
    }
}

public class TraversalTestReport {

    static List<String> preorder(CaseNode node) {
        List<String> result = new ArrayList<>();
        pre(node, result);
        return result;
    }

    private static void pre(CaseNode node, List<String> result) {
        if (node == null) {
            return;
        }
        result.add(node.value);
        pre(node.left, result);
        pre(node.right, result);
    }

    static List<String> inorder(CaseNode node) {
        List<String> result = new ArrayList<>();
        in(node, result);
        return result;
    }

    private static void in(CaseNode node, List<String> result) {
        if (node == null) {
            return;
        }
        in(node.left, result);
        result.add(node.value);
        in(node.right, result);
    }

    static List<String> postorder(CaseNode node) {
        List<String> result = new ArrayList<>();
        post(node, result);
        return result;
    }

    private static void post(CaseNode node, List<String> result) {
        if (node == null) {
            return;
        }
        post(node.left, result);
        post(node.right, result);
        result.add(node.value);
    }

    static List<String> levelOrder(CaseNode root) {
        List<String> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
        Deque<CaseNode> queue = new ArrayDeque<>();
        queue.offerLast(root);
        while (!queue.isEmpty()) {
            CaseNode node = queue.pollFirst();
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

    static void compare(String label, List<String> expected, List<String> actual) {
        boolean same = expected.equals(actual);
        System.out.println("  " + label + " 預期=" + expected + " 實際=" + actual
                + " 相同=" + same);
    }

    static void report(String title, CaseNode root,
                       List<String> pre, List<String> in,
                       List<String> post, List<String> level) {
        System.out.println(title);
        compare("preorder ", pre, preorder(root));
        compare("inorder  ", in, inorder(root));
        compare("postorder", post, postorder(root));
        compare("level    ", level, levelOrder(root));
    }

    public static void main(String[] args) {
        report("empty tree", null,
                List.of(), List.of(), List.of(), List.of());

        report("single-node", new CaseNode("A"),
                List.of("A"), List.of("A"), List.of("A"), List.of("A"));

        CaseNode onlyLeft = new CaseNode("A");
        onlyLeft.left = new CaseNode("B");
        onlyLeft.left.left = new CaseNode("C");
        report("only-left", onlyLeft,
                List.of("A", "B", "C"), List.of("C", "B", "A"),
                List.of("C", "B", "A"), List.of("A", "B", "C"));

        CaseNode onlyRight = new CaseNode("A");
        onlyRight.right = new CaseNode("B");
        onlyRight.right.right = new CaseNode("C");
        report("only-right", onlyRight,
                List.of("A", "B", "C"), List.of("A", "B", "C"),
                List.of("C", "B", "A"), List.of("A", "B", "C"));

        CaseNode complete = new CaseNode("A");
        complete.left = new CaseNode("B");
        complete.right = new CaseNode("C");
        complete.left.left = new CaseNode("D");
        complete.left.right = new CaseNode("E");
        complete.right.left = new CaseNode("F");
        complete.right.right = new CaseNode("G");
        report("complete", complete,
                List.of("A", "B", "D", "E", "C", "F", "G"),
                List.of("D", "B", "E", "A", "F", "C", "G"),
                List.of("D", "E", "B", "F", "G", "C", "A"),
                List.of("A", "B", "C", "D", "E", "F", "G"));

        CaseNode irregular = new CaseNode("A");
        irregular.left = new CaseNode("B");
        irregular.right = new CaseNode("C");
        irregular.left.right = new CaseNode("D");
        irregular.right.left = new CaseNode("E");
        irregular.right.left.left = new CaseNode("F");
        report("irregular", irregular,
                List.of("A", "B", "D", "C", "E", "F"),
                List.of("B", "D", "A", "F", "E", "C"),
                List.of("D", "B", "F", "E", "C", "A"),
                List.of("A", "B", "C", "D", "E", "F"));
    }
}
