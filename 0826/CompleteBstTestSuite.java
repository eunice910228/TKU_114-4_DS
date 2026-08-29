import java.util.ArrayList;
import java.util.List;

class TestNode {
    int value;
    TestNode left;
    TestNode right;

    TestNode(int value) {
        this.value = value;
    }
}
class TestBst {
    private TestNode root;
    boolean add(int value) {
        if (contains(value)) {
            return false;
        }
        root = add(root, value);
        return true;
    }
    private TestNode add(TestNode node, int value) {
        if (node == null) {
            return new TestNode(value);
        }
        if (value < node.value) {
            node.left = add(node.left, value);
        } else {
            node.right = add(node.right, value);
        }
        return node;
    }
    boolean contains(int value) {
        TestNode current = root;
        while (current != null) {
            if (value == current.value) {
                return true;
            }
            current = (value < current.value) ? current.left : current.right;
        }
        return false;
    }
    boolean remove(int value) {
        if (!contains(value)) {
            return false;
        }
        root = remove(root, value);
        return true;
    }
    private TestNode remove(TestNode node, int value) {
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
            TestNode successor = node.right;
            while (successor.left != null) {
                successor = successor.left;
            }
            node.value = successor.value;
            node.right = remove(node.right, successor.value);
        }
        return node;
    }
    int size() {
        return size(root);
    }
    private int size(TestNode node) {
        if (node == null) {
            return 0;
        }
        return 1 + size(node.left) + size(node.right);
    }
    List<Integer> inorder() {
        List<Integer> result = new ArrayList<>();
        inorder(root, result);
        return result;
    }
    private void inorder(TestNode node, List<Integer> result) {
        if (node == null) {
            return;
        }
        inorder(node.left, result);
        result.add(node.value);
        inorder(node.right, result);
    }
    List<Integer> range(int low, int high) {
        List<Integer> result = new ArrayList<>();
        range(root, low, high, result);
        return result;
    }
    private void range(TestNode node, int low, int high, List<Integer> result) {
        if (node == null) {
            return;
        }
        if (node.value > low) {
            range(node.left, low, high, result);
        }
        if (node.value >= low && node.value <= high) {
            result.add(node.value);
        }
        if (node.value < high) {
            range(node.right, low, high, result);
        }
    }
    boolean isValid() {
        return isValid(root, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }
    private boolean isValid(TestNode node, int low, int high) {
        if (node == null) {
            return true;
        }
        if (node.value <= low || node.value >= high) {
            return false;
        }
        return isValid(node.left, low, node.value)
                && isValid(node.right, node.value, high);
    }
}
public class CompleteBstTestSuite {
    static int passCount = 0;
    static int failCount = 0;
    static void check(String description, boolean condition) {
        if (condition) {
            passCount++;
            System.out.println("PASS " + description);
        } else {
            failCount++;
            System.out.println("FAIL " + description);
        }
    }
    public static void main(String[] args) {
        TestBst empty = new TestBst();
        check("empty tree size為0", empty.size() == 0);
        check("empty tree contains回false", !empty.contains(10));
        check("empty tree remove回false", !empty.remove(10));
        check("empty tree inorder為空", empty.inorder().isEmpty());
        check("empty tree是valid", empty.isValid());
        TestBst tree = new TestBst();
        for (int value : new int[]{ 50, 30, 70, 20, 40, 60, 80 }) {
            tree.add(value);
        }
        check("加入7筆後size為7", tree.size() == 7);
        check("duplicate add 回false", !tree.add(50));
        check("duplicate不改變size", tree.size() == 7);
        check("inorder 為升冪", tree.inorder()
                .equals(List.of(20, 30, 40, 50, 60, 70, 80)));
        check("加入後invariant成立", tree.isValid());
        check("contains找得到leaf 20", tree.contains(20));
        check("contains找不到missing 65", !tree.contains(65));
        check("remove missing回false", !tree.remove(99));
        check("remove leaf 20成功", tree.remove(20));
        check("刪leaf後inorder正確", tree.inorder()
                .equals(List.of(30, 40, 50, 60, 70, 80)));
        check("remove one-child 30成功", tree.remove(30));
        check("one-child的child 40沒有遺失", tree.contains(40));
        check("remove two-children root 0成功", tree.remove(50));
        check("two-child刪除後沒有重複", tree.size() == 4
                && tree.inorder().equals(List.of(40, 60, 70, 80)));
        check("連續刪除後invariant成立", tree.isValid());
        check("range(60, 80)含兩端點", tree.range(60, 80).equals(List.of(60, 70, 80)));
        check("range落空回空的list", tree.range(90, 99).isEmpty());
        check("range low>high回空的list", tree.range(80, 60).isEmpty());
        TestBst single = new TestBst();
        single.add(42);
        check("single root remove後變empty", single.remove(42) && single.size() == 0);
        TestBst drain = new TestBst();
        drain.add(10);
        drain.add(5);
        drain.add(15);
        drain.remove(10);
        drain.remove(5);
        drain.remove(15);
        check("刪到empty後可add", drain.add(7) && drain.size() == 1);
        System.out.println("共 " + (passCount + failCount) + " 項，PASS "
                + passCount + "，FAIL " + failCount);
    }
}
