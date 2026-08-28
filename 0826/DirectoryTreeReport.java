class FsNode {
    String name;
    boolean isFile;
    int fileSize;
    FsNode left;
    FsNode right;

    FsNode(String name, boolean isFile, int fileSize) {
        this.name = name;
        this.isFile = isFile;
        this.fileSize = isFile ? Math.max(0, fileSize) : 0;
    }
}
public class DirectoryTreeReport {
    static int capacity(FsNode node) {
        if (node == null) {
            return 0;
        }
        int left = capacity(node.left);
        int right = capacity(node.right);
        return left + right + node.fileSize;
    }
    static void printDirectoryCapacity(FsNode node) {
        if (node == null) {
            return;
        }
        printDirectoryCapacity(node.left);
        printDirectoryCapacity(node.right);
        if (!node.isFile) {
            System.out.println(node.name + "總容量=" + capacity(node) + "KB");
        }
    }
    static int totalNodes(FsNode node) {
        if (node == null) {
            return 0;
        }
        return 1 + totalNodes(node.left) + totalNodes(node.right);
    }
    static int fileCount(FsNode node) {
        if (node == null) {
            return 0;
        }
        int here = node.isFile ? 1 : 0;
        return here + fileCount(node.left) + fileCount(node.right);
    }
    static int directoryCount(FsNode node) {
        if (node == null) {
            return 0;
        }
        int here = node.isFile ? 0 : 1;
        return here + directoryCount(node.left) + directoryCount(node.right);
    }
    static int height(FsNode node) {
        if (node == null) {
            return -1;
        }
        return 1 + Math.max(height(node.left), height(node.right));
    }
    static FsNode largestFile(FsNode node) {
        if (node == null) {
            return null;
        }
        FsNode best = node.isFile ? node : null;
        FsNode left = largestFile(node.left);
        if (left != null && (best == null || left.fileSize > best.fileSize)) {
            best = left;
        }
        FsNode right = largestFile(node.right);
        if (right != null && (best == null || right.fileSize > best.fileSize)) {
            best = right;
        }
        return best;
    }
    public static void main(String[] args) {
        FsNode root = new FsNode("root", false, 0);
        root.left = new FsNode("photos", false, 0);
        root.right = new FsNode("docs", false, 0);
        root.left.left = new FsNode("trip.jpg", true, 4200);
        root.left.right = new FsNode("cat.png", true, 900);
        root.right.left = new FsNode("report.pdf", true, 350);
        root.right.right = new FsNode("backup", false, 0);
        root.right.right.left = new FsNode("old.zip", true, 8000);
        printDirectoryCapacity(root);
        System.out.println("total node = " + totalNodes(root));
        System.out.println("file count = " + fileCount(root));
        System.out.println("directory count = " + directoryCount(root));
        System.out.println("height = " + height(root));
        FsNode biggest = largestFile(root);
        System.out.println("最大檔案=" + biggest.name + "（" + biggest.fileSize + "KB）");
        System.out.println("empty tree容量=" + capacity(null)
                + "，node=" + totalNodes(null) + "，height=" + height(null));
    }
}
