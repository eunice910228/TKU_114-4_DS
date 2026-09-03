import java.util.ArrayDeque;
import java.util.Queue;
class LevelNode {
    String value;
    LevelNode left;
    LevelNode right;
    LevelNode(String value) {
        this.value = value;
    }
}
public class LevelOrderByLine {
    static void printByLevel(LevelNode root) {
        if (root == null) {
            System.out.println("empty tree");
            return;
        }
        Queue<LevelNode> queue = new ArrayDeque<>();
        queue.offer(root);
        int level = 0;
        while (!queue.isEmpty()) {
            int count = queue.size();
            System.out.print("level" + level + "（" + count + "個）=");
            for (int i = 0; i < count; i++) {
                LevelNode current = queue.poll();
                System.out.print(current.value + " ");
                if (current.left != null) {
                    queue.offer(current.left);
                }
                if (current.right != null) {
                    queue.offer(current.right);
                }
            }
            System.out.println();
            level++;
        }
    }
    public static void main(String[] args) {
        LevelNode root = new LevelNode("A");
        root.left = new LevelNode("B");
        root.right = new LevelNode("C");
        root.left.left = new LevelNode("D");
        root.left.right = new LevelNode("E");
        root.right.right = new LevelNode("F");
        root.left.right.left = new LevelNode("G");
        printByLevel(root);
        printByLevel(null);
        printByLevel(new LevelNode("X"));
    }
}
