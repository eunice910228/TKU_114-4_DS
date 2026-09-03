import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
public class CampusNavigationSystem {
    private final Map<String, String> places = new HashMap<>();
    private final Map<String, List<String>> roads = new LinkedHashMap<>();
    boolean addPlace(String code, String name) {
        if (code == null || code.isBlank() || name == null || name.isBlank()) {
            return false;
        }
        if (places.containsKey(code)) {
            return false;
        }
        places.put(code, name);
        roads.put(code, new ArrayList<>());
        return true;
    }
    boolean connect(String a, String b) {
        if (!places.containsKey(a) || !places.containsKey(b) || a.equals(b)) {
            return false;
        }
        if (roads.get(a).contains(b)) {
            return false;
        }
        roads.get(a).add(b);
        roads.get(b).add(a);
        return true;
    }
    String nameOf(String code) {
        return places.get(code);
    }
    List<String> shortestPath(String start, String target) {
        List<String> path = new ArrayList<>();
        if (!places.containsKey(start) || !places.containsKey(target)) {
            return path;
        }
        if (start.equals(target)) {
            path.add(start);
            return path;
        }
        Map<String, String> previous = new HashMap<>();
        Queue<String> queue = new ArrayDeque<>();
        queue.offer(start);
        previous.put(start, null);
        while (!queue.isEmpty()) {
            String current = queue.poll();
            if (current.equals(target)) {
                break;
            }
            for (String next : roads.get(current)) {
                if (!previous.containsKey(next)) {
                    previous.put(next, current);
                    queue.offer(next);
                }
            }
        }
        if (!previous.containsKey(target)) {
            return path;
        }
        for (String at = target; at != null; at = previous.get(at)) {
            path.add(at);
        }
        Collections.reverse(path);
        return path;
    }
    void routeReport(String start, String target) {
        List<String> path = shortestPath(start, target);
        System.out.print(start + "->" + target + "=");
        if (path.isEmpty()) {
            System.out.println("無法到達");
            return;
        }
        List<String> names = new ArrayList<>();
        for (String code : path) {
            names.add(places.get(code));
        }
        System.out.println(names + "，經過" + (path.size() - 1) + "條路");
    }
    public static void main(String[] args) {
        CampusNavigationSystem campus = new CampusNavigationSystem();
        System.out.println("addPlace G 大門=" + campus.addPlace("G", "大門"));
        System.out.println("addPlace L 圖書館=" + campus.addPlace("L", "圖書館"));
        System.out.println("addPlace E 工學院=" + campus.addPlace("E", "工學院"));
        System.out.println("addPlace S 學生餐廳=" + campus.addPlace("S", "學生餐廳"));
        System.out.println("addPlace D 宿舍=" + campus.addPlace("D", "宿舍"));
        System.out.println("addPlace P 操場=" + campus.addPlace("P", "操場"));
        System.out.println("重複addPlace G=" + campus.addPlace("G", "重複"));
        System.out.println("空白名稱 addPlace=" + campus.addPlace("X", "  "));
        System.out.println("connect G-L=" + campus.connect("G", "L"));
        System.out.println("connect G-S=" + campus.connect("G", "S"));
        System.out.println("connect L-E=" + campus.connect("L", "E"));
        System.out.println("connect S-D=" + campus.connect("S", "D"));
        System.out.println("connect E-D=" + campus.connect("E", "D"));
        System.out.println("重複connect G-L=" + campus.connect("G", "L"));
        System.out.println("不存在地點 connect G-X=" + campus.connect("G", "X"));
        System.out.println("自己連自己 G-G=" + campus.connect("G", "G"));
        System.out.println("G->D=" + campus.shortestPath("G", "D"));
        System.out.println("G->P=" + campus.shortestPath("G", "P"));
        System.out.println("G->G=" + campus.shortestPath("G", "G"));
        System.out.println("不存在地點 G->X=" + campus.shortestPath("G", "X"));
        System.out.println("空系統=" + new CampusNavigationSystem().shortestPath("G", "D"));
        campus.routeReport("G", "D");
        campus.routeReport("G", "E");
        campus.routeReport("G", "P");
    }
}
