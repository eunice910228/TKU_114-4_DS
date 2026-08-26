class Student {
    String studentId;
    String name;
    Student(String studentId, String name) {
        this.studentId = studentId;
        this.name = name;
    }
    @Override
    public String toString() {
        return studentId + " " + name;
    }
}

class StudentNode {
    Student student;
    StudentNode left;
    StudentNode right;
    StudentNode(Student student) {
        this.student = student;
    }
}

public class StudentBstIndex {
    private StudentNode root;
    boolean insert(Student student) {
        if (student == null || search(student.studentId) != null) {
            return false;
        }
        root = insert(root, student);
        return true;
    }
    private StudentNode insert(StudentNode node, Student student) {
        if (node == null) {
            return new StudentNode(student);
        }
        if (student.studentId.compareTo(node.student.studentId) < 0) {
            node.left = insert(node.left, student);
        } else {
            node.right = insert(node.right, student);
        }
        return node;
    }
    Student search(String studentId) {
        StudentNode current = root;
        while (current != null) {
            int compare = studentId.compareTo(current.student.studentId);
            if (compare == 0) {
                return current.student;
            }
            current = (compare < 0) ? current.left : current.right;
        }
        return null;
    }
    boolean delete(String studentId) {
        if (search(studentId) == null) {
            return false;
        }
        root = delete(root, studentId);
        return true;
    }
    private StudentNode delete(StudentNode node, String studentId) {
        if (node == null) {
            return null;
        }
        int compare = studentId.compareTo(node.student.studentId);
        if (compare < 0) {
            node.left = delete(node.left, studentId);
        } else if (compare > 0) {
            node.right = delete(node.right, studentId);
        } else {
            if (node.left == null) {
                return node.right;
            }
            if (node.right == null) {
                return node.left;
            }
            StudentNode successor = node.right;
            while (successor.left != null) {
                successor = successor.left;
            }
            node.student = successor.student;
            node.right = delete(node.right, successor.student.studentId);
        }
        return node;
    }

    void inorder() {
        inorder(root);
    }
    private void inorder(StudentNode node) {
        if (node == null) {
            return;
        }
        inorder(node.left);
        System.out.println("  " + node.student);
        inorder(node.right);
    }

    public static void main(String[] args) {
        StudentBstIndex index = new StudentBstIndex();
        System.out.println("insert A03 = " + index.insert(new Student("A03", "Candice")));
        System.out.println("insert A01 = " + index.insert(new Student("A01", "Eunice")));
        System.out.println("insert A05 = " + index.insert(new Student("A05", "Ella")));
        System.out.println("insert A02 = " + index.insert(new Student("A02", "Bsy")));
        System.out.println("insert A04 = " + index.insert(new Student("A04", "Doris")));
        System.out.println("重複 insert A01 = " + index.insert(new Student("A01", "冒名者")));
        System.out.println("inorder（依學號排序）");
        index.inorder();
        System.out.println("search A04 = " + index.search("A04"));
        System.out.println("search A99 = " + index.search("A99"));
        System.out.println("delete A03（root）= " + index.delete("A03"));
        System.out.println("delete A99 = " + index.delete("A99"));
        System.out.println("刪除後 inorder");
        index.inorder();
    }
}
