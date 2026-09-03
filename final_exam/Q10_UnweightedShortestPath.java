import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
public class Q10_UnweightedShortestPath {
    public static List<String> shortestPath(Map<String, List<String>> graph,
            String start, String target) {
        List<String> path = new ArrayList<>();
        if (graph == null || start == null || target == null
                || !graph.containsKey(start) || !graph.containsKey(target)) {
            return path;
        }
        if (start.equals(target)) {
            path.add(start);
            return path;
        }
        Queue<String> queue = new ArrayDeque<>();
        Set<String> visited = new HashSet<>();
        Map<String, String> previous = new HashMap<>();
        queue.offer(start);
        visited.add(start);
        while (!queue.isEmpty()) {
            String current = queue.poll();
            if (current.equals(target)) {
                break;
            }
            for (String next : graph.getOrDefault(current, List.of())) {
                if (next != null && graph.containsKey(next) && visited.add(next)) {
                    previous.put(next, current);
                    queue.offer(next);
                }
            }
        }
        if (!visited.contains(target)) {
            return path;
        }
        for (String at = target; at != null; at = previous.get(at)) {
            path.add(at);
        }
        Collections.reverse(path);
        return path;
    }
    public static void main(String[] args) {
        Map<String, List<String>> graph = new LinkedHashMap<>();
        graph.put("A", List.of("B", "C"));
        graph.put("B", List.of("D"));
        graph.put("C", List.of("D", "E"));
        graph.put("D", List.of("F"));
        graph.put("E", List.of("F"));
        graph.put("F", List.of());
        graph.put("X", List.of());
        System.out.println("A->F=" + shortestPath(graph, "A", "F"));
        System.out.println("A->E=" + shortestPath(graph, "A", "E"));
        System.out.println("A->X=" + shortestPath(graph, "A", "X"));
        System.out.println("A->A=" + shortestPath(graph, "A", "A"));
        System.out.println("missing=" + shortestPath(graph, "A", "Z"));
        System.out.println("null graph=" + shortestPath(null, "A", "F"));
    }
}
