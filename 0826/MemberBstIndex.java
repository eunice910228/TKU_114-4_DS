class Member {
    int memberId;
    String name;
    String email;
    Member(int memberId, String name, String email) {
        this.memberId = memberId;
        this.name = name;
        this.email = email;
    }
    @Override
    public String toString() {
        return memberId + " " + name + " " + email;
    }
}
class MemberNode {
    Member member;
    MemberNode left;
    MemberNode right;
    MemberNode(Member member) {
        this.member = member;
    }
}
public class MemberBstIndex {
    private MemberNode root;
    boolean add(Member member) {
        if (member == null || member.email == null || member.email.isBlank()) {
            return false;
        }
        if (find(member.memberId) != null) {
            return false;
        }
        root = add(root, member);
        return true;
    }
    private MemberNode add(MemberNode node, Member member) {
        if (node == null) {
            return new MemberNode(member);
        }
        if (member.memberId < node.member.memberId) {
            node.left = add(node.left, member);
        } else {
            node.right = add(node.right, member);
        }
        return node;
    }
    Member find(int memberId) {
        MemberNode current = root;
        while (current != null) {
            if (memberId == current.member.memberId) {
                return current.member;
            }
            current = memberId < current.member.memberId ? current.left : current.right;
        }
        return null;
    }
    boolean updateEmail(int memberId, String email) {
        Member member = find(memberId);
        if (member == null || email == null || email.isBlank()) {
            return false;
        }
        member.email = email;
        return true;
    }
    boolean remove(int memberId) {
        if (find(memberId) == null) {
            return false;
        }
        root = remove(root, memberId);
        return true;
    }
    private MemberNode remove(MemberNode node, int memberId) {
        if (node == null) {
            return null;
        }
        if (memberId < node.member.memberId) {
            node.left = remove(node.left, memberId);
        } else if (memberId > node.member.memberId) {
            node.right = remove(node.right, memberId);
        } else {
            if (node.left == null) {
                return node.right;
            }
            if (node.right == null) {
                return node.left;
            }
            MemberNode successor = node.right;
            while (successor.left != null) {
                successor = successor.left;
            }
            node.member = successor.member;
            node.right = remove(node.right, successor.member.memberId);
        }
        return node;
    }
    void inorderReport() {
        System.out.println("會員清單");
        inorderReport(root);
    }
    private void inorderReport(MemberNode node) {
        if (node == null) {
            return;
        }
        inorderReport(node.left);
        System.out.println(node.member);
        inorderReport(node.right);
    }
    public static void main(String[] args) {
        MemberBstIndex index = new MemberBstIndex();
        System.out.println("add 2077=" + index.add(new Member(2077, "Eunice", "eunice@mail.com")));
        System.out.println("add 1378=" + index.add(new Member(1378, "Candice", "candice@mail.com")));
        System.out.println("add 7520=" + index.add(new Member(7520, "Bsy", "bsy@mail.com")));
        System.out.println("add 1741=" + index.add(new Member(1741, "Doris", "doris@mail.com")));
        System.out.println("重複id add 2077=" + index.add(new Member(2077, "test", "x@mail.com")));
        System.out.println("空白email add=" + index.add(new Member(9999, "test", "  ")));
        index.inorderReport();
        System.out.println("find 1378=" + index.find(1378));
        System.out.println("find 9999=" + index.find(9999));
        System.out.println("updateEmail 1378=" + index.updateEmail(1378, "candice@new.com"));
        System.out.println("updateEmail 空白=" + index.updateEmail(1378, " "));
        System.out.println("updateEmail missing=" + index.updateEmail(9999, "a@b.com"));
        System.out.println("remove 2077（root）=" + index.remove(2077));
        System.out.println("remove missing=" + index.remove(9999));
        index.inorderReport();
    }
}
