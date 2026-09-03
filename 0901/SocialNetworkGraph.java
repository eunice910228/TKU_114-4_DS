import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
public class SocialNetworkGraph {
    private final Map<String, Set<String>> friends = new LinkedHashMap<>();
    boolean addUser(String user) {
        if (user == null || user.isBlank() || friends.containsKey(user)) {
            return false;
        }
        friends.put(user, new HashSet<>());
        return true;
    }
    boolean addFriend(String a, String b) {
        if (!friends.containsKey(a) || !friends.containsKey(b) || a.equals(b)) {
            return false;
        }
        if (!friends.get(a).add(b)) {
            return false;
        }
        friends.get(b).add(a);
        return true;
    }
    boolean removeFriend(String a, String b) {
        if (!friends.containsKey(a) || !friends.containsKey(b)) {
            return false;
        }
        if (!friends.get(a).remove(b)) {
            return false;
        }
        friends.get(b).remove(a);
        return true;
    }
    List<String> mutualFriends(String a, String b) {
        List<String> result = new ArrayList<>();
        if (!friends.containsKey(a) || !friends.containsKey(b)) {
            return result;
        }
        for (String friend : friends.get(a)) {
            if (friends.get(b).contains(friend)) {
                result.add(friend);
            }
        }
        Collections.sort(result);
        return result;
    }
    List<String> isolatedUsers() {
        List<String> result = new ArrayList<>();
        for (Map.Entry<String, Set<String>> entry : friends.entrySet()) {
            if (entry.getValue().isEmpty()) {
                result.add(entry.getKey());
            }
        }
        return result;
    }
    public static void main(String[] args) {
        SocialNetworkGraph network = new SocialNetworkGraph();
        for (String user : new String[]{ "Eunice", "Bsy", "Candice", "Doris", "Ella" }) {
            network.addUser(user);
        }
        System.out.println("重複addUser=" + network.addUser("Eunice"));
        System.out.println("Eunice-Bsy=" + network.addFriend("Eunice", "Bsy"));
        System.out.println("Eunice-Candice=" + network.addFriend("Eunice", "Candice"));
        System.out.println("Bsy-Candice=" + network.addFriend("Bsy", "Candice"));
        System.out.println("Bsy-Doris=" + network.addFriend("Bsy", "Doris"));
        System.out.println("重複 Eunice-Bsy=" + network.addFriend("Eunice", "Bsy"));
        System.out.println("自己加自己=" + network.addFriend("Eunice", "Eunice"));
        System.out.println("missing user=" + network.addFriend("Eunice", "Zoe"));
        System.out.println("Eunice與Bsy共同好友=" + network.mutualFriends("Eunice", "Bsy"));
        System.out.println("Eunice與Doris共同好友=" + network.mutualFriends("Eunice", "Doris"));
        System.out.println("孤立使用者=" + network.isolatedUsers());
        System.out.println("removeFriend Bsy-Doris=" + network.removeFriend("Bsy", "Doris"));
        System.out.println("重複remove=" + network.removeFriend("Bsy", "Doris"));
        System.out.println("解除後孤立使用者=" + network.isolatedUsers());
    }
}
