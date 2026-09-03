import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
public class Q12_CampusDispatchSystem {
    public record Request(String id, String location, int priority, long sequence) {}
    private final Map<String, List<String>> roads = new LinkedHashMap<>();
    private final Map<String, Request> byId = new HashMap<>();
    private final PriorityQueue<Request> queue = new PriorityQueue<>(
            Comparator.comparingInt(Request::priority)
                    .thenComparingLong(Request::sequence));
    public boolean addLocation(String location) {
        if (location == null || location.isBlank()) {
            return false;
        }
        if (roads.containsKey(location)) {
            return false;
        }
        roads.put(location, new ArrayList<>());
        return true;
    }
    public boolean addRoad(String first, String second) {
        if (!roads.containsKey(first) || !roads.containsKey(second)) {
            return false;
        }
        if (first.equals(second) || roads.get(first).contains(second)) {
            return false;
        }
        roads.get(first).add(second);
        roads.get(second).add(first);
        return true;
    }
    public boolean submit(Request request) {
        if (request == null || request.id() == null || request.id().isBlank()
                || request.location() == null || request.location().isBlank()) {
            return false;
        }
        if (byId.containsKey(request.id())) {
            return false;
        }
        byId.put(request.id(), request);
        queue.offer(request);
        return true;
    }
    private boolean isReachable(String start, String target) {
        if (!roads.containsKey(start) || !roads.containsKey(target)) {
            return false;
        }
        if (start.equals(target)) {
            return true;
        }
        Queue<String> search = new ArrayDeque<>();
        Set<String> visited = new HashSet<>();
        search.offer(start);
        visited.add(start);
        while (!search.isEmpty()) {
            String current = search.poll();
            for (String next : roads.get(current)) {
                if (next.equals(target)) {
                    return true;
                }
                if (visited.add(next)) {
                    search.offer(next);
                }
            }
        }
        return false;
    }
    public Request nextReachable(String serviceCenter) {
        if (serviceCenter == null || !roads.containsKey(serviceCenter)) {
            return null;
        }
        List<Request> unreachable = new ArrayList<>();
        Request found = null;
        while (!queue.isEmpty()) {
            Request candidate = queue.poll();
            if (isReachable(serviceCenter, candidate.location())) {
                found = candidate;
                break;
            }
            unreachable.add(candidate);
        }
        for (Request request : unreachable) {
            queue.offer(request);
        }
        if (found != null) {
            byId.remove(found.id());
        }
        return found;
    }
    public List<String> route(String start, String target) {
        List<String> path = new ArrayList<>();
        if (start == null || target == null
                || !roads.containsKey(start) || !roads.containsKey(target)) {
            return path;
        }
        if (start.equals(target)) {
            path.add(start);
            return path;
        }
        Queue<String> search = new ArrayDeque<>();
        Set<String> visited = new HashSet<>();
        Map<String, String> previous = new HashMap<>();
        search.offer(start);
        visited.add(start);
        while (!search.isEmpty()) {
            String current = search.poll();
            if (current.equals(target)) {
                break;
            }
            for (String next : roads.get(current)) {
                if (visited.add(next)) {
                    previous.put(next, current);
                    search.offer(next);
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
    public int pendingCount() {
        return byId.size();
    }
    public static void main(String[] args) {
        Q12_CampusDispatchSystem system = new Q12_CampusDispatchSystem();
        for (String location : new String[]{ "派送中心", "圖書館", "宿舍", "體育館", "其他校區" }) {
            System.out.println("addLocation " + location + "=" + system.addLocation(location));
        }
        System.out.println("重複 addLocation 派送中心=" + system.addLocation("派送中心"));
        System.out.println("addRoad 派送中心-圖書館=" + system.addRoad("派送中心", "圖書館"));
        System.out.println("addRoad 圖書館-宿舍=" + system.addRoad("圖書館", "宿舍"));
        System.out.println("addRoad 派送中心-體育館=" + system.addRoad("派送中心", "體育館"));
        System.out.println("重複 addRoad=" + system.addRoad("派送中心", "圖書館"));
        System.out.println("missing addRoad=" + system.addRoad("派送中心", "操場"));
        System.out.println("submit R1 宿舍 P2=" + system.submit(new Request("R1", "宿舍", 2, 1)));
        System.out.println("submit R2 其他校區 P1=" + system.submit(new Request("R2", "其他校區", 1, 2)));
        System.out.println("submit R3 體育館 P2=" + system.submit(new Request("R3", "體育館", 2, 3)));
        System.out.println("重複id submit R1=" + system.submit(new Request("R1", "圖書館", 1, 4)));
        System.out.println("null submit=" + system.submit(null));
        System.out.println("pending=" + system.pendingCount());
        System.out.println("nextReachable 派送中心=" + system.nextReachable("派送中心"));
        System.out.println("nextReachable 派送中心=" + system.nextReachable("派送中心"));
        System.out.println("nextReachable 派送中心（剩不可達）=" + system.nextReachable("派送中心"));
        System.out.println("pending（不可達保留）=" + system.pendingCount());
        System.out.println("route 派送中心->宿舍=" + system.route("派送中心", "宿舍"));
        System.out.println("route 派送中心->其他校區=" + system.route("派送中心", "其他校區"));
        System.out.println("route 派送中心->派送中心=" + system.route("派送中心", "派送中心"));
        System.out.println("route missing=" + system.route("派送中心", "操場"));
        System.out.println("nextReachable missing派送中心=" + system.nextReachable("操場"));
    }
}