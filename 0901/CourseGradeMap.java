import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class CourseGradeMap {
    private final Map<String, List<Integer>> grades = new HashMap<>();
    boolean add(String course, int score) {
        if (course == null || course.isBlank() || score < 0 || score > 100) {
            return false;
        }
        List<Integer> list = grades.get(course);
        if (list == null) {
            list = new ArrayList<>();
            grades.put(course, list);
        }
        list.add(score);
        return true;
    }
    double average(String course) {
        List<Integer> list = grades.get(course);
        if (list == null || list.isEmpty()) {
            return 0.0;
        }
        int total = 0;
        for (int score : list) {
            total += score;
        }
        return (double) total / list.size();
    }
    Integer highest(String course) {
        List<Integer> list = grades.get(course);
        if (list == null || list.isEmpty()) {
            return null;
        }
        return Collections.max(list);
    }
    void report() {
        System.out.println("成績報告");
        List<String> courses = new ArrayList<>(grades.keySet());
        Collections.sort(courses);
        for (String course : courses) {
            System.out.println(course + " " + grades.get(course) + " 平均=" + average(course) + " 最高=" + highest(course));
        }
    }
    public static void main(String[] args) {
        CourseGradeMap map = new CourseGradeMap();
        System.out.println("add 微積分 88=" + map.add("微積分", 88));
        System.out.println("add 微積分 72=" + map.add("微積分", 72));
        System.out.println("add 線性代數 95=" + map.add("線性代數", 95));
        System.out.println("add 離散數學 60=" + map.add("離散數學", 60));
        System.out.println("add 離散數學 101=" + map.add("離散數學", 101));
        System.out.println("空白課號=" + map.add("  ", 80));
        map.report();
        System.out.println("missing平均=" + map.average("微分方程") + " 最高=" + map.highest("微分方程"));
    }
}