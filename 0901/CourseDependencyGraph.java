import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
public class CourseDependencyGraph {
    private final Map<String, List<String>> unlocks = new LinkedHashMap<>();
    boolean addCourse(String course) {
        if (course == null || course.isBlank() || unlocks.containsKey(course)) {
            return false;
        }
        unlocks.put(course, new ArrayList<>());
        return true;
    }
    boolean addPrerequisite(String prereq, String course) {
        if (!unlocks.containsKey(prereq) || !unlocks.containsKey(course) || prereq.equals(course)) {
            return false;
        }
        if (unlocks.get(prereq).contains(course)) {
            return false;
        }
        unlocks.get(prereq).add(course);
        return true;
    }
    List<String> prerequisitesOf(String course) {
        List<String> result = new ArrayList<>();
        if (!unlocks.containsKey(course)) {
            return result;
        }
        for (Map.Entry<String, List<String>> entry : unlocks.entrySet()) {
            if (entry.getValue().contains(course)) {
                result.add(entry.getKey());
            }
        }
        return result;
    }
    List<String> followUpsOf(String course) {
        List<String> result = new ArrayList<>();
        if (unlocks.containsKey(course)) {
            result.addAll(unlocks.get(course));
        }
        return result;
    }
    int inDegree(String course) {
        return prerequisitesOf(course).size();
    }
    int outDegree(String course) {
        return followUpsOf(course).size();
    }
    void report() {
        System.out.println("課程相依報告");
        for (String course : unlocks.keySet()) {
            System.out.println(course + " 先修=" + prerequisitesOf(course) + " 後續=" + followUpsOf(course) + " in=" + inDegree(course) + " out=" + outDegree(course));
        }
    }
    public static void main(String[] args) {
        CourseDependencyGraph graph = new CourseDependencyGraph();
        for (String course : new String[]{ "微積分", "線性代數", "高等微積分", "微分方程", "數理統計", "離散數學" }) {
            graph.addCourse(course);
        }
        System.out.println("微積分->高等微積分=" + graph.addPrerequisite("微積分", "高等微積分"));
        System.out.println("微積分->微分方程=" + graph.addPrerequisite("微積分", "微分方程"));
        System.out.println("線性代數->微分方程=" + graph.addPrerequisite("線性代數", "微分方程"));
        System.out.println("高等微積分->數理統計=" + graph.addPrerequisite("高等微積分", "數理統計"));
        System.out.println("重複=" + graph.addPrerequisite("微積分", "高等微積分"));
        System.out.println("missing=" + graph.addPrerequisite("微積分", "統計學"));
        graph.report();
        System.out.println("missing in=" + graph.inDegree("統計學") + " out=" + graph.outDegree("統計學"));
    }
}