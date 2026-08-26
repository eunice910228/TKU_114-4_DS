class SuiteNode {
    int value;
    SuiteNode left;
    SuiteNode right;
    SuiteNode(int value) {
        this.value = value;
    }
}

class SuiteBst {
    private SuiteNode root;
    void add(int value) {
        root = add(root, value);
    }
    private SuiteNode add(SuiteNode node, int value) {
        if (node == null) {
            return new SuiteNode(value);
        }
        if (value < node.value) {
            node.left = add(node.left, value);
        } else if (value > node.value) {
            node.right = add(node.right, value);
        }
        return node;
    }
    boolean contains(int value) {
        SuiteNode current = root;
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
    private SuiteNode remove(SuiteNode node, int value) {
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
            SuiteNode successor = node.right;
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
    private int size(SuiteNode node) {
        if (node == null) {
            return 0;
        }
        return 1 + size(node.left) + size(node.right);
    }
    String inorderText() {
        StringBuilder sb = new StringBuilder();
        inorderText(root, sb);
        return sb.toString().trim();
    }
    private void inorderText(SuiteNode node, StringBuilder sb) {
        if (node == null) {
            return;
        }
        inorderText(node.left, sb);
        sb.append(node.value).append(' ');
        inorderText(node.right, sb);
    }
}

public class BstDeleteTestSuite {
    static void show(String label, SuiteBst tree, boolean result) {
        System.out.println("  " + label + " = " + result
                + "，inorder = [" + tree.inorderText() + "]，size = " + tree.size());
    }
    public static void main(String[] args) {
        System.out.println("empty tree 刪除");
        SuiteBst empty = new SuiteBst();
        show("remove(10)", empty, empty.remove(10));

        System.out.println("missing value 刪除");
        SuiteBst tree = new SuiteBst();
        for (int value : new int[]{ 50, 30, 70 }) {
            tree.add(value);
        }
        show("remove(99)", tree, tree.remove(99));

        System.out.println("single root 刪除");
        SuiteBst single = new SuiteBst();
        single.add(50);
        show("remove(50)", single, single.remove(50));

        System.out.println("root with one child 刪除");
        SuiteBst oneChild = new SuiteBst();
        oneChild.add(50);
        oneChild.add(30);
        show("remove(50)", oneChild, oneChild.remove(50));

        System.out.println("root with two children 刪除");
        SuiteBst twoChildren = new SuiteBst();
        for (int value : new int[]{ 50, 30, 70, 60 }) {
            twoChildren.add(value);
        }
        show("remove(50)", twoChildren, twoChildren.remove(50));

        System.out.println("連續刪除到 empty");
        SuiteBst drain = new SuiteBst();
        for (int value : new int[]{ 50, 30, 70, 20, 40 }) {
            drain.add(value);
        }
        for (int value : new int[]{ 30, 50, 20, 70, 40 }) {
            show("remove(" + value + ")", drain, drain.remove(value));
        }
        show("remove(40)", drain, drain.remove(40));
    }
}
