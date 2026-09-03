import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
public class CoursePlanningGraph {
    private final Map<String, List<String>> unlocks = new LinkedHashMap<>();
    boolean addCourse(String course) {
        if (course == null || course.isBlank()) {
            return false;
        }
        if (unlocks.containsKey(course)) {
            return false;
        }
        unlocks.put(course, new ArrayList<>());
        return true;
    }
    boolean addPrerequisite(String prereq, String course) {
        if (!unlocks.containsKey(prereq) || !unlocks.containsKey(course)
                || prereq.equals(course)) {
            return false;
        }
        if (unlocks.get(prereq).contains(course)) {
            return false;
        }
        unlocks.get(prereq).add(course);
        return true;
    }
    boolean reachable(String from, String to) {
        if (!unlocks.containsKey(from) || !unlocks.containsKey(to)) {
            return false;
        }
        if (from.equals(to)) {
            return true;
        }
        Deque<String> stack = new ArrayDeque<>();
        Set<String> visited = new HashSet<>();
        stack.push(from);
        visited.add(from);
        while (!stack.isEmpty()) {
            String current = stack.pop();
            for (String next : unlocks.get(current)) {
                if (next.equals(to)) {
                    return true;
                }
                if (visited.add(next)) {
                    stack.push(next);
                }
            }
        }
        return false;
    }
    List<String> affectedCourses(String course) {
        List<String> result = new ArrayList<>();
        if (!unlocks.containsKey(course)) {
            return result;
        }
        Deque<String> stack = new ArrayDeque<>();
        Set<String> visited = new HashSet<>();
        stack.push(course);
        visited.add(course);
        while (!stack.isEmpty()) {
            String current = stack.pop();
            for (String next : unlocks.get(current)) {
                if (visited.add(next)) {
                    result.add(next);
                    stack.push(next);
                }
            }
        }
        Collections.sort(result);
        return result;
    }
    void impactReport(String course) {
        List<String> affected = affectedCourses(course);
        System.out.print(course + "異動影響=");
        if (affected.isEmpty()) {
            System.out.println("無後續課程");
            return;
        }
        System.out.println(affected + "，共" + affected.size() + "門");
    }
    public static void main(String[] args) {
        CoursePlanningGraph plan = new CoursePlanningGraph();
        System.out.println("addCourse 計概=" + plan.addCourse("計概"));
        System.out.println("addCourse 程式設計=" + plan.addCourse("程式設計"));
        System.out.println("addCourse 資料結構=" + plan.addCourse("資料結構"));
        System.out.println("addCourse 微積分=" + plan.addCourse("微積分"));
        System.out.println("addCourse 資料庫=" + plan.addCourse("資料庫"));
        System.out.println("addCourse 數理統計=" + plan.addCourse("數理統計"));
        System.out.println("重複addCourse 計概=" + plan.addCourse("計概"));
        System.out.println("空白課名 addCourse=" + plan.addCourse("  "));
        System.out.println("計概->程式設計=" + plan.addPrerequisite("計概", "程式設計"));
        System.out.println("程式設計->資料結構=" + plan.addPrerequisite("程式設計", "資料結構"));
        System.out.println("資料結構->微積分=" + plan.addPrerequisite("資料結構", "微積分"));
        System.out.println("程式設計->資料庫=" + plan.addPrerequisite("程式設計", "資料庫"));
        System.out.println("重複 計概->程式設計=" + plan.addPrerequisite("計概", "程式設計"));
        System.out.println("不存在課程 計概->AI=" + plan.addPrerequisite("計概", "AI"));
        System.out.println("自己當先修=" + plan.addPrerequisite("計概", "計概"));
        System.out.println("reachable 計概->微積分=" + plan.reachable("計概", "微積分"));
        System.out.println("reachable 微積分->計概=" + plan.reachable("微積分", "計概"));
        System.out.println("reachable 計概->計概=" + plan.reachable("計概", "計概"));
        System.out.println("reachable 計概->數理統計=" + plan.reachable("計概", "數理統計"));
        System.out.println("reachable 不存在課程=" + plan.reachable("計概", "AI"));
        System.out.println("循環測試 資料庫->計概=" + plan.addPrerequisite("資料庫", "計概"));
        System.out.println("循環後reachable 資料庫->微積分=" + plan.reachable("資料庫", "微積分"));
        plan.impactReport("計概");
        plan.impactReport("數理統計");
        plan.impactReport("AI");
        System.out.println("空系統affected=" + new CoursePlanningGraph().affectedCourses("計概"));
    }
}
