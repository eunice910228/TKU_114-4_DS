import java.util.ArrayList;
import java.util.List;
class Requirement {
    String need;
    String choice;
    String reason;
    String bigO;
    Requirement(String need, String choice, String reason, String bigO) {
        this.need = need;
        this.choice = choice;
        this.reason = reason;
        this.bigO = bigO;
    }
}
public class DataStructureDecisionReport {
    private final List<Requirement> requirements = new ArrayList<>();
    boolean add(String need, String choice, String reason, String bigO) {
        if (need == null || need.isBlank() || choice == null || choice.isBlank()) {
            return false;
        }
        requirements.add(new Requirement(need, choice, reason, bigO));
        return true;
    }
    int size() {
        return requirements.size();
    }
    void report() {
        System.out.println("資料結構選擇報告");
        for (int i = 0; i < requirements.size(); i++) {
            Requirement r = requirements.get(i);
            System.out.println((i + 1) + ". " + r.need);
            System.out.println("   選擇=" + r.choice + "，理由=" + r.reason
                    + "，主要操作=" + r.bigO);
        }
    }
    public static void main(String[] args) {
        DataStructureDecisionReport report = new DataStructureDecisionReport();
        report.add("依索引讀取固定順序的資料", "ArrayList",
                "index存取直接定位", "get為O(1)");
        report.add("先進先出的排隊處理", "Queue（ArrayDeque）",
                "兩端操作各自O(1)", "offer與poll為O(1)");
        report.add("需要復原上一步", "Stack（ArrayDeque）",
                "後進先出符合復原順序", "push與pop為O(1)");
        report.add("依key快速查詢資料", "HashMap",
                "hash直接算出bucket", "平均O(1)");
        report.add("判斷元素是否重複", "HashSet",
                "add回傳false即重複", "平均O(1)");
        report.add("隨時取出最小值", "PriorityQueue",
                "heap性質讓最小值在根", "poll為O(log n)");
        report.add("需要排序輸出的查詢", "BST（TreeMap）",
                "inorder即升冪", "查詢O(log n)");
        report.add("查詢某範圍內的所有key", "BST",
                "可用範圍剪枝跳過整棵subtree", "O(log n + k)");
        report.add("保存物件之間的連結關係", "Graph（adjacency list）",
                "只存實際存在的edge", "走訪鄰居O(degree)");
        report.add("找出兩點間最少轉乘", "Graph + BFS",
                "BFS逐層擴散即最少edge數", "O(V + E)");
        report.add("判斷是否可從A走到B", "Graph + DFS",
                "只需判斷可達不需最短", "O(V + E)");
        report.add("找出所有互相連通的群組", "Graph + BFS",
                "對每個未走訪vertex啟動一次", "O(V + E)");
        System.out.println("需求筆數=" + report.size());
        System.out.println("空需求add=" + report.add("  ", "List", "x", "x"));
        System.out.println("null需求add=" + report.add(null, "List", "x", "x"));
        System.out.println("空報告筆數=" + new DataStructureDecisionReport().size());
        report.report();
    }
}
