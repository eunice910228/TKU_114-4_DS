import java.util.ArrayList;
import java.util.List;
class StatNode {
    int value;
    StatNode left;
    StatNode right;
    StatNode(int value) {
        this.value = value;
    }
}
public class BstRangeStatistics {
    private StatNode root;
    void add(int value) {
        root = add(root, value);
    }
    private StatNode add(StatNode node, int value) {
        if (node == null) {
            return new StatNode(value);
        }
        if (value < node.value) {
            node.left = add(node.left, value);
        } else if (value > node.value) {
            node.right = add(node.right, value);
        }
        return node;
    }
    List<Integer> valuesBetween(int low, int high) {
        List<Integer> result = new ArrayList<>();
        if (low > high) {
            return result;
        }
        collect(root, low, high, result);
        return result;
    }
    private void collect(StatNode node, int low, int high, List<Integer> result) {
        if (node == null) {
            return;
        }
        if (node.value > low) {
            collect(node.left, low, high, result);
        }
        if (node.value >= low && node.value <= high) {
            result.add(node.value);
        }
        if (node.value < high) {
            collect(node.right, low, high, result);
        }
    }
    int countBetween(int low, int high) {
        if (low > high) {
            return 0;
        }
        return countBetween(root, low, high);
    }
    private int countBetween(StatNode node, int low, int high) {
        if (node == null) {
            return 0;
        }
        int total = 0;
        if (node.value > low) {
            total += countBetween(node.left, low, high);
        }
        if (node.value >= low && node.value <= high) {
            total++;
        }
        if (node.value < high) {
            total += countBetween(node.right, low, high);
        }
        return total;
    }
    int sumBetween(int low, int high) {
        if (low > high) {
            return 0;
        }
        return sumBetween(root, low, high);
    }
    private int sumBetween(StatNode node, int low, int high) {
        if (node == null) {
            return 0;
        }
        int total = 0;
        if (node.value > low) {
            total += sumBetween(node.left, low, high);
        }
        if (node.value >= low && node.value <= high) {
            total += node.value;
        }
        if (node.value < high) {
            total += sumBetween(node.right, low, high);
        }
        return total;
    }
    void report(int low, int high) {
        System.out.println(low + "~" + high + " values=" + valuesBetween(low, high) + " count=" + countBetween(low, high) + " sum=" + sumBetween(low, high));
    }
    public static void main(String[] args) {
        BstRangeStatistics tree = new BstRangeStatistics();
        for (int value : new int[]{ 50, 30, 70, 20, 40, 60, 80 }) {
            tree.add(value);
        }
        tree.report(30, 60);
        tree.report(20, 80);
        tree.report(45, 55);
        tree.report(90, 99);
        tree.report(60, 30);
        System.out.println("empty tree=" + new BstRangeStatistics().valuesBetween(1, 100));
    }
}
