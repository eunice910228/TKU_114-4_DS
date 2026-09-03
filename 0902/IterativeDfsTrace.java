import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
public class IterativeDfsTrace {
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
    List<String> dfsWithTrace(String start) {
        List<String> order = new ArrayList<>();
        if (!graph.containsKey(start)) {
            System.out.println("查無此vertex");
            return order;
        }
        Deque<String> stack = new ArrayDeque<>();
        Set<String> visited = new LinkedHashSet<>();
        stack.push(start);
        System.out.println("push " + start + "，stack=" + stack + "，visited=" + visited);
        while (!stack.isEmpty()) {
            String current = stack.pop();
            if (!visited.add(current)) {
                System.out.println("pop " + current + "（已走訪，略過）");
                continue;
            }
            order.add(current);
            System.out.println("pop " + current + "，stack=" + stack + "，visited=" + visited);
            List<String> neighbors = graph.get(current);
            for (int i = neighbors.size() - 1; i >= 0; i--) {
                String next = neighbors.get(i);
                if (!visited.contains(next)) {
                    stack.push(next);
                    System.out.println("push " + next + "，stack=" + stack);
                }
            }
        }
        return order;
    }
    public static void main(String[] args) {
        IterativeDfsTrace trace = new IterativeDfsTrace();
        for (String name : new String[]{ "A", "B", "C", "D", "E" }) {
            trace.addVertex(name);
        }
        trace.addEdge("A", "B");
        trace.addEdge("A", "C");
        trace.addEdge("B", "D");
        trace.addEdge("C", "D");
        System.out.println("空白vertex addVertex=" + trace.addVertex("  "));
        System.out.println("空graph DFS=" + new IterativeDfsTrace().dfsWithTrace("A"));
        System.out.println("從A開始的DFS追蹤");
        System.out.println("走訪順序=" + trace.dfsWithTrace("A"));
        System.out.println("孤立vertex E");
        System.out.println("走訪順序=" + trace.dfsWithTrace("E"));
        System.out.println("不存在的vertex");
        System.out.println("走訪順序=" + trace.dfsWithTrace("Z"));
    }
}
