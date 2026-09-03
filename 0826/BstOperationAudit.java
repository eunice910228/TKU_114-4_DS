import java.util.ArrayList;
import java.util.List;
class AuditNode {
    int value;
    AuditNode left;
    AuditNode right;
    AuditNode(int value) {
        this.value = value;
    }
}
public class BstOperationAudit {
    private AuditNode root;
    private int size;
    boolean add(int value) {
        if (contains(value)) {
            return false;
        }
        root = add(root, value);
        size++;
        return true;
    }
    private AuditNode add(AuditNode node, int value) {
        if (node == null) {
            return new AuditNode(value);
        }
        if (value < node.value) {
            node.left = add(node.left, value);
        } else {
            node.right = add(node.right, value);
        }
        return node;
    }
    boolean contains(int value) {
        AuditNode current = root;
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
    private AuditNode remove(AuditNode node, int value) {
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
            AuditNode successor = node.right;
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
    private void inorder(AuditNode node, List<Integer> result) {
        if (node == null) {
            return;
        }
        inorder(node.left, result);
        result.add(node.value);
        inorder(node.right, result);
    }
    int height() {
        return height(root);
    }
    private int height(AuditNode node) {
        if (node == null) {
            return -1;
        }
        return 1 + Math.max(height(node.left), height(node.right));
    }
    boolean isValid() {
        return isValid(root, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }
    private boolean isValid(AuditNode node, int low, int high) {
        if (node == null) {
            return true;
        }
        if (node.value <= low || node.value >= high) {
            return false;
        }
        return isValid(node.left, low, node.value) && isValid(node.right, node.value, high);
    }
    void audit(String operation, boolean result) {
        System.out.println(operation + "=" + result + " inorder=" + inorder() + " size=" + size + " height=" + height() + " valid=" + isValid());
    }
    public static void main(String[] args) {
        BstOperationAudit tree = new BstOperationAudit();
        for (int value : new int[]{ 50, 30, 70, 20, 40, 60, 80 }) {
            tree.audit("add " + value, tree.add(value));
        }
        tree.audit("add duplicate 40", tree.add(40));
        tree.audit("remove missing 99", tree.remove(99));
        tree.audit("remove leaf 20", tree.remove(20));
        tree.audit("remove one-child 30", tree.remove(30));
        tree.audit("remove two-child root 50", tree.remove(50));
    }
}
