import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
public class BfsLayerReport {
    private final Map<String, List<String>> graph = new LinkedHashMap<>();
    boolean addVertex(String name) {
        if (name == null || name.isBlank()) {
            return false;
        }
        if (graph.containsKey(name)) {
            return false;
        }
        graph.put(name, new ArrayList<>());
        return true;
    }
    boolean addEdge(String a, String b) {
        if (!graph.containsKey(a) || !graph.containsKey(b) || a.equals(b)) {
            return false;
        }
        if (graph.get(a).contains(b)) {
            return false;
        }
        graph.get(a).add(b);
        graph.get(b).add(a);
        return true;
    }
    Map<String, Integer> distanceFrom(String start) {
        Map<String, Integer> distance = new LinkedHashMap<>();
        if (!graph.containsKey(start)) {
            return distance;
        }
        Queue<String> queue = new ArrayDeque<>();
        queue.offer(start);
        distance.put(start, 0);
        while (!queue.isEmpty()) {
            String current = queue.poll();
            for (String next : graph.get(current)) {
                if (!distance.containsKey(next)) {
                    distance.put(next, distance.get(current) + 1);
                    queue.offer(next);
                }
            }
        }
        return distance;
    }
    void layerReport(String start) {
        System.out.println("從" + start + "出發的最少edge數");
        Map<String, Integer> distance = distanceFrom(start);
        if (distance.isEmpty()) {
            System.out.println("查無此vertex");
            return;
        }
        for (String vertex : graph.keySet()) {
            Integer d = distance.get(vertex);
            System.out.println(vertex + "=" + (d == null ? "不可達" : d));
        }
    }
    public static void main(String[] args) {
        BfsLayerReport report = new BfsLayerReport();
        for (String name : new String[]{ "A", "B", "C", "D", "E", "F" }) {
            report.addVertex(name);
        }
        System.out.println("重複addVertex A=" + report.addVertex("A"));
        System.out.println("A-B=" + report.addEdge("A", "B"));
        System.out.println("A-C=" + report.addEdge("A", "C"));
        System.out.println("B-D=" + report.addEdge("B", "D"));
        System.out.println("C-D=" + report.addEdge("C", "D"));
        System.out.println("D-E=" + report.addEdge("D", "E"));
        System.out.println("重複A-B=" + report.addEdge("A", "B"));
        System.out.println("不存在vertex A-Z=" + report.addEdge("A", "Z"));
        System.out.println("null vertex addVertex=" + report.addVertex(null));
        System.out.println("空graph距離表=" + new BfsLayerReport().distanceFrom("A"));
        report.layerReport("A");
        report.layerReport("F");
        report.layerReport("Z");
    }
}
