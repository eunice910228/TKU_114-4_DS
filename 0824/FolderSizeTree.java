class FolderNode {
    String name;
    int ownSize;
    FolderNode left;
    FolderNode right;

    FolderNode(String name, int ownSize) {
        this.name = name;
        this.ownSize = ownSize;
    }
}

public class FolderSizeTree {
    // postorder：先算左右 subtree，再加自己
    static int subtreeSize(FolderNode node) {
        if (node == null) {
            return 0;
        }
        int left = subtreeSize(node.left);
        int right = subtreeSize(node.right);
        return left + right + node.ownSize;
    }
    public static void main(String[] args) {
        FolderNode root = new FolderNode("root", 10);
        root.left = new FolderNode("photos", 500);
        root.right = new FolderNode("docs", 40);
        root.left.left = new FolderNode("2025", 1200);
        root.left.right = new FolderNode("2026", 800);
        root.right.left = new FolderNode("work", 300);
        root.right.right = new FolderNode("school", 150);
        System.out.println("總大小 = " + subtreeSize(root) + " KB");
        System.out.println("photos subtree = " + subtreeSize(root.left) + " KB");
        System.out.println("empty tree = " + subtreeSize(null) + " KB");
    }
}
