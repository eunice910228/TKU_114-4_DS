import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
public class CourseTagReport {
    public static void main(String[] args) {
        String[] rawTags = {
            "Java", "OOP", "Java", "Collections", "OOP",
            "Java", "  ", null, "generic", "Generic", "Collections"
        };
        List<String> ordered = new ArrayList<>();
        Set<String> unique = new LinkedHashSet<>();
        Map<String, Integer> counter = new TreeMap<>();
        for (String raw : rawTags) {
            if (raw == null || raw.isBlank()) {
                continue;
            }
            String tag = raw.trim().toLowerCase();
            ordered.add(tag);
            unique.add(tag);
            counter.put(tag, counter.getOrDefault(tag, 0) + 1);
        }
        System.out.println("原始輸入筆數=" + rawTags.length);
        System.out.println("List保留順序含重複=" + ordered);
        System.out.println("List size=" + ordered.size());
        System.out.println("List第1筆=" + ordered.get(0));
        System.out.println("Set不重複=" + unique);
        System.out.println("Set size=" + unique.size());
        System.out.println("Set contains java=" + unique.contains("java"));
        System.out.println("Map統計次數");
        for (Map.Entry<String, Integer> e : counter.entrySet()) {
            System.out.println(e.getKey() + "=" + e.getValue() + "次");
        }
        int valueSum = 0;
        for (int count : counter.values()) {
            valueSum += count;
        }
        System.out.println("Map size=" + counter.size() + "，value總和=" + valueSum);
        String top = null;
        int max = 0;
        for (Map.Entry<String, Integer> e : counter.entrySet()) {
            if (e.getValue() > max) {
                max = e.getValue();
                top = e.getKey();
            }
        }
        System.out.println("出現最多=" + top + "（" + max + "次）");
        List<String> emptyList = new ArrayList<>();
        Set<String> emptySet = new LinkedHashSet<>();
        Map<String, Integer> emptyMap = new TreeMap<>();
        for (String raw : new String[]{ null, "", "   " }) {
            if (raw == null || raw.isBlank()) {
                continue;
            }
            emptyList.add(raw);
            emptySet.add(raw);
            emptyMap.put(raw, emptyMap.getOrDefault(raw, 0) + 1);
        }
        System.out.println("全無效資料 List=" + emptyList + " Set=" + emptySet + " Map=" + emptyMap);
        System.out.println("Map查無key get=" + emptyMap.get("java") + "，getOrDefault=" + emptyMap.getOrDefault("java", 0));
    }
}
