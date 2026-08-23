import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

class Student {
    private final String studentId;
    private final String name;
    private int score;
    private final Set<String> tags = new HashSet<>();

    Student(String studentId, String name, int score) {
        this.studentId = studentId;
        this.name = name;
        this.score = Math.max(0, Math.min(100, score));
    }

    String getStudentId() {
        return studentId;
    }

    int getScore() {
        return score;
    }

    void setScore(int score) {
        this.score = Math.max(0, Math.min(100, score));
    }

    void addTag(String tag) {
        if (tag != null && !tag.isBlank()) {
            tags.add(tag.toLowerCase());
        }
    }

    boolean hasTag(String tag) {
        return tag != null && tags.contains(tag.toLowerCase());
    }

    String grade() {
        if (score >= 90) {
            return "A";
        }
        if (score >= 80) {
            return "B";
        }
        if (score >= 70) {
            return "C";
        }
        if (score >= 60) {
            return "D";
        }
        return "F";
    }

    @Override
    public String toString() {
        return studentId + " " + name + " score=" + score + " (" + grade() + ") tags=" + tags;
    }
}

class CourseManager {
    private final List<Student> order = new ArrayList<>();
    private final Set<String> studentIds = new HashSet<>();
    private final Map<String, Student> byId = new HashMap<>();

    boolean enroll(Student student) {
        if (student == null || !studentIds.add(student.getStudentId())) {
            return false;
        }
        order.add(student);
        byId.put(student.getStudentId(), student);
        return true;
    }

    boolean updateScore(String studentId, int score) {
        Student student = byId.get(studentId);
        if (student == null) {
            return false;
        }
        student.setScore(score);
        return true;
    }

    List<Student> findByTag(String tag) {
        List<Student> result = new ArrayList<>();
        for (Student student : order) {
            if (student.hasTag(tag)) {
                result.add(student);
            }
        }
        return result;
    }

    Map<String, Integer> scoreDistribution() {
        Map<String, Integer> distribution = new HashMap<>();
        for (String grade : new String[]{"A", "B", "C", "D", "F"}) {
            distribution.put(grade, 0);
        }
        for (Student student : order) {
            distribution.put(student.grade(), distribution.get(student.grade()) + 1);
        }
        return distribution;
    }

    List<Student> top(int count) {
        List<Student> ranking = new ArrayList<>(order);
        ranking.sort(Comparator.comparingInt(Student::getScore)
                .reversed()
                .thenComparing(Student::getStudentId));
        List<Student> result = new ArrayList<>();
        for (int i = 0; i < ranking.size() && i < count; i++) {
            result.add(ranking.get(i));
        }
        return result;
    }

    int removeBelow(int minimum) {
        int before = order.size();
        order.removeIf(student -> student.getScore() < minimum);
        studentIds.clear();
        byId.clear();
        for (Student student : order) {
            studentIds.add(student.getStudentId());
            byId.put(student.getStudentId(), student);
        }
        return before - order.size();
    }

    int size() {
        return order.size();
    }

    List<Student> getOrder() {
        return order;
    }

    void printStructures() {
        System.out.println("  List size = " + order.size()
                + "，Set size = " + studentIds.size()
                + "，Map size = " + byId.size());
        System.out.println("  Set 內容 = " + studentIds);
        System.out.println("  Map keys = " + byId.keySet());
    }
}

public class CourseCollectionManager {

    static <T> void printList(List<T> list) {
        if (list.isEmpty()) {
            System.out.println("    (空)");
        }
        for (T value : list) {
            System.out.println("    " + value);
        }
    }

    public static void main(String[] args) {
        CourseManager manager = new CourseManager();

        Student eunice = new Student("A01", "Eunice", 92);
        eunice.addTag("Java");
        eunice.addTag("oop");
        Student bsy = new Student("A02", "Bsy", 85);
        bsy.addTag("java");
        Student candice = new Student("A03", "Candice", 85);
        candice.addTag("   ");
        candice.addTag(null);
        Student doris = new Student("A04", "Doris", 58);
        doris.addTag("oop");

        Student ella = new Student("A05", "Ella", 73);
        ella.addTag("java");
        ella.addTag("db");
        Student frank = new Student("A06", "Frank", 45);
        frank.addTag("db");
        System.out.println("報名");
        System.out.println("  Eunice = " + manager.enroll(eunice));
        System.out.println("  Bsy = " + manager.enroll(bsy));
        System.out.println("  Candice = " + manager.enroll(candice));
        System.out.println("  Doris = " + manager.enroll(doris));
        System.out.println("  Ella = " + manager.enroll(ella));
        System.out.println("  Frank = " + manager.enroll(frank));
        System.out.println("  重複學號 A01 = "
                + manager.enroll(new Student("A01", "test", 100)));
        System.out.println("  null = " + manager.enroll(null));
        printList(manager.getOrder());
        System.out.println("updateScore");
        System.out.println("  A04 改 78 = " + manager.updateScore("A04", 78));
        System.out.println("  A06 改 61 = " + manager.updateScore("A06", 61));
        System.out.println("  A19 改 90 = " + manager.updateScore("A19", 90));
        System.out.println("  A01 改 150 = " + manager.updateScore("A01", 150));
        printList(manager.getOrder());
        

        System.out.println("findByTag");
        System.out.println("  java = " + manager.findByTag("java").size() + " 人");
        printList(manager.findByTag("java"));
        System.out.println("  db = " + manager.findByTag("db").size() + " 人");
        System.out.println("  python = " + manager.findByTag("python").size() + " 人");
        System.out.println("  null = " + manager.findByTag(null).size() + " 人");
        
        System.out.println("scoreDistribution");
        Map<String, Integer> distribution = manager.scoreDistribution();
        int total = 0;
        for (String grade : new String[]{"A", "B", "C", "D", "F"}) {
            System.out.println("  " + grade + " = " + distribution.get(grade) + " 人");
            total = total + distribution.get(grade);
        }
        System.out.println("  合計 " + total + " 人，名單人數 " + manager.size());
        System.out.println("top");
        printList(manager.top(3));
        System.out.println("  top(100) = " + manager.top(100).size() + " 筆");  
        System.out.println("removeBelow(70) 移除 " + manager.removeBelow(70) + " 人");
        printList(manager.getOrder());
        manager.printStructures();
        System.out.println("  updateScore(A06, 90) = " + manager.updateScore("A06", 90));
        System.out.println("removeBelow(101) 移除 " + manager.removeBelow(101) + " 人");
        printList(manager.getOrder());
        manager.printStructures();
    }
}