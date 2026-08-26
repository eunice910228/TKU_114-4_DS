class ScoreEntry {
    int score;
    String studentId;

    ScoreEntry(int score, String studentId) {
        this.score = score;
        this.studentId = studentId;
    }

    @Override
    public String toString() {
        return studentId + "(" + score + "分)";
    }
}

class ScoreNode {
    ScoreEntry entry;
    ScoreNode left;
    ScoreNode right;

    ScoreNode(ScoreEntry entry) {
        this.entry = entry;
    }
}

public class ScoreRangeBst {
    private ScoreNode root;

    // 複合順序：先比 score，同分再比 studentId
    private int compare(ScoreEntry a, ScoreEntry b) {
        if (a.score != b.score) {
            return a.score - b.score;
        }
        return a.studentId.compareTo(b.studentId);
    }

    boolean add(int score, String studentId) {
        ScoreEntry entry = new ScoreEntry(score, studentId);
        if (contains(entry)) {
            return false;
        }
        root = add(root, entry);
        return true;
    }

    private ScoreNode add(ScoreNode node, ScoreEntry entry) {
        if (node == null) {
            return new ScoreNode(entry);
        }
        if (compare(entry, node.entry) < 0) {
            node.left = add(node.left, entry);
        } else {
            node.right = add(node.right, entry);
        }
        return node;
    }

    private boolean contains(ScoreEntry entry) {
        ScoreNode current = root;
        while (current != null) {
            int result = compare(entry, current.entry);
            if (result == 0) {
                return true;
            }
            current = (result < 0) ? current.left : current.right;
        }
        return false;
    }

    void inorder() {
        System.out.print("  ");
        inorder(root);
        System.out.println();
    }

    private void inorder(ScoreNode node) {
        if (node == null) {
            return;
        }
        inorder(node.left);
        System.out.print(node.entry + " ");
        inorder(node.right);
    }

    void printScoreRange(int low, int high) {
        System.out.print("分數 " + low + "~" + high + "：");
        if (low > high) {
            System.out.println("(low>high，查無結果)");
            return;
        }
        printScoreRange(root, low, high);
        System.out.println();
    }

    private void printScoreRange(ScoreNode node, int low, int high) {
        if (node == null) {
            return;
        }
        if (node.entry.score >= low) {
            printScoreRange(node.left, low, high);
        }
        if (node.entry.score >= low && node.entry.score <= high) {
            System.out.print(node.entry + " ");
        }
        if (node.entry.score <= high) {
            printScoreRange(node.right, low, high);
        }
    }

    public static void main(String[] args) {
        ScoreRangeBst tree = new ScoreRangeBst();
        System.out.println("add 分數：85 ID：A02 = " + tree.add(85, "A02"));
        System.out.println("add 分數：92 ID：A01 = " + tree.add(92, "A01"));
        System.out.println("add 分數：85 ID：A03 = " + tree.add(85, "A03") + " 同分不同id，可加入");
        System.out.println("add 分數：73 ID：A05 = " + tree.add(73, "A05"));
        System.out.println("add 分數：85 ID：A02 = " + tree.add(85, "A02") + " 同分同id，重複");
        System.out.println("add 分數：58 ID：A04 = " + tree.add(58, "A04"));
        System.out.println("add 分數：85 ID：A00 = " + tree.add(85, "A00"));
        System.out.println("分數升冪，同分依學號");
        tree.inorder();
        tree.printScoreRange(70, 90);
        tree.printScoreRange(85, 85);
        tree.printScoreRange(95, 99);
        tree.printScoreRange(90, 70);
    }
}
