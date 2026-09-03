import java.util.ArrayList;
import java.util.List;
class CharNode {
    char value;
    CharNode left;
    CharNode right;
    CharNode(char value) {
        this.value = value;
    }
}
public class ThreeTraversalPractice {
    static void preorder(CharNode node, List<Character> result) {
        if (node == null) {
            return;
        }
        result.add(node.value);
        preorder(node.left, result);
        preorder(node.right, result);
    }
    static void inorder(CharNode node, List<Character> result) {
        if (node == null) {
            return;
        }
        inorder(node.left, result);
        result.add(node.value);
        inorder(node.right, result);
    }
    static void postorder(CharNode node, List<Character> result) {
        if (node == null) {
            return;
        }
        postorder(node.left, result);
        postorder(node.right, result);
        result.add(node.value);
    }
    static List<Character> run(String which, CharNode root) {
        List<Character> result = new ArrayList<>();
        if (which.equals("pre")) {
            preorder(root, result);
        } else if (which.equals("in")) {
            inorder(root, result);
        } else {
            postorder(root, result);
        }
        return result;
    }
    public static void main(String[] args) {
        CharNode root = new CharNode('M');
        root.left = new CharNode('F');
        root.right = new CharNode('T');
        root.left.left = new CharNode('B');
        root.right.left = new CharNode('R');
        root.right.right = new CharNode('Z');
        System.out.println("preorder=" + run("pre", root));
        System.out.println("inorder=" + run("in", root));
        System.out.println("postorder=" + run("post", root));
        System.out.println("empty preorder=" + run("pre", null));
    }
}
