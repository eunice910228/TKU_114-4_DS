import java.util.ArrayList;
import java.util.List;
public class HeapPropertyValidator {
    static boolean isMinHeap(List<Integer> data) {
        if (data == null) {
            return false;
        }
        for (int i = 1; i < data.size(); i++) {
            int parent = (i - 1) / 2;
            if (data.get(parent) > data.get(i)) {
                return false;
            }
        }
        return true;
    }
    static boolean isMaxHeap(List<Integer> data) {
        if (data == null) {
            return false;
        }
        for (int i = 1; i < data.size(); i++) {
            int parent = (i - 1) / 2;
            if (data.get(parent) < data.get(i)) {
                return false;
            }
        }
        return true;
    }
    static void report(String label, List<Integer> data) {
        System.out.println(label + " " + data + " min=" + isMinHeap(data) + " max=" + isMaxHeap(data));
    }
    public static void main(String[] args) {
        report("合法min heap", List.of(1, 3, 2, 7, 5, 4));
        report("合法max heap", List.of(9, 7, 8, 3, 5, 2));
        report("兩者皆非", List.of(3, 9, 1));
        report("重複值min", List.of(2, 2, 5, 2));
        report("empty", new ArrayList<>());
        report("單一元素", List.of(42));
        System.out.println("null min=" + isMinHeap(null) + " max=" + isMaxHeap(null));
    }
}
