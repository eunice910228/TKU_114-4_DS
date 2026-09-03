public class IntegratedStructureAudit {
    enum Need {
        INDEX_ACCESS, FIFO, LIFO, SORTED_RANGE,
        NEXT_PRIORITY, KEY_LOOKUP, RELATION_TRAVERSAL
    }
    static int reasonableCount;
    static int unreasonableCount;
    static String recommend(Need need) {
        if (need == null) {
            return "UNKNOWN";
        }
        return switch (need) {
            case INDEX_ACCESS -> "ArrayList";
            case FIFO -> "Queue（ArrayDeque）";
            case LIFO -> "Stack（ArrayDeque）";
            case SORTED_RANGE -> "BST（TreeMap）";
            case NEXT_PRIORITY -> "Heap（PriorityQueue）";
            case KEY_LOOKUP -> "HashMap";
            case RELATION_TRAVERSAL -> "Graph（adjacency list）";
        };
    }
    static String reasonOf(Need need) {
        return switch (need) {
            case INDEX_ACCESS -> "index存取get為O(1)";
            case FIFO -> "offer與poll皆O(1)";
            case LIFO -> "push與pop皆O(1)";
            case SORTED_RANGE -> "查詢與range為O(log n)";
            case NEXT_PRIORITY -> "peek O(1)、poll O(log n)";
            case KEY_LOOKUP -> "平均O(1)";
            case RELATION_TRAVERSAL -> "BFS/DFS走訪為O(V+E)";
        };
    }
    static void audit(String scenario, Need need, String used) {
        if (scenario == null || need == null || used == null) {
            System.out.println("情境資訊不足，無法診斷");
            return;
        }
        String suggested = recommend(need);
        if (suggested.equals(used)) {
            reasonableCount++;
            System.out.println(scenario + "：使用" + used + " 合理（" + reasonOf(need) + "）");
        } else {
            unreasonableCount++;
            System.out.println(scenario + "：使用" + used + " 不合理，建議"
                    + suggested + "（" + reasonOf(need) + "）");
        }
    }
    static void summaryReport() {
        System.out.println("診斷結果：合理" + reasonableCount + "件，不合理" + unreasonableCount + "件");
    }
    public static void main(String[] args) {
        System.out.println("需求與建議結構");
        for (Need need : Need.values()) {
            System.out.println(need + " -> " + recommend(need));
        }
        System.out.println("null需求 -> " + recommend(null));
        System.out.println("情境診斷");
        audit("依索引讀取月報表第n列", Need.INDEX_ACCESS, "ArrayList");
        audit("列印工作先送先印", Need.FIFO, "ArrayList");
        audit("編輯器復原上一步", Need.LIFO, "Stack（ArrayDeque）");
        audit("查詢學號範圍內的成績", Need.SORTED_RANGE, "HashMap");
        audit("急診依危急度叫號", Need.NEXT_PRIORITY, "Heap（PriorityQueue）");
        audit("依員工編號查資料", Need.KEY_LOOKUP, "ArrayList");
        audit("捷運轉乘路線查詢", Need.RELATION_TRAVERSAL, "Graph（adjacency list）");
        audit("依訂單編號查詢訂單", Need.KEY_LOOKUP, "HashMap");
        audit("客服排隊依序處理", Need.FIFO, "Queue（ArrayDeque）");
        audit("課程先修影響分析", Need.RELATION_TRAVERSAL, "ArrayList");
        audit("即時取最短回應時間", Need.NEXT_PRIORITY, "BST（TreeMap）");
        audit("瀏覽紀錄回上一頁", Need.LIFO, "Queue（ArrayDeque）");
        audit(null, Need.FIFO, "Queue（ArrayDeque）");
        summaryReport();
    }
}
