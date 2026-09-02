import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class LogisticsWeightedGraph {
    private final Map<String, Map<String, Integer>> network = new HashMap<>();
    boolean addVertex(String city) {
        if (city == null || city.isBlank()) {
            return false;
        }
        if (network.containsKey(city)) {
            return false;
        }
        network.put(city, new HashMap<>());
        return true;
    }
    boolean addEdge(String from, String to, int cost) {
        if (!network.containsKey(from) || !network.containsKey(to)) {
            return false;
        }
        if (cost < 0 || from.equals(to)) {
            return false;
        }
        if (network.get(from).containsKey(to)) {
            return false;
        }
        network.get(from).put(to, cost);
        return true;
    }
    boolean updateWeight(String from, String to, int cost) {
        if (cost < 0 || getWeight(from, to) < 0) {
            return false;
        }
        network.get(from).put(to, cost);
        return true;
    }
    boolean removeEdge(String from, String to) {
        if (getWeight(from, to) < 0) {
            return false;
        }
        network.get(from).remove(to);
        return true;
    }
    int getWeight(String from, String to) {
        if (!network.containsKey(from)) {
            return -1;
        }
        Integer cost = network.get(from).get(to);
        return cost == null ? -1 : cost;
    }
    void routeReport() {
        System.out.println("物流路線成本");
        List<String> cities = new ArrayList<>(network.keySet());
        Collections.sort(cities);
        for (String city : cities) {
            Map<String, Integer> targets = network.get(city);
            List<String> names = new ArrayList<>(targets.keySet());
            Collections.sort(names);
            System.out.print( city + "->");
            if (names.isEmpty()) {
                System.out.print("無路線");
            }
            for (String name : names) {
                System.out.print(name + "(" + targets.get(name) + ") ");
            }
            System.out.println();
        }
    }
    public static void main(String[] args) {
        LogisticsWeightedGraph network = new LogisticsWeightedGraph();
        System.out.println("addVertex 台北=" + network.addVertex("台北"));
        System.out.println("addVertex 台中=" + network.addVertex("台中"));
        System.out.println("addVertex 台南=" + network.addVertex("台南"));
        System.out.println("addVertex 高雄=" + network.addVertex("高雄"));
        System.out.println("重複 addVertex 台北=" + network.addVertex("台北"));
        System.out.println("台北->台中 300=" + network.addEdge("台北", "台中", 300));
        System.out.println("台中->台南 250=" + network.addEdge("台中", "台南", 250));
        System.out.println("台南->高雄 150=" + network.addEdge("台南", "高雄", 150));
        System.out.println("負權重 台北->高雄=" + network.addEdge("台北", "高雄", -100));
        System.out.println("不存在城市 台北->花蓮=" + network.addEdge("台北", "花蓮", 400));
        System.out.println("重複 台北->台中=" + network.addEdge("台北", "台中", 999));
        System.out.println("null城市 addVertex=" + network.addVertex(null));
        System.out.println("自己到自己 台北->台北=" + network.addEdge("台北", "台北", 100));
        System.out.println("空網路 getWeight=" + new LogisticsWeightedGraph().getWeight("台北", "台中"));
        System.out.println("getWeight 台北->台中=" + network.getWeight("台北", "台中"));
        System.out.println("getWeight 台北->台南=" + network.getWeight("台北", "台南"));
        System.out.println("updateWeight 台北->台中改350=" + network.updateWeight("台北", "台中", 350));
        System.out.println("updateWeight 負數=" + network.updateWeight("台北", "台中", -50));
        System.out.println("updateWeight 不存在路線=" + network.updateWeight("台北", "台南", 500));
        System.out.println("removeEdge 台南->高雄=" + network.removeEdge("台南", "高雄"));
        System.out.println("removeEdge 不存在=" + network.removeEdge("台南", "高雄"));
        network.routeReport();
    }
}
