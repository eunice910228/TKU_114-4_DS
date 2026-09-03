import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
public class MetroTransferPath {
    private final Map<String, List<String>> lines = new LinkedHashMap<>();
    boolean addStation(String name) {
        if (name == null || name.isBlank()) {
            return false;
        }
        if (lines.containsKey(name)) {
            return false;
        }
        lines.put(name, new ArrayList<>());
        return true;
    }
    boolean connect(String a, String b) {
        if (!lines.containsKey(a) || !lines.containsKey(b) || a.equals(b)) {
            return false;
        }
        if (lines.get(a).contains(b)) {
            return false;
        }
        lines.get(a).add(b);
        lines.get(b).add(a);
        return true;
    }
    List<String> shortestPath(String start, String target) {
        List<String> path = new ArrayList<>();
        if (!lines.containsKey(start) || !lines.containsKey(target)) {
            return path;
        }
        if (start.equals(target)) {
            path.add(start);
            return path;
        }
        Map<String, String> predecessor = new LinkedHashMap<>();
        Queue<String> queue = new ArrayDeque<>();
        queue.offer(start);
        predecessor.put(start, null);
        while (!queue.isEmpty()) {
            String current = queue.poll();
            for (String next : lines.get(current)) {
                if (!predecessor.containsKey(next)) {
                    predecessor.put(next, current);
                    queue.offer(next);
                }
            }
        }
        if (!predecessor.containsKey(target)) {
            return path;
        }
        String step = target;
        while (step != null) {
            path.add(step);
            step = predecessor.get(step);
        }
        Collections.reverse(path);
        return path;
    }
    void pathReport(String start, String target) {
        List<String> path = shortestPath(start, target);
        System.out.print(start + "->" + target + "=");
        if (path.isEmpty()) {
            System.out.println("無法到達");
            return;
        }
        System.out.println(path + "，經過" + (path.size() - 1) + "段");
    }
    public static void main(String[] args) {
        MetroTransferPath metro = new MetroTransferPath();
        for (String name : new String[]{ "台北", "中山", "松山", "板橋", "新店", "淡水" }) {
            metro.addStation(name);
        }
        metro.connect("台北", "中山");
        metro.connect("中山", "松山");
        metro.connect("台北", "板橋");
        metro.connect("板橋", "新店");
        metro.connect("中山", "新店");
        System.out.println("重複連線 台北-中山=" + metro.connect("台北", "中山"));
        System.out.println("不存在站 台北-高雄=" + metro.connect("台北", "高雄"));
        System.out.println("null站名 addStation=" + metro.addStation(null));
        System.out.println("空路網 shortestPath=" + new MetroTransferPath().shortestPath("台北", "松山"));
        System.out.println("最少站數路徑");
        metro.pathReport("台北", "松山");
        metro.pathReport("台北", "新店");
        metro.pathReport("松山", "板橋");
        metro.pathReport("台北", "台北");
        metro.pathReport("台北", "淡水");
        metro.pathReport("台北", "高雄");
    }
}
