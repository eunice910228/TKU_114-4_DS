import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class WordIndexSystem {
    static String normalize(String raw) {
        if (raw == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (char c : raw.toLowerCase().toCharArray()) {
            if ((c >= 'a' && c <= 'z') || (c >= '0' && c <= '9') || c == '-') {
                sb.append(c);
            }
        }
        return sb.toString();
    }
    static int indexInto(String[] sentences, Map<String, Integer> counter, Set<String> unique) {
        int total = 0;
        if (sentences == null) {
            return 0;
        }
        for (String sentence : sentences) {
            if (sentence == null || sentence.isBlank()) {
               continue;
            }
            for (String token : sentence.trim().split("\\s+")) {
                String word = normalize(token);
                if (word.isEmpty()) {
                   continue;
                }
                total++;
                unique.add(word);
                counter.put(word, counter.getOrDefault(word, 0) + 1);
            }
        }
        return total;
    }
    static <K extends Comparable<K>> List<Map.Entry<K, Integer>> atLeast(
            Map<K, Integer> counts, int minimum) {
        List<Map.Entry<K, Integer>> result = new ArrayList<>();
        if (counts == null) {
            return result;
        }
        for (Map.Entry<K, Integer> e : counts.entrySet()) {
            if (e.getValue() >= minimum) {
                result.add(e);
            }
        }
        result.sort(Comparator
                .comparingInt((Map.Entry<K, Integer> e) -> e.getValue()).reversed()
                .thenComparing(Map.Entry::getKey));
        return result;
    }
    public static void main(String[] args) {
        String[] sentences = {
            "The cat sleeps on the warm sofa.",
			"I drink coffee every morning, then I walk the dog.",
			"My dog likes the park, and the cat likes the sofa.",
			"Coffee, tea, or juice? I prefer coffee.",
			"Two cats and one dog live in my house.",
			"   ",
			null
        };
        Map<String, Integer> counter = new TreeMap<>();
        Set<String> unique = new LinkedHashSet<>();
        int totalWords = indexInto(sentences, counter, unique);

        System.out.println("=== Set<String>：不重複單字（首次出現順序）===");
        System.out.println("  " + unique);
        System.out.println("  不重複單字數 = " + unique.size() + "，單字總數 = " + totalWords);

        System.out.println("\n=== Map<String, Integer>：次數統計（字母排序）===");
        for (Map.Entry<String, Integer> e : counter.entrySet()) {
            System.out.printf("  %-14s %d%n", e.getKey(), e.getValue());
        }

        System.out.println("\n=== 出現至少兩次的單字 ===");
        List<Map.Entry<String, Integer>> repeated = atLeast(counter, 2);
        if (repeated.isEmpty()) {
            System.out.println("  （沒有重複的單字）");
        }
        for (Map.Entry<String, Integer> e : repeated) {
            System.out.printf("  %-14s %d 次%n", e.getKey(), e.getValue());
        }

        System.out.println("\n=== 驗證 ===");
        int sum = 0;
        for (int v : counter.values()) {
            sum = sum + v;
        }
        System.out.println("  Map 次數加總 = " + sum + "（應等於 " + totalWords + "）");
        System.out.println("  Map.size = " + counter.size()
                + "，Set.size = " + unique.size() + "（應相同）");
        System.out.println("  Coffee, / coffee = " + counter.get("coffee") + " 次");
        System.out.println("  cat / cats  = "
                + counter.get("cat") + " / " + counter.get("cats"));
        System.out.println("\n=== 邊界測試 ===");
        Map<String, Integer> emptyCounter = new TreeMap<>();
        Set<String> emptyUnique = new LinkedHashSet<>();
        int n = indexInto(new String[]{ null, "", "   ", ",,,", "..." }, emptyCounter, emptyUnique);
        System.out.println("  全無效輸入：單字數 = " + n + "，Map = " + emptyCounter);
        System.out.println("  查無 key：get(\"cat\") = " + emptyCounter.get("cat"));
        System.out.println("  sentences 為 null：單字數 = "
                + indexInto(null, emptyCounter, emptyUnique));
        System.out.println("  atLeast(null, 2) = " + atLeast(null, 2));
        System.out.println("  atLeast(counter, 99) = " + atLeast(counter, 99));
    }
}