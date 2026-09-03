import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
public class Q05_StudentHashIndex {
    private final Map<String, Set<String>> byStudent = new HashMap<>();
    private final Map<String, Set<String>> byCourse = new HashMap<>();
    private int enrollmentCount;
    private String normalize(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return value.trim().toUpperCase();
    }
    public boolean enroll(String studentId, String courseId) {
        String student = normalize(studentId);
        String course = normalize(courseId);
        if (student == null || course == null) {
            return false;
        }
        Set<String> courses = byStudent.get(student);
        if (courses == null) {
            courses = new HashSet<>();
            byStudent.put(student, courses);
        }
        if (!courses.add(course)) {
            return false;
        }
        Set<String> students = byCourse.get(course);
        if (students == null) {
            students = new HashSet<>();
            byCourse.put(course, students);
        }
        students.add(student);
        enrollmentCount++;
        return true;
    }
    public boolean drop(String studentId, String courseId) {
        String student = normalize(studentId);
        String course = normalize(courseId);
        if (student == null || course == null) {
            return false;
        }
        Set<String> courses = byStudent.get(student);
        if (courses == null || !courses.remove(course)) {
            return false;
        }
        if (courses.isEmpty()) {
            byStudent.remove(student);
        }
        Set<String> students = byCourse.get(course);
        students.remove(student);
        if (students.isEmpty()) {
            byCourse.remove(course);
        }
        enrollmentCount--;
        return true;
    }
    public Set<String> coursesOf(String studentId) {
        String student = normalize(studentId);
        if (student == null || !byStudent.containsKey(student)) {
            return Set.of();
        }
        return Set.copyOf(byStudent.get(student));
    }
    public Set<String> studentsIn(String courseId) {
        String course = normalize(courseId);
        if (course == null || !byCourse.containsKey(course)) {
            return Set.of();
        }
        return Set.copyOf(byCourse.get(course));
    }
    public int enrollmentCount() {
        return enrollmentCount;
    }
    public static void main(String[] args) {
        Q05_StudentHashIndex index = new Q05_StudentHashIndex();
        System.out.println("enroll a01 ds=" + index.enroll(" a01 ", "ds"));
        System.out.println("enroll A01 JAVA=" + index.enroll("A01", "java"));
        System.out.println("enroll A02 DS=" + index.enroll("A02", "DS"));
        System.out.println("重複 enroll A01 DS=" + index.enroll("A01", " DS "));
        System.out.println("null enroll=" + index.enroll(null, "DS"));
        System.out.println("空白 enroll=" + index.enroll("A03", "  "));
        System.out.println("A01的課=" + index.coursesOf("a01"));
        System.out.println("DS的學生=" + index.studentsIn(" ds "));
        System.out.println("enrollmentCount=" + index.enrollmentCount());
        System.out.println("drop A01 DS=" + index.drop("A01", "DS"));
        System.out.println("重複 drop A01 DS=" + index.drop("A01", "DS"));
        System.out.println("drop A02 DS=" + index.drop("A02", "DS"));
        System.out.println("DS的學生（空key已移除）=" + index.studentsIn("DS"));
        System.out.println("enrollmentCount=" + index.enrollmentCount());
        Set<String> result = index.coursesOf("A01");
        boolean threw = false;
        try {
            result.add("Test");
        } catch (UnsupportedOperationException e) {
            threw = true;
        }
        System.out.println("回傳Set不可修改=" + threw);
    }
}
