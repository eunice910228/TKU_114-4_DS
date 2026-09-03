class CountNode {
    int key;
    int count;
    CountNode left;
    CountNode right;
    CountNode(int key) {
        this.key = key;
        this.count = 1;
    }
}
public class BstDuplicateCounter {
    private CountNode root;
    private int nodeCount;
    void add(int key) {
        root = add(root, key);
    }
    private CountNode add(CountNode node, int key) {
        if (node == null) {
            nodeCount++;
            return new CountNode(key);
        }
        if (key < node.key) {
            node.left = add(node.left, key);
        } else if (key > node.key) {
            node.right = add(node.right, key);
        } else {
            node.count++;
        }
        return node;
    }
    int countOf(int key) {
        CountNode current = root;
        while (current != null) {
            if (key == current.key) {
                return current.count;
            }
            current = key < current.key ? current.left : current.right;
        }
        return 0;
    }
    int nodeCount() {
        return nodeCount;
    }
    void inorder() {
        System.out.print("inorder=");
        inorder(root);
        System.out.println();
    }
    private void inorder(CountNode node) {
        if (node == null) {
            return;
        }
        inorder(node.left);
        System.out.print(node.key + "(" + node.count + ") ");
        inorder(node.right);
    }
    public static void main(String[] args) {
        BstDuplicateCounter tree = new BstDuplicateCounter();
        for (int key : new int[]{ 50, 30, 70, 30, 50, 30, 90 }) {
            tree.add(key);
        }
        tree.inorder();
        System.out.println("node數=" + tree.nodeCount());
        System.out.println("30的count=" + tree.countOf(30));
        System.out.println("90的count=" + tree.countOf(90));
        System.out.println("missing的count=" + tree.countOf(99));
        BstDuplicateCounter empty = new BstDuplicateCounter();
        empty.inorder();
        System.out.println("empty node數=" + empty.nodeCount());
    }
}
