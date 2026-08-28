import java.util.ArrayList;
import java.util.List;

public class Q12_StudentBstSystem {
    public static class Student {
        private final int id;
        private final String name;
        private int score;
        public Student(int id, String name, int score) {
            if (id <= 0) {
                throw new IllegalArgumentException("id 必須大於 0");
            }
            if (name == null || name.isBlank()) {
                throw new IllegalArgumentException("name 不可為空");
            }
            this.id = id;
            this.name = name;
            this.score = clamp(score);
        }
        private static int clamp(int score) {
            return Math.max(0, Math.min(100, score));
        }
        public int getId() {
            return id;
        }
        public String getName() {
            return name;
        }
        public int getScore() {
            return score;
        }
        void applyScore(int score) {
            this.score = clamp(score);
        }
        @Override
        public String toString() {
            return id + "|" + name + "|" + score;
        }
    }
    private static class Node {
        Student student;
        Node left;
        Node right;

        Node(Student student) {
            this.student = student;
        }
    }
    private Node root;
    public boolean add(Student student) {
        if (student == null || find(student.getId()) != null) {
            return false;
        }
        root = add(root, student);
        return true;
    }
    private Node add(Node node, Student student) {
        if (node == null) {
            return new Node(student);
        }
        if (student.getId() < node.student.getId()) {
            node.left = add(node.left, student);
        } else {
            node.right = add(node.right, student);
        }
        return node;
    }
    public Student find(int id) {
        Node current = root;
        while (current != null) {
            if (id == current.student.getId()) {
                return current.student;
            }
            current = (id < current.student.getId()) ? current.left : current.right;
        }
        return null;
    }
    public boolean updateScore(int id, int score) {
        Student student = find(id);
        if (student == null) {
            return false;
        }
        student.applyScore(score);
        return true;
    }
    public boolean remove(int id) {
        if (find(id) == null) {
            return false;
        }
        root = remove(root, id);
        return true;
    }
    private Node remove(Node node, int id) {
        if (node == null) {
            return null;
        }
        if (id < node.student.getId()) {
            node.left = remove(node.left, id);
        } else if (id > node.student.getId()) {
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
            node.student = successor.student;
            node.right = remove(node.right, successor.student.getId());
        }
        return node;
    }
    public List<Student> studentsBetween(int lowId, int highId) {
        List<Student> result = new ArrayList<>();
        if (lowId > highId) {
            return result;
        }
        studentsBetween(root, lowId, highId, result);
        return result;
    }
    private void studentsBetween(Node node, int lowId, int highId, List<Student> result) {
        if (node == null) {
            return;
        }
        if (node.student.getId() > lowId) {
            studentsBetween(node.left, lowId, highId, result);
        }
        if (node.student.getId() >= lowId && node.student.getId() <= highId) {
            result.add(node.student);
        }
        if (node.student.getId() < highId) {
            studentsBetween(node.right, lowId, highId, result);
        }
    }
    public List<Student> inorder() {
        List<Student> result = new ArrayList<>();
        inorder(root, result);
        return result;
    }
    private void inorder(Node node, List<Student> result) {
        if (node == null) {
            return;
        }
        inorder(node.left, result);
        result.add(node.student);
        inorder(node.right, result);
    }
    public static void main(String[] args) {
        Q12_StudentBstSystem system = new Q12_StudentBstSystem();
        system.add(new Student(300, "Mina", 78));
        system.add(new Student(100, "Leo", 84));
        system.add(new Student(500, "Nora", 105));
        system.add(new Student(200, "Ivy", 69));
        System.out.println(system.updateScore(200, 88));
        System.out.println(system.studentsBetween(150, 500));
        System.out.println(system.remove(300));
        System.out.println(system.inorder());
    }
}
