import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
public class Q07_AdjacencyListGraph {
    private final Map<String, Set<String>> adjacency = new LinkedHashMap<>();
    public boolean addVertex(String vertex) {
        if (vertex == null || vertex.isBlank()) {
            return false;
        }
        if (adjacency.containsKey(vertex)) {
            return false;
        }
        adjacency.put(vertex, new LinkedHashSet<>());
        return true;
    }
    public boolean addEdge(String from, String to) {
        if (!adjacency.containsKey(from) || !adjacency.containsKey(to)) {
            return false;
        }
        if (from.equals(to)) {
            return false;
        }
        return adjacency.get(from).add(to);
    }
    public boolean removeEdge(String from, String to) {
        if (!adjacency.containsKey(from) || to == null) {
            return false;
        }
        return adjacency.get(from).remove(to);
    }
    public List<String> outgoing(String vertex) {
        List<String> result = new ArrayList<>();
        if (vertex != null && adjacency.containsKey(vertex)) {
            result.addAll(adjacency.get(vertex));
        }
        return result;
    }
    public int inDegree(String vertex) {
        if (vertex == null || !adjacency.containsKey(vertex)) {
            return 0;
        }
        int count = 0;
        for (Set<String> targets : adjacency.values()) {
            if (targets.contains(vertex)) {
                count++;
            }
        }
        return count;
    }
    public int edgeCount() {
        int total = 0;
        for (Set<String> targets : adjacency.values()) {
            total = total + targets.size();
        }
        return total;
    }
    public static void main(String[] args) {
        Q07_AdjacencyListGraph graph = new Q07_AdjacencyListGraph();
        System.out.println("addVertex A=" + graph.addVertex("A"));
        System.out.println("addVertex B=" + graph.addVertex("B"));
        System.out.println("addVertex C=" + graph.addVertex("C"));
        System.out.println("重複 addVertex A=" + graph.addVertex("A"));
        System.out.println("A->B=" + graph.addEdge("A", "B"));
        System.out.println("A->C=" + graph.addEdge("A", "C"));
        System.out.println("B->C=" + graph.addEdge("B", "C"));
        System.out.println("重複 A->B=" + graph.addEdge("A", "B"));
        System.out.println("self-loop A->A=" + graph.addEdge("A", "A"));
        System.out.println("missing A->Z=" + graph.addEdge("A", "Z"));
        System.out.println("A outgoing=" + graph.outgoing("A"));
        System.out.println("C outgoing=" + graph.outgoing("C"));
        System.out.println("missing outgoing=" + graph.outgoing("Z"));
        System.out.println("C inDegree=" + graph.inDegree("C"));
        System.out.println("A inDegree=" + graph.inDegree("A"));
        System.out.println("missing inDegree=" + graph.inDegree("Z"));
        System.out.println("edgeCount=" + graph.edgeCount());
        System.out.println("removeEdge A->C=" + graph.removeEdge("A", "C"));
        System.out.println("重複 removeEdge A->C=" + graph.removeEdge("A", "C"));
        System.out.println("remove後 edgeCount=" + graph.edgeCount());
        System.out.println("remove後 C inDegree=" + graph.inDegree("C"));
    }
}
