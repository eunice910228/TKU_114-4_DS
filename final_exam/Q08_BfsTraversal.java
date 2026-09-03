import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
public class Q08_BfsTraversal {
    public static List<String> bfs(Map<String, List<String>> graph, String start) {
        List<String> order = new ArrayList<>();
        if (graph == null || start == null || !graph.containsKey(start)) {
            return order;
        }
        Queue<String> queue = new ArrayDeque<>();
        Set<String> visited = new HashSet<>();
        queue.offer(start);
        visited.add(start);
        while (!queue.isEmpty()) {
            String current = queue.poll();
            order.add(current);
            for (String next : graph.getOrDefault(current, List.of())) {
                if (next != null && graph.containsKey(next) && visited.add(next)) {
                    queue.offer(next);
                }
            }
        }
        return order;
    }
    public static Map<String, Integer> distanceFrom(Map<String, List<String>> graph, String start) {
        Map<String, Integer> distance = new LinkedHashMap<>();
        if (graph == null || start == null || !graph.containsKey(start)) {
            return distance;
        }
        Queue<String> queue = new ArrayDeque<>();
        queue.offer(start);
        distance.put(start, 0);
        while (!queue.isEmpty()) {
            String current = queue.poll();
            for (String next : graph.getOrDefault(current, List.of())) {
                if (next != null && graph.containsKey(next) && !distance.containsKey(next)) {
                    distance.put(next, distance.get(current) + 1);
                    queue.offer(next);
                }
            }
        }
        return distance;
    }
    public static void main(String[] args) {
        Map<String, List<String>> graph = new LinkedHashMap<>();
        graph.put("A", List.of("B", "C"));
        graph.put("B", List.of("A", "D"));
        graph.put("C", List.of("A", "D"));
        graph.put("D", List.of("B", "C", "E"));
        graph.put("E", List.of("D"));
        graph.put("F", List.of());
        System.out.println("bfs A=" + bfs(graph, "A"));
        System.out.println("bfs F=" + bfs(graph, "F"));
        System.out.println("bfs missing=" + bfs(graph, "X"));
        System.out.println("bfs null graph=" + bfs(null, "A"));
        System.out.println("distance A=" + distanceFrom(graph, "A"));
        System.out.println("distance F=" + distanceFrom(graph, "F"));
        System.out.println("distance missing=" + distanceFrom(graph, "X"));
        System.out.println("輸入未被修改=" + (graph.size() == 6));
    }
}
