import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class Q11_BstHashDirectory {
    private static class Node {
        int id;
        Node left;
        Node right;
        Node(int id) {
            this.id = id;
        }
    }
    private Node root;
    private final Map<Integer, String> names = new HashMap<>();
    public boolean add(int id, String name) {
        if (id <= 0 || name == null || name.isBlank()) {
            return false;
        }
        if (names.containsKey(id)) {
            return false;
        }
        names.put(id, name.trim());
        root = add(root, id);
        return true;
    }
    private Node add(Node node, int id) {
        if (node == null) {
            return new Node(id);
        }
        if (id < node.id) {
            node.left = add(node.left, id);
        } else {
            node.right = add(node.right, id);
        }
        return node;
    }
    public String findName(int id) {
        return names.get(id);
    }
    public boolean remove(int id) {
        if (!names.containsKey(id)) {
            return false;
        }
        names.remove(id);
        root = remove(root, id);
        return true;
    }
    private Node remove(Node node, int id) {
        if (node == null) {
            return null;
        }
        if (id < node.id) {
            node.left = remove(node.left, id);
        } else if (id > node.id) {
            node.right = remove(node.right, id);
        } else {
            if (node.left == null) {
                return node.right;
            }
            if (node.right == null) {
                return node.left;
            }
            Node successor = node.right;
            while (successor.left != null) {
                successor = successor.left;
            }
            node.id = successor.id;
            node.right = remove(node.right, successor.id);
        }
        return node;
    }
    public List<Integer> idsBetween(int low, int high) {
        List<Integer> result = new ArrayList<>();
        if (low > high) {
            return result;
        }
        idsBetween(root, low, high, result);
        return result;
    }
    private void idsBetween(Node node, int low, int high, List<Integer> result) {
        if (node == null) {
            return;
        }
        if (node.id > low) {
            idsBetween(node.left, low, high, result);
        }
        if (node.id >= low && node.id <= high) {
            result.add(node.id);
        }
        if (node.id < high) {
            idsBetween(node.right, low, high, result);
        }
    }
    public int size() {
        return names.size();
    }
    public static void main(String[] args) {
        Q11_BstHashDirectory directory = new Q11_BstHashDirectory();
        System.out.println("add 2077 Eunice=" + directory.add(2077, "Eunice"));
        System.out.println("add 1378 Candice=" + directory.add(1378, " Candice "));
        System.out.println("add 7520 Bsy=" + directory.add(7520, "Bsy"));
        System.out.println("add 1741 Doris=" + directory.add(1741, "Doris"));
        System.out.println("重複 add 2077=" + directory.add(2077, "test"));
        System.out.println("id 0 add=" + directory.add(0, "違規"));
        System.out.println("空白name add=" + directory.add(1742, "  "));
        System.out.println("findName 1378=" + directory.findName(1378));
        System.out.println("findName 9999=" + directory.findName(9999));
        System.out.println("idsBetween 1500 7520=" + directory.idsBetween(1500, 7520));
        System.out.println("idsBetween 7520 1500=" + directory.idsBetween(7520, 1500));
        System.out.println("remove 2077（root）=" + directory.remove(2077));
        System.out.println("重複 remove 2077=" + directory.remove(2077));
        System.out.println("remove後 findName 2077=" + directory.findName(2077));
        System.out.println("remove後 idsBetween 1 9999=" + directory.idsBetween(1, 9999));
        System.out.println("size=" + directory.size());
    }
}