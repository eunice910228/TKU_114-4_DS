class ExprNode {
    String token;
    ExprNode left;
    ExprNode right;
    ExprNode(String token) {
        this.token = token;
    }
    ExprNode(String token, ExprNode left, ExprNode right) {
        this.token = token;
        this.left = left;
        this.right = right;
    }
    boolean isLeaf() {
        return left == null && right == null;
    }
}
public class TraversalSelector {
    static String prefix(ExprNode node) {
        if (node == null) {
            return "";
        }
        if (node.isLeaf()) {
            return node.token;
        }
        return node.token + " " + prefix(node.left) + " " + prefix(node.right);
    }
    static String infix(ExprNode node) {
        if (node == null) {
            return "";
        }
        if (node.isLeaf()) {
            return node.token;
        }
        return "(" + infix(node.left) + " " + node.token + " " + infix(node.right) + ")";
    }
    static String postfix(ExprNode node) {
        if (node == null) {
            return "";
        }
        if (node.isLeaf()) {
            return node.token;
        }
        return postfix(node.left) + " " + postfix(node.right) + " " + node.token;
    }
    public static void main(String[] args) {
        ExprNode expr = new ExprNode("*",
                new ExprNode("+", new ExprNode("3"), new ExprNode("4")),
                new ExprNode("-", new ExprNode("7"), new ExprNode("2")));
        System.out.println("prefix=" + prefix(expr));
        System.out.println("infix=" + infix(expr));
        System.out.println("postfix=" + postfix(expr));
        ExprNode single = new ExprNode("9");
        System.out.println("單一leaf infix=" + infix(single));
        System.out.println("empty infix=[" + infix(null) + "]");
    }
}
