import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
public class Q09_DfsPathSearch {
    public static List<String> dfs(Map<String, List<String>> graph, String start) {
        List<String> result = new ArrayList<>();
        if (graph == null || start == null || !graph.containsKey(start)) {
            return result;
        }
        visit(graph, start, new HashSet<>(), result);
        return result;
    }
    private static void visit(Map<String, List<String>> graph, String current,
            Set<String> visited, List<String> result) {
        if (!visited.add(current)) {
            return;
        }
        result.add(current);
        for (String next : graph.getOrDefault(current, List.of())) {
            if (next != null && graph.containsKey(next)) {
                visit(graph, next, visited, result);
            }
        }
    }
    public static boolean reachable(Map<String, List<String>> graph,
            String start, String target) {
        if (graph == null || start == null || target == null
                || !graph.containsKey(start) || !graph.containsKey(target)) {
            return false;
        }
        if (start.equals(target)) {
            return true;
        }
        return dfs(graph, start).contains(target);
    }
    public static void main(String[] args) {
        Map<String, List<String>> graph = new LinkedHashMap<>();
        graph.put("A", List.of("B", "C"));
        graph.put("B", List.of("D"));
        graph.put("C", List.of("D"));
        graph.put("D", List.of("A"));
        graph.put("E", List.of());
        System.out.println("dfs A=" + dfs(graph, "A"));
        System.out.println("dfs E=" + dfs(graph, "E"));
        System.out.println("dfs missing=" + dfs(graph, "X"));
        System.out.println("dfs null=" + dfs(null, "A"));
        System.out.println("reachable A->D=" + reachable(graph, "A", "D"));
        System.out.println("reachable D->A（循環）=" + reachable(graph, "D", "A"));
        System.out.println("reachable A->E=" + reachable(graph, "A", "E"));
        System.out.println("reachable A->A=" + reachable(graph, "A", "A"));
        System.out.println("reachable missing=" + reachable(graph, "A", "X"));
    }
}
