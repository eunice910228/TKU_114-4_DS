import java.util.ArrayList;
import java.util.List;
class DelNode {
    int value;
    DelNode left;
    DelNode right;
    DelNode(int value) {
        this.value = value;
    }
}
public class BstDeleteCases {
    private DelNode root;
    private int size;
    void add(int value) {
        if (!contains(value)) {
            root = add(root, value);
            size++;
        }
    }
    private DelNode add(DelNode node, int value) {
        if (node == null) {
            return new DelNode(value);
        }
        if (value < node.value) {
            node.left = add(node.left, value);
        } else {
            node.right = add(node.right, value);
        }
        return node;
    }
    boolean contains(int value) {
        DelNode current = root;
        while (current != null) {
            if (value == current.value) {
                return true;
            }
            current = value < current.value ? current.left : current.right;
        }
        return false;
    }
    boolean remove(int value) {
        if (!contains(value)) {
            return false;
        }
        root = remove(root, value);
        size--;
        return true;
    }
    private DelNode remove(DelNode node, int value) {
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
            DelNode successor = node.right;
            while (successor.left != null) {
                successor = successor.left;
            }
            node.value = successor.value;
            node.right = remove(node.right, successor.value);
        }
        return node;
    }
    List<Integer> inorder() {
        List<Integer> result = new ArrayList<>();
        inorder(root, result);
        return result;
    }
    private void inorder(DelNode node, List<Integer> result) {
        if (node == null) {
            return;
        }
        inorder(node.left, result);
        result.add(node.value);
        inorder(node.right, result);
    }
    boolean isValid() {
        return isValid(root, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }
    private boolean isValid(DelNode node, int low, int high) {
        if (node == null) {
            return true;
        }
        if (node.value <= low || node.value >= high) {
            return false;
        }
        return isValid(node.left, low, node.value) && isValid(node.right, node.value, high);
    }
    void report(String label) {
        System.out.println(label + " inorder=" + inorder() + " size=" + size + " valid=" + isValid());
    }
    public static void main(String[] args) {
        BstDeleteCases tree = new BstDeleteCases();
        for (int value : new int[]{ 50, 30, 70, 20, 40, 60, 80, 35 }) {
            tree.add(value);
        }
        tree.report("初始");
        System.out.println("刪leaf 20=" + tree.remove(20));
        tree.report("刪leaf後");
        System.out.println("刪single-child 40=" + tree.remove(40));
        tree.report("刪single-child後");
        System.out.println("刪two-child 50=" + tree.remove(50));
        tree.report("刪two-child後");
        System.out.println("刪missing 99=" + tree.remove(99));
        tree.report("刪missing後");
    }
}
