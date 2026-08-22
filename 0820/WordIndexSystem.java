/*
 * 課後作業二：文字索引系統
 *
 * 讀取程式內建句子陣列，使用 Map<String, Integer> 統計單字次數、
 * Set<String> 保存不重複單字，並輸出出現至少兩次的單字。
 * 忽略英文大小寫與句點、逗號。
 */

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class WordIndexSystem {

    /** 正規化：轉小寫並去掉標點，讓 "Java," 與 "java." 算同一個字。 */
    static String normalize(String raw) {
        if (raw == null) {
            return "";
        }
        // 逐字檢查，只保留英數字與連字號；
        // 句點、逗號、問號、引號等標點一律略過。不使用正規表示式。
        String lower = raw.toLowerCase();
        StringBuilder sb = new StringBuilder();
        for (char c : lower.toCharArray()) {
            boolean isLetter = c >= 'a' && c <= 'z';
            boolean isDigit = c >= '0' && c <= '9';
            if (isLetter || isDigit || c == '-') {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        String[] sentences = {
            "Java is a programming language.",
            "Collections Framework is part of Java.",
            "A List keeps order, a Set removes duplicates.",
            "Java Collections include List, Set, and Map.",
            "Is a Map a Collection? Not exactly.",
            "   ",                                   // 邊界：全空白
            null                                     // 邊界：null 句子
        };

        Map<String, Integer> counter = new TreeMap<>();   // TreeMap：輸出自動照字母排序
        Set<String> unique = new LinkedHashSet<>();       // LinkedHashSet：保留首次出現順序
        int totalWords = 0;

        for (String sentence : sentences) {
            if (sentence == null || sentence.isBlank()) {
                continue;                                  // 略過無效句子
            }
            // split 用一個以上的空白切；\\s+ 可以處理連續空白
            for (String token : sentence.trim().split("\\s+")) {
                String word = normalize(token);
                if (word.isEmpty()) {
                    continue;                              // 邊界：整個 token 都是標點
                }
                totalWords++;
                unique.add(word);
                counter.put(word, counter.getOrDefault(word, 0) + 1);
            }
        }

        System.out.println("=== 來源 ===");
        System.out.println("  句子數（含無效）= " + sentences.length);
        System.out.println("  有效單字總數    = " + totalWords);

        System.out.println();
        System.out.println("=== Set<String>：不重複單字（依首次出現順序）===");
        System.out.println("  " + unique);
        System.out.println("  不重複單字數 = " + unique.size());

        System.out.println();
        System.out.println("=== Map<String, Integer>：完整次數統計（依字母排序）===");
        for (Map.Entry<String, Integer> e : counter.entrySet()) {
            System.out.printf("  %-14s %d%n", e.getKey(), e.getValue());
        }

        System.out.println();
        System.out.println("=== 出現至少兩次的單字 ===");
        List<Map.Entry<String, Integer>> repeated = new ArrayList<>();
        for (Map.Entry<String, Integer> e : counter.entrySet()) {
            if (e.getValue() >= 2) {
                repeated.add(e);
            }
        }
        // 依次數降冪，同次數依字母升冪
        repeated.sort(Comparator
                .comparingInt((Map.Entry<String, Integer> e) -> e.getValue()).reversed()
                .thenComparing(Map.Entry::getKey));

        if (repeated.isEmpty()) {
            System.out.println("  （沒有重複的單字）");
        }
        for (Map.Entry<String, Integer> e : repeated) {
            System.out.printf("  %-14s %d 次  %s%n",
                    e.getKey(), e.getValue(), "■".repeat(e.getValue()));
        }

        System.out.println();
        System.out.println("=== 驗證：三種結構的關係 ===");
        int sum = 0;
        for (int v : counter.values()) {
            sum += v;
        }
        System.out.println("  Map 所有次數相加 = " + sum + "（應等於單字總數 " + totalWords + "）");
        System.out.println("  Map.size = " + counter.size()
                + "，Set.size = " + unique.size() + "（應相同）");

        System.out.println();
        System.out.println("=== 正規化效果驗證 ===");
        System.out.println("  \"Java\"  -> " + normalize("Java"));
        System.out.println("  \"Java.\" -> " + normalize("Java."));
        System.out.println("  \"Java,\" -> " + normalize("Java,"));
        System.out.println("  \"Collection?\" -> " + normalize("Collection?"));
        System.out.println("  \"...\"   -> \"" + normalize("...") + "\"（整個被移除，會被略過）");
        System.out.println("  null    -> \"" + normalize(null) + "\"");
        System.out.println("  所以 Java / Java. / Java, 都被算成同一個字：共 "
                + counter.get("java") + " 次");

        System.out.println();
        System.out.println("=== 邊界：全部都是無效輸入 ===");
        Map<String, Integer> emptyCounter = new TreeMap<>();
        for (String s : new String[]{ null, "", "   ", ",,,", "..." }) {
            if (s == null || s.isBlank()) {
                continue;
            }
            for (String token : s.trim().split("\\s+")) {
                String w = normalize(token);
                if (!w.isEmpty()) {
                    emptyCounter.put(w, emptyCounter.getOrDefault(w, 0) + 1);
                }
            }
        }
        System.out.println("  結果 = " + emptyCounter + "（空 Map，不會出錯）");
        System.out.println("  get(\"java\") = " + emptyCounter.get("java") + "（查無 key 回 null）");
    }
}
