class Course {
    String code;
    String name;
    int credit;
    Course(String code, String name, int credit) {
        this.code = code;
        this.name = name;
        this.credit = credit;
    }
    @Override
    public String toString() {
        return code + " " + name + " " + credit + "學分";
    }
}
class CourseNode {
    Course course;
    CourseNode left;
    CourseNode right;

    CourseNode(Course course) {
        this.course = course;
    }
}
public class CourseBstIndex {
    private CourseNode root;
    boolean add(Course course) {
        if (course == null || course.credit < 1 || course.credit > 6) {
            return false;
        }
        if (find(course.code) != null) {
            return false;
        }
        root = add(root, course);
        return true;
    }
    private CourseNode add(CourseNode node, Course course) {
        if (node == null) {
            return new CourseNode(course);
        }
        if (course.code.compareTo(node.course.code) < 0) {
            node.left = add(node.left, course);
        } else {
            node.right = add(node.right, course);
        }
        return node;
    }
    Course find(String code) {
        CourseNode current = root;
        while (current != null) {
            int compare = code.compareTo(current.course.code);
            if (compare == 0) {
                return current.course;
            }
            current = (compare < 0) ? current.left : current.right;
        }
        return null;
    }
    boolean updateCredit(String code, int credit) {
        Course course = find(code);
        if (course == null || credit < 1 || credit > 6) {
            return false;
        }
        course.credit = credit;
        return true;
    }
    boolean remove(String code) {
        if (find(code) == null) {
            return false;
        }
        root = remove(root, code);
        return true;
    }
    private CourseNode remove(CourseNode node, String code) {
        if (node == null) {
            return null;
        }
        int compare = code.compareTo(node.course.code);
        if (compare < 0) {
            node.left = remove(node.left, code);
        } else if (compare > 0) {
            node.right = remove(node.right, code);
        } else {
            if (node.left == null) {
                return node.right;
            }
            if (node.right == null) {
                return node.left;
            }
            CourseNode successor = node.right;
            while (successor.left != null) {
                successor = successor.left;
            }
            node.course = successor.course;
            node.right = remove(node.right, successor.course.code);
        }
        return node;
    }
    void rangeQuery(String low, String high) {
        System.out.println("代碼範圍" + low + "~" + high);
        rangeQuery(root, low, high);
    }
    private void rangeQuery(CourseNode node, String low, String high) {
        if (node == null) {
            return;
        }
        if (node.course.code.compareTo(low) > 0) {
            rangeQuery(node.left, low, high);
        }
        if (node.course.code.compareTo(low) >= 0 && node.course.code.compareTo(high) <= 0) {
            System.out.println(node.course);
        }
        if (node.course.code.compareTo(high) < 0) {
            rangeQuery(node.right, low, high);
        }
    }
    void sortedReport() {
        System.out.println("課程清單（依代碼排序）");
        sortedReport(root);
    }
    private void sortedReport(CourseNode node) {
        if (node == null) {
            return;
        }
        sortedReport(node.left);
        System.out.println(node.course);
        sortedReport(node.right);
    }
    public static void main(String[] args) {
        CourseBstIndex index = new CourseBstIndex();
        System.out.println("add M201=" + index.add(new Course("M201", "資料結構", 3)));
        System.out.println("add M101=" + index.add(new Course("M101", "數理統計", 3)));
        System.out.println("add M301=" + index.add(new Course("M301", "微積分", 3)));
        System.out.println("add M150=" + index.add(new Course("M150", "離散數學", 2)));
        System.out.println("add M250=" + index.add(new Course("M250", "高等微積分", 3)));
        System.out.println("重複 add M101=" + index.add(new Course("M101", "重複", 3)));
        System.out.println("credit 0 add M123="+index.add(new Course("M123", "違規", 0)));
        System.out.println("credit 7 add M321="+index.add(new Course("M321", "違規", 7)));
        index.sortedReport();
        System.out.println("find M250=" + index.find("M250"));
        System.out.println("find M123=" + index.find("M123"));
        System.out.println("updateCredit M150改3="+index.updateCredit("M150", 3));
        System.out.println("updateCredit M150改9="+index.updateCredit("M150", 9));
        System.out.println("updateCredit M123="+index.updateCredit("M123", 3));
        index.rangeQuery("M150", "M250");
        System.out.println("remove M201（root）="+index.remove("M201"));
        System.out.println("remove M123=" + index.remove("M123"));
        index.sortedReport();
    }
}
