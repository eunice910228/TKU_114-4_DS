import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CollectionChoiceReport {

    public static void main(String[] args) {
        System.out.println("需求1：保留搜尋紀錄且允許重複");
        List<String> searchHistory = new ArrayList<>();
        searchHistory.add("java");
        searchHistory.add("collections");
        searchHistory.add("java");
        searchHistory.add("generic");
        searchHistory.add("java");
        System.out.println("全部紀錄 = " + searchHistory);
        System.out.println("size = " + searchHistory.size() + "含重複");
        System.out.println("最近一次 = " + searchHistory.get(searchHistory.size() - 1));
        System.out.println("需求2：保存不重複會員編號");
        Set<String> members = new HashSet<>();
        String[] incoming = { "A01", "A02", "A01", "A03", "A02", "A04" };
        for (String id : incoming) {
            System.out.println("add(" + id + ") = " + members.add(id));
        }
        System.out.println("輸入" + incoming.length + "筆，實際保存 " + members.size() + "筆");
        System.out.println("contains(A03) = " + members.contains("A03"));
        System.out.println("需求3：以學號查詢成績");
        Map<String, Integer> scores = new HashMap<>();
        scores.put("S01", 92);
        scores.put("S02", 85);
        scores.put("S03", 78);
        System.out.println("get(S02) = " + scores.get("S02"));
        System.out.println("get(S33) = " + scores.get("S33"));
        System.out.println("getOrDefault(S33, 0) = " + scores.getOrDefault("S33", 0));
        System.out.println("put(S01, 95) 舊值 = " + scores.put("S01", 95));
    }
}
