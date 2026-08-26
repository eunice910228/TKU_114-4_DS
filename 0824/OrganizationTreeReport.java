import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

class OrgNode {
    String name;
    OrgNode left;
    OrgNode right;

    OrgNode(String name) {
        this.name = name;
    }
}

public class OrganizationTreeReport {

    static String findParent(OrgNode node, String target) {
        if (node == null) {
            return null;
        }
        if (node.left != null && node.left.name.equals(target)) {
            return node.name;
        }
        if (node.right != null && node.right.name.equals(target)) {
            return node.name;
        }
        String left = findParent(node.left, target);
        if (left != null) {
            return left;
        }
        return findParent(node.right, target);
    }

    static int findDepth(OrgNode node, String target) {
        if (node == null) {
            return -1;
        }
        if (node.name.equals(target)) {
            return 0;
        }
        int left = findDepth(node.left, target);
        if (left >= 0) {
            return left + 1;
        }
        int right = findDepth(node.right, target);
        if (right >= 0) {
            return right + 1;
        }
        return -1;
    }

    static List<String> pathFromRoot(OrgNode node, String target) {
        List<String> path = new ArrayList<>();
        build(node, target, path);
        return path;
    }

    private static boolean build(OrgNode node, String target, List<String> path) {
        if (node == null) {
            return false;
        }
        path.add(node.name);
        if (node.name.equals(target)) {
            return true;
        }
        if (build(node.left, target, path) || build(node.right, target, path)) {
            return true;
        }
        path.remove(path.size() - 1);
        return false;
    }

    static void printByLevel(OrgNode root) {
        if (root == null) {
            System.out.println("  (empty)");
            return;
        }
        Deque<OrgNode> queue = new ArrayDeque<>();
        queue.offerLast(root);
        int level = 0;
        while (!queue.isEmpty()) {
            int count = queue.size();
            System.out.print("  level " + level + ": ");
            for (int i = 0; i < count; i++) {
                OrgNode node = queue.pollFirst();
                System.out.print(node.name + " ");
                if (node.left != null) {
                    queue.offerLast(node.left);
                }
                if (node.right != null) {
                    queue.offerLast(node.right);
                }
            }
            System.out.println();
            level++;
        }
    }

    public static void main(String[] args) {
        OrgNode root = new OrgNode("註冊組");
        root.left = new OrgNode("課務組");
        root.right = new OrgNode("成績組");
        root.left.left = new OrgNode("學生會");
        root.left.right = new OrgNode("體育組");
        root.right.left = new OrgNode("文書組");
        root.right.right = new OrgNode("招生組");

        System.out.println("組織架構");
        printByLevel(root);

        System.out.println("findParent(體育組) =" + findParent(root, "體育組"));
        System.out.println("findParent(註冊組) =" + findParent(root, "註冊組"));
        System.out.println("findDepth(文書組) =" + findDepth(root, "文書組"));
        System.out.println("pathFromRoot(招生組) =" + pathFromRoot(root, "招生組"));

        System.out.println("找不到的單位");
        System.out.println("  findParent(學務處) =" + findParent(root, "學務處"));
        System.out.println("  findDepth(學務處) =" + findDepth(root, "學務處"));
        System.out.println("  pathFromRoot(學務處) =" + pathFromRoot(root, "學務處"));

        System.out.println("empty tree");
        System.out.println("  findDepth =" + findDepth(null, "任何"));
        System.out.println("  pathFromRoot =" + pathFromRoot(null, "任何"));
        printByLevel(null);
    }
}
