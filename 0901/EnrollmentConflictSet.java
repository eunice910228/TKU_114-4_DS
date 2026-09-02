import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
public class EnrollmentConflictSet {
    private final Set<String> records = new HashSet<>();
    private final Map<String, Set<String>> studentCourses = new HashMap<>();
    private final Map<String, Integer> courseCount = new HashMap<>();
    private final List<String> conflicts = new ArrayList<>();
    private String key(String studentId, String courseCode) {
        return studentId + "-" + courseCode;
    }
    boolean enroll(String studentId, String courseCode) {
        if (studentId == null || studentId.isBlank()
                || courseCode == null || courseCode.isBlank()) {
            return false;
        }
        String key = key(studentId, courseCode);
        if (!records.add(key)) {
            conflicts.add(key);
            return false;
        }
        Set<String> courses = studentCourses.get(studentId);
        if (courses == null) {
            courses = new HashSet<>();
            studentCourses.put(studentId, courses);
        }
        courses.add(courseCode);
        courseCount.put(courseCode, courseCount.getOrDefault(courseCode, 0) + 1);
        return true;
    }
    List<String> coursesOf(String studentId) {
        List<String> result = new ArrayList<>();
        Set<String> courses = studentCourses.get(studentId);
        if (courses != null) {
            result.addAll(courses);
            Collections.sort(result);
        }
        return result;
    }
    int countOf(String courseCode) {
        return courseCount.getOrDefault(courseCode, 0);
    }
    void conflictReport() {
        System.out.println("重複選課紀錄");
        for (String key : conflicts) {
            System.out.println(key);
        }
    }
    void courseReport() {
        System.out.println("每門課修課人數");
        List<String> codes = new ArrayList<>(courseCount.keySet());
        Collections.sort(codes);
        for (String code : codes) {
            System.out.println( code + "=" + courseCount.get(code) + "人");
        }
    }
    public static void main(String[] args) {
        EnrollmentConflictSet system = new EnrollmentConflictSet();
        System.out.println("A01選微積分=" + system.enroll("A01", "微積分"));
        System.out.println("A01選JAVA=" + system.enroll("A01", "JAVA"));
        System.out.println("A02選微積分=" + system.enroll("A02", "微積分"));
        System.out.println("A03選微積分=" + system.enroll("A03", "微積分"));
        System.out.println("A01重複選微積分=" + system.enroll("A01", "微積分"));
        System.out.println("A02重複選微積分=" + system.enroll("A02", "微積分"));
        System.out.println("null學號 enroll=" + system.enroll(null, "微積分"));
        System.out.println("空白課號 enroll=" + system.enroll("A01", "  "));
        System.out.println("A01的課=" + system.coursesOf("A01"));
        System.out.println("A77的課=" + system.coursesOf("A77"));
        System.out.println("微積分人數=" + system.countOf("微積分"));
        System.out.println("MATH人數=" + system.countOf("MATH"));
        System.out.println("空系統的課=" + new EnrollmentConflictSet().coursesOf("A01"));
        system.conflictReport();
        system.courseReport();
    }
}
