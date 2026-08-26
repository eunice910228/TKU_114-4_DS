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
    public static void main(String[] args) {
        OrgNode root = new OrgNode("註冊組");
        root.left = new OrgNode("課務組");
        root.right = new OrgNode("成績組");
        root.left.left = new OrgNode("學生會");
        root.left.right = new OrgNode("體育組");
        root.right.left = new OrgNode("文書組");
        root.right.right = new OrgNode("招生組");
        System.out.println("findParent(體育組) =" + findParent(root, "體育組"));
        System.out.println("findParent(註冊組) =" + findParent(root, "註冊組"));
        System.out.println("findDepth(文書組) =" + findDepth(root, "文書組"));
        System.out.println("findParent(出納組) =" + findParent(root, "出納組"));
        System.out.println("findDepth(出納組) =" + findDepth(root, "出納組"));
        System.out.println("empty tree findDepth =" + findDepth(null, "任何"));
    }
}
