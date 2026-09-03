class TraceNode {
    int value;
    TraceNode left;
    TraceNode right;
    TraceNode(int value) {
        this.value = value;
    }
}
public class BstSearchTrace {
    private TraceNode root;
    void add(int value) {
        root = add(root, value);
    }
    private TraceNode add(TraceNode node, int value) {
        if (node == null) {
            return new TraceNode(value);
        }
        if (value < node.value) {
            node.left = add(node.left, value);
        } else if (value > node.value) {
            node.right = add(node.right, value);
        }
        return node;
    }
    boolean searchTrace(int target) {
        System.out.println("搜尋" + target);
        TraceNode current = root;
        int comparisons = 0;
        while (current != null) {
            comparisons++;
            if (target == current.value) {
                System.out.println("比較" + current.value + " 相等，共比較" + comparisons + "次");
                return true;
            }
            String direction = target < current.value ? "往左" : "往右";
            System.out.println("比較" + current.value + " " + direction);
            current = target < current.value ? current.left : current.right;
        }
        System.out.println("找不到，共比較" + comparisons + "次");
        return false;
    }
    public static void main(String[] args) {
        BstSearchTrace tree = new BstSearchTrace();
        for (int value : new int[]{ 50, 30, 70, 20, 40, 60, 80 }) {
            tree.add(value);
        }
        System.out.println("root=" + tree.searchTrace(50));
        System.out.println("leaf=" + tree.searchTrace(20));
        System.out.println("internal=" + tree.searchTrace(70));
        System.out.println("missing=" + tree.searchTrace(65));
        System.out.println("empty tree=" + new BstSearchTrace().searchTrace(1));
    }
}
