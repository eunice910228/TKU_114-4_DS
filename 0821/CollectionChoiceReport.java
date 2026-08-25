import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CollectionChoiceReport {

    static void trace(String title, List<String> data) {
        System.out.println("  " + title);
        data.add("A");
        data.add("B");
        data.add("C");
        System.out.println("尾端新增 =" + data);
        data.add(1, "X");
        System.out.println("index 1 插入 =" + data);
        data.remove(2);
        System.out.println("index 2 刪除 =" + data);
        System.out.println("get(0) =" + data.get(0));
    }

    public static void main(String[] args) {
        System.out.println("需求1：保留搜尋紀錄且允許重複");
        List<String> searchHistory = new ArrayList<>();
        searchHistory.add("java");
        searchHistory.add("collections");
        searchHistory.add("java");
        searchHistory.add("generic");
        searchHistory.add("java");
        System.out.println("全部紀錄 =" + searchHistory);
        System.out.println("size =" + searchHistory.size() + "含重複");
        System.out.println("最近一次 =" + searchHistory.get(searchHistory.size() - 1));
        System.out.println("需求2：保存不重複會員編號");
        Set<String> members = new HashSet<>();
        String[] incoming = { "A01", "A02", "A01", "A03", "A02", "A04" };
        for (String id : incoming) {
            System.out.println("add(" + id + ") = " + members.add(id));
        }
        System.out.println("輸入" + incoming.length + "筆，實際保存" + members.size() + "筆");
        System.out.println("contains(A03) = " + members.contains("A03"));
        System.out.println("需求3：以學號查詢成績");
        Map<String, Integer> scores = new HashMap<>();
        scores.put("S01", 92);
        scores.put("S02", 85);
        scores.put("S03", 78);
        System.out.println("get(S02) =" + scores.get("S02"));
        System.out.println("get(S33) =" + scores.get("S33"));
        System.out.println("getOrDefault(S33, 0) = " + scores.getOrDefault("S33", 0));
        System.out.println("put(S01, 95) 舊值 = " + scores.put("S01", 95));
		
        System.out.println("需求4：依到達順序處理列印工作");
        Deque<String> printQueue = new ArrayDeque<>();
        printQueue.offerLast("報告.pdf");
        printQueue.offerLast("履歷.docx");
        printQueue.offerLast("海報.png");
        System.out.println("佇列 =" + printQueue);
        System.out.println("peekFirst() =" + printQueue.peekFirst());
        while (!printQueue.isEmpty()) {
            System.out.println("列印 " + printQueue.pollFirst() + "剩" + printQueue);
        }
        System.out.println("空佇列 pollFirst() =" + printQueue.pollFirst());
        System.out.println("需求5：復原最近操作");
        Deque<String> undoStack = new ArrayDeque<>();
        undoStack.push("新增文字");
        undoStack.push("設定粗體");
        undoStack.push("插入圖片");
        undoStack.push("調整行距");
        System.out.println("stack =" + undoStack);
        System.out.println("peek() =" + undoStack.peek());
        System.out.println("undo 撤銷" + undoStack.pollFirst() + " 剩 " + undoStack);
        System.out.println("undo 撤銷" + undoStack.pollFirst() + " 剩 " + undoStack);
		
        trace("ArrayList", new ArrayList<>());
        trace("LinkedList", new LinkedList<>());
        String[] raw = { "B", "A", "C", "A", "D" };
        List<String> asList = new ArrayList<>();
        Set<String> asSet = new HashSet<>();
        Map<String, Integer> asMap = new HashMap<>();
        Deque<String> asQueue = new ArrayDeque<>();
        Deque<String> asStack = new ArrayDeque<>();
        for (String s : raw) {
            asList.add(s);
            asSet.add(s);
            asMap.put(s, asMap.getOrDefault(s, 0) + 1);
            asQueue.offerLast(s);
            asStack.push(s);
        }
        System.out.println("原始輸入 = [B, A, C, A, D]");
        System.out.println("List" + asList + " size=" + asList.size());
        System.out.println("Set" + asSet + " size=" + asSet.size());
        System.out.println("Map" + asMap);
        System.out.println("Queue" + asQueue + "取出" + asQueue.pollFirst());
        System.out.println("Stack" + asStack + "取出" + asStack.pollFirst());
        System.out.println("new ArrayList<>().isEmpty() = " + new ArrayList<>().isEmpty());
        System.out.println("new HashMap<>().get(x) = " + new HashMap<>().get("x"));
        System.out.println("new ArrayDeque<>().pollFirst() = " + new ArrayDeque<>().pollFirst());
        System.out.println("new ArrayDeque<>().peekFirst() = " + new ArrayDeque<>().peekFirst());
    }
}
