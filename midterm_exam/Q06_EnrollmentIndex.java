import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class Q06_EnrollmentIndex {
    private final Map<String, Set<String>> courseToStudents = new HashMap<>();
    private boolean invalid(String text) {
        return text == null || text.isBlank();
    }
    public boolean enroll(String courseCode, String studentId) {
        if (invalid(courseCode) || invalid(studentId)) {
            return false;
        }
        Set<String> students = courseToStudents.get(courseCode);
        if (students == null) {
            students = new HashSet<>();
            courseToStudents.put(courseCode, students);
        }
        return students.add(studentId);
    }
    public boolean drop(String courseCode, String studentId) {
        if (invalid(courseCode) || invalid(studentId)) {
            return false;
        }
        Set<String> students = courseToStudents.get(courseCode);
        if (students == null || !students.remove(studentId)) {
            return false;
        }
        if (students.isEmpty()) {
            courseToStudents.remove(courseCode);
        }
        return true;
    }
    public int courseSize(String courseCode) {
        Set<String> students = courseToStudents.get(courseCode);
        return students == null ? 0 : students.size();
    }
    public List<String> studentsOf(String courseCode) {
        List<String> result = new ArrayList<>();
        Set<String> students = courseToStudents.get(courseCode);
        if (students != null) {
            result.addAll(students);
            Collections.sort(result);
        }
        return result;
    }
    public List<String> coursesOf(String studentId) {
        List<String> result = new ArrayList<>();
        for (Map.Entry<String, Set<String>> entry : courseToStudents.entrySet()) {
            if (entry.getValue().contains(studentId)) {
                result.add(entry.getKey());
            }
        }
        Collections.sort(result);
        return result;
    }
    public Map<String, Integer> summary() {
        Map<String, Integer> result = new TreeMap<>();
        for (Map.Entry<String, Set<String>> entry : courseToStudents.entrySet()) {
            result.put(entry.getKey(), entry.getValue().size());
        }
        return result;
    }
    public static void main(String[] args) {
        Q06_EnrollmentIndex index = new Q06_EnrollmentIndex();
        index.enroll("DS", "S02");
        index.enroll("DS", "S01");
        index.enroll("JAVA", "S01");
        System.out.println(index.studentsOf("DS"));
        System.out.println(index.coursesOf("S01"));
        System.out.println(index.summary());
    }
}
