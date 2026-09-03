class MenuNode {
    String label;
    MenuNode left;
    MenuNode right;
    MenuNode(String label) {
        this.label = label;
    }
}
public class MenuTreeSearch {
    static boolean contains(MenuNode node, String target) {
        if (node == null) {
            return false;
        }
        if (node.label.equals(target)) {
            return true;
        }
        return contains(node.left, target) || contains(node.right, target);
    }
    static int findDepth(MenuNode node, String target) {
        if (node == null) {
            return -1;
        }
        if (node.label.equals(target)) {
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
    static int countLeaves(MenuNode node) {
        if (node == null) {
            return 0;
        }
        if (node.left == null && node.right == null) {
            return 1;
        }
        return countLeaves(node.left) + countLeaves(node.right);
    }
    static void display(MenuNode node, int depth) {
        if (node == null) {
            return;
        }
        System.out.println("  ".repeat(depth) + node.label);
        display(node.left, depth + 1);
        display(node.right, depth + 1);
    }
    public static void main(String[] args) {
        MenuNode root = new MenuNode("主選單");
        root.left = new MenuNode("檔案");
        root.right = new MenuNode("編輯");
        root.left.left = new MenuNode("開啟");
        root.left.right = new MenuNode("儲存");
        root.right.left = new MenuNode("復原");
        root.right.right = new MenuNode("設定");
        root.right.right.left = new MenuNode("字型");
        display(root, 0);
        System.out.println("contains 儲存=" + contains(root, "儲存"));
        System.out.println("contains 列印=" + contains(root, "列印"));
        System.out.println("findDepth 主選單=" + findDepth(root, "主選單"));
        System.out.println("findDepth 字型=" + findDepth(root, "字型"));
        System.out.println("findDepth 列印=" + findDepth(root, "列印"));
        System.out.println("countLeaves=" + countLeaves(root));
        System.out.println("empty contains=" + contains(null, "檔案") + " findDepth=" + findDepth(null, "檔案") + " countLeaves=" + countLeaves(null));
    }
}
