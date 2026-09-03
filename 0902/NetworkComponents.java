import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
public class NetworkComponents {
    private final Map<String, List<String>> graph = new LinkedHashMap<>();
    boolean addNode(String name) {
        if (name == null || name.isBlank()) {
            return false;
        }
        if (graph.containsKey(name)) {
            return false;
        }
        graph.put(name, new ArrayList<>());
        return true;
    }
    boolean connect(String a, String b) {
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
    List<List<String>> components() {
        List<List<String>> result = new ArrayList<>();
        Set<String> visited = new HashSet<>();
        for (String start : graph.keySet()) {
            if (visited.contains(start)) {
                continue;
            }
            List<String> group = new ArrayList<>();
            Queue<String> queue = new ArrayDeque<>();
            queue.offer(start);
            visited.add(start);
            while (!queue.isEmpty()) {
                String current = queue.poll();
                group.add(current);
                for (String next : graph.get(current)) {
                    if (visited.add(next)) {
                        queue.offer(next);
                    }
                }
            }
            result.add(group);
        }
        return result;
    }
    int componentCount() {
        return components().size();
    }
    List<String> largestComponent() {
        List<String> largest = new ArrayList<>();
        for (List<String> group : components()) {
            if (group.size() > largest.size()) {
                largest = group;
            }
        }
        return largest;
    }
    void componentReport() {
        System.out.println("component清單");
        List<List<String>> groups = components();
        for (int i = 0; i < groups.size(); i++) {
            System.out.println("component" + (i + 1) + "=" + groups.get(i));
        }
        System.out.println("component總數=" + componentCount());
        System.out.println("最大component=" + largestComponent());
    }
    public static void main(String[] args) {
        NetworkComponents network = new NetworkComponents();
        for (String name : new String[]{ "A", "B", "C", "D", "E", "F", "G" }) {
            network.addNode(name);
        }
        network.connect("A", "B");
        network.connect("B", "C");
        network.connect("D", "E");
        System.out.println("重複connect A-B=" + network.connect("A", "B"));
        System.out.println("不存在node A-Z=" + network.connect("A", "Z"));
        System.out.println("空白node addNode=" + network.addNode("  "));
        NetworkComponents emptyNet = new NetworkComponents();
        System.out.println("空graph component數=" + emptyNet.componentCount()
                + "，最大component=" + emptyNet.largestComponent());
        network.componentReport();
    }
}
