import java.util.ArrayList;
import java.util.List;
class LabNode {
    int value;
    LabNode left;
    LabNode right;
    LabNode(int value) {
        this.value = value;
    }
}
public class TreeBugLab {
    static LabNode build() {
        LabNode root = new LabNode(50);
        root.left = new LabNode(30);
        root.right = new LabNode(70);
        root.left.left = new LabNode(20);
        return root;
    }
    static boolean buggySearch(LabNode node, int target) {
        while (node != null) {
            if (target == node.value) {
                return true;
            }
            node = target < node.value ? node.right : node.left;
        }
        return false;
    }
    static boolean fixedSearch(LabNode node, int target) {
        while (node != null) {
            if (target == node.value) {
                return true;
            }
            node = target < node.value ? node.left : node.right;
        }
        return false;
    }
    static void buggyInorder(LabNode node, List<Integer> result) {
        if (node == null) {
            return;
        }
        result.add(node.value);
        buggyInorder(node.left, result);
        buggyInorder(node.right, result);
    }
    static void fixedInorder(LabNode node, List<Integer> result) {
        if (node == null) {
            return;
        }
        fixedInorder(node.left, result);
        result.add(node.value);
        fixedInorder(node.right, result);
    }
    static LabNode buggyRemove(LabNode node, int value) {
        if (node == null) {
            return null;
        }
        if (value < node.value) {
            node.left = buggyRemove(node.left, value);
        } else if (value > node.value) {
            node.right = buggyRemove(node.right, value);
        } else {
            return null;
        }
        return node;
    }
    static LabNode fixedRemove(LabNode node, int value) {
        if (node == null) {
            return null;
        }
        if (value < node.value) {
            node.left = fixedRemove(node.left, value);
        } else if (value > node.value) {
            node.right = fixedRemove(node.right, value);
        } else {
            if (node.left == null) {
                return node.right;
            }
            if (node.right == null) {
                return node.left;
            }
            LabNode successor = node.right;
            while (successor.left != null) {
                successor = successor.left;
            }
            node.value = successor.value;
            node.right = fixedRemove(node.right, successor.value);
        }
        return node;
    }
    static boolean buggyValid(LabNode node) {
        if (node == null) {
            return true;
        }
        if (node.left != null && node.left.value >= node.value) {
            return false;
        }
        if (node.right != null && node.right.value <= node.value) {
            return false;
        }
        return buggyValid(node.left) && buggyValid(node.right);
    }
    static boolean fixedValid(LabNode node, int low, int high) {
        if (node == null) {
            return true;
        }
        if (node.value <= low || node.value >= high) {
            return false;
        }
        return fixedValid(node.left, low, node.value) && fixedValid(node.right, node.value, high);
    }
    public static void main(String[] args) {
        System.out.println("bug1 search方向相反");
        System.out.println("搜尋20 buggy=" + buggySearch(build(), 20) + " fixed=" + fixedSearch(build(), 20));
        System.out.println("bug2 inorder順序錯誤");
        List<Integer> a = new ArrayList<>();
        List<Integer> b = new ArrayList<>();
        buggyInorder(build(), a);
        fixedInorder(build(), b);
        System.out.println("buggy=" + a + " fixed=" + b);
        System.out.println("bug3 delete遺失child");
        List<Integer> c = new ArrayList<>();
        List<Integer> d = new ArrayList<>();
        fixedInorder(buggyRemove(build(), 30), c);
        fixedInorder(fixedRemove(build(), 30), d);
        System.out.println("刪30後 buggy=" + c + " fixed=" + d);
        System.out.println("bug4 validation只查直接child");
        LabNode bad = build();
        bad.left.right = new LabNode(60);
        System.out.println("左子樹藏60 buggy=" + buggyValid(bad) + " fixed=" + fixedValid(bad, Integer.MIN_VALUE, Integer.MAX_VALUE));
    }
}
