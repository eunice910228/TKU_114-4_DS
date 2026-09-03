import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
public class DirectedReachability {
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
    boolean addEdge(String from, String to) {
        if (!graph.containsKey(from) || !graph.containsKey(to)) {
            return false;
        }
        if (graph.get(from).contains(to)) {
            return false;
        }
        graph.get(from).add(to);
        return true;
    }
    boolean reachable(String from, String to) {
        if (!graph.containsKey(from) || !graph.containsKey(to)) {
            return false;
        }
        if (from.equals(to)) {
            return true;
        }
        Queue<String> queue = new ArrayDeque<>();
        Set<String> visited = new HashSet<>();
        queue.offer(from);
        visited.add(from);
        while (!queue.isEmpty()) {
            String current = queue.poll();
            for (String next : graph.get(current)) {
                if (next.equals(to)) {
                    return true;
                }
                if (visited.add(next)) {
                    queue.offer(next);
                }
            }
        }
        return false;
    }
    void queryReport(String[][] queries) {
        System.out.println("reachable查詢");
        for (String[] query : queries) {
            System.out.println(query[0] + "->" + query[1]
                    + "=" + reachable(query[0], query[1]));
        }
    }
    public static void main(String[] args) {
        DirectedReachability system = new DirectedReachability();
        for (String name : new String[]{ "A", "B", "C", "D", "E" }) {
            system.addVertex(name);
        }
        system.addEdge("A", "B");
        system.addEdge("B", "C");
        system.addEdge("C", "A");
        system.addEdge("C", "D");
        System.out.println("重複edge A->B=" + system.addEdge("A", "B"));
        System.out.println("不存在vertex A->Z=" + system.addEdge("A", "Z"));
        String[][] queries = {
                { "A", "D" }, { "D", "A" }, { "A", "A" },
                { "A", "E" }, { "E", "A" }, { "A", "Z" }
        };
        System.out.println("空graph reachable=" + new DirectedReachability().reachable("A", "B"));
        system.queryReport(queries);
    }
}
