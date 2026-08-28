import java.util.ArrayList;
import java.util.List;

public class Q11_BstDeletion {
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
    public boolean remove(int value) {
        if (!contains(value)) {
            return false;
        }
        root = remove(root, value);
        size--;
        return true;
    }
    private Node remove(Node node, int value) {
        if (node == null) {
            return null;
        }
        if (value < node.value) {
            node.left = remove(node.left, value);
        } else if (value > node.value) {
            node.right = remove(node.right, value);
        } else {
            if (node.left == null) {
                return node.right;
            }
            if (node.right == null) {
                return node.left;
            }
            Node successor = node.right;
            while (successor.left != null) {
                successor = successor.left;
            }
            node.value = successor.value;
            node.right = remove(node.right, successor.value);
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
        Q11_BstDeletion tree = new Q11_BstDeletion();
        for (int value : new int[]{50, 30, 70, 20, 40, 60, 80}) tree.add(value);
        System.out.println(tree.remove(20));
        System.out.println(tree.remove(30));
        System.out.println(tree.remove(50));
        System.out.println(tree.remove(999));
        System.out.println(tree.inorder());
        System.out.println(tree.size());
        System.out.println(tree.isValid());
    }
}
