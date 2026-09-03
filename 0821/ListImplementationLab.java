import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
public class ListImplementationLab {
    static void appendAll(List<Integer> list, int[] values) {
        for (int value : values) {
            list.add(value);
        }
    }
    static boolean insertAt(List<Integer> list, int index, int value) {
        if (index < 0 || index > list.size()) {
            return false;
        }
        list.add(index, value);
        return true;
    }
    static int find(List<Integer> list, int target) {
        return list.indexOf(target);
    }
    static boolean removeValue(List<Integer> list, int target) {
        return list.remove(Integer.valueOf(target));
    }
    static int sum(List<Integer> list) {
        int total = 0;
        for (int value : list) {
            total += value;
        }
        return total;
    }
    static void runAll(String title, List<Integer> list) {
        System.out.println(title);
        appendAll(list, new int[]{ 10, 20, 30 });
        System.out.println("尾端新增後=" + list);
        System.out.println("插入index1=" + insertAt(list, 1, 15) + " " + list);
        System.out.println("插入index99=" + insertAt(list, 99, 0));
        System.out.println("搜尋20的index=" + find(list, 20));
        System.out.println("搜尋99的index=" + find(list, 99));
        System.out.println("刪除15=" + removeValue(list, 15) + " " + list);
        System.out.println("刪除99=" + removeValue(list, 99));
        System.out.println("總和=" + sum(list));
    }
    public static void main(String[] args) {
        runAll("ArrayList", new ArrayList<>());
        runAll("LinkedList", new LinkedList<>());
        System.out.println("空list總和=" + sum(new ArrayList<>()));
        System.out.println("成本差異：ArrayList依index存取快、中間插入刪除要搬移；LinkedList頭尾插入刪除快、依index存取要逐一走訪");
    }
}
