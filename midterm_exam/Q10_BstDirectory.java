import java.util.ArrayList;
import java.util.List;

public class Q10_BstDirectory {
    private static class Node {
        int value;
        Node left;
        Node right;

        Node(int value) {
            this.value = value;
        }
    }
    private Node root;
    private int size;
    public boolean add(int value) {
        if (contains(value)) {
            return false;
        }
        root = add(root, value);
        size++;
        return true;
    }
    private Node add(Node node, int value) {
        if (node == null) {
            return new Node(value);
        }
        if (value < node.value) {
            node.left = add(node.left, value);
        } else {
            node.right = add(node.right, value);
        }
        return node;
    }
    public boolean contains(int value) {
        Node current = root;
        while (current != null) {
            if (value == current.value) {
                return true;
            }
            current = (value < current.value) ? current.left : current.right;
        }
        return false;
    }
    public int size() {
        return size;
    }
    public List<Integer> searchPath(int target) {
        List<Integer> path = new ArrayList<>();
        Node current = root;
        while (current != null) {
            path.add(current.value);
            if (target == current.value) {
                return path;
            }
            current = (target < current.value) ? current.left : current.right;
        }
        return path;
    }
    public List<Integer> inorder() {
        List<Integer> result = new ArrayList<>();
        inorder(root, result);
        return result;
    }
    private void inorder(Node node, List<Integer> result) {
        if (node == null) {
            return;
        }
        inorder(node.left, result);
        result.add(node.value);
        inorder(node.right, result);
    }
    public boolean isValid() {
        return isValid(root, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }
    private boolean isValid(Node node, int low, int high) {
        if (node == null) {
            return true;
        }
        if (node.value <= low || node.value >= high) {
            return false;
        }
        return isValid(node.left, low, node.value)
                && isValid(node.right, node.value, high);
    }
    public static void main(String[] args) {
        Q10_BstDirectory tree = new Q10_BstDirectory();
        for (int value : new int[]{50, 30, 70, 20, 40, 60, 80}) {
            tree.add(value);
        }
        System.out.println(tree.add(40));
        System.out.println(tree.searchPath(60));
        System.out.println(tree.searchPath(65));
        System.out.println(tree.inorder());
        System.out.println(tree.isValid());
    }
}
