import java.util.Arrays;
public class GenericArrayTools {
    static <T> int countMatches(T[] data, T target) {
        if (data == null) {
            return 0;
        }
        int count = 0;
        for (T item : data) {
            if (item == null ? target == null : item.equals(target)) {
                count++;
            }
        }
        return count;
    }
    static <T> T last(T[] data) {
        if (data == null || data.length == 0) {
            return null;
        }
        return data[data.length - 1];
    }
    static <T> void swap(T[] data, int first, int second) {
        if (data == null) {
            return;
        }
        if (first < 0 || first >= data.length || second < 0 || second >= data.length) {
            return;
        }
        if (first == second) {
            return;
        }
        T temp = data[first];
        data[first] = data[second];
        data[second] = temp;
    }
    static <T> int indexOf(T[] data, T target) {
        if (data == null) {
            return -1;
        }
        for (int i = 0; i < data.length; i++) {
            if (data[i] == null ? target == null : data[i].equals(target)) {
                return i;
            }
        }
        return -1;
    }
    public static void main(String[] args) {
        String[] names = { "Eunice", "Bsy", "Eunice", "Candice", null, "Eunice" };
        Integer[] scores = { 90, 85, 90, 72 };
        System.out.println("names=" + Arrays.toString(names));
        System.out.println("Eunice出現=" + countMatches(names, "Eunice"));
        System.out.println("null出現=" + countMatches(names, null));
        System.out.println("Doris出現=" + countMatches(names, "Doris"));
        System.out.println("scores中90出現=" + countMatches(scores, 90));
        System.out.println("names最後一個=" + last(names));
        System.out.println("scores最後一個=" + last(scores));
        Integer[] nums = { 10, 20, 30, 40 };
        System.out.println("交換前=" + Arrays.toString(nums));
        swap(nums, 0, 3);
        System.out.println("swap(0,3)=" + Arrays.toString(nums));
        swap(nums, 1, 1);
        System.out.println("swap(1,1)=" + Arrays.toString(nums));
        System.out.println("Candice在index=" + indexOf(names, "Candice"));
        System.out.println("Doris在index=" + indexOf(names, "Doris"));
        String[] nullArray = null;
        System.out.println("null陣列countMatches=" + countMatches(nullArray, "Eunice"));
        System.out.println("null陣列last=" + last(nullArray));
        swap(nullArray, 0, 1);
        String[] emptyArray = new String[0];
        System.out.println("空陣列countMatches=" + countMatches(emptyArray, "Eunice"));
        System.out.println("空陣列last=" + last(emptyArray));
        swap(emptyArray, 0, 0);
        swap(nums, -1, 2);
        swap(nums, 0, 99);
        System.out.println("不合法index後陣列未被破壞=" + Arrays.toString(nums));
        Double[] prices = { 19.9, 25.0, 19.9 };
        System.out.println("19.9出現=" + countMatches(prices, 19.9));
    }
}
