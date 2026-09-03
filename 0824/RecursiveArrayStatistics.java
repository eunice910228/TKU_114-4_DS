public class RecursiveArrayStatistics {
    static int maximum(int[] data) {
        check(data);
        return maximum(data, 0);
    }
    private static int maximum(int[] data, int index) {
        if (index == data.length - 1) {
            return data[index];
        }
        return Math.max(data[index], maximum(data, index + 1));
    }
    static int minimum(int[] data) {
        check(data);
        return minimum(data, 0);
    }
    private static int minimum(int[] data, int index) {
        if (index == data.length - 1) {
            return data[index];
        }
        return Math.min(data[index], minimum(data, index + 1));
    }
    static int countAbove(int[] data, int threshold) {
        check(data);
        return countAbove(data, threshold, 0);
    }
    private static int countAbove(int[] data, int threshold, int index) {
        if (index == data.length) {
            return 0;
        }
        int here = data[index] > threshold ? 1 : 0;
        return here + countAbove(data, threshold, index + 1);
    }
    private static void check(int[] data) {
        if (data == null || data.length == 0) {
            throw new IllegalArgumentException("array 不可為 null 或 empty");
        }
    }
    static void tryBad(int[] data, String label) {
        try {
            maximum(data);
            System.out.println(label + "=未丟例外");
        } catch (IllegalArgumentException e) {
            System.out.println(label + "=" + e.getMessage());
        }
    }
    public static void main(String[] args) {
        int[] data = { 42, -7, 88, 15, 88, 3 };
        System.out.println("maximum=" + maximum(data));
        System.out.println("minimum=" + minimum(data));
        System.out.println("countAbove(20)=" + countAbove(data, 20));
        System.out.println("countAbove(100)=" + countAbove(data, 100));
        int[] single = { 5 };
        System.out.println("單元素 max=" + maximum(single) + " min=" + minimum(single) + " countAbove(4)=" + countAbove(single, 4));
        tryBad(null, "null array");
        tryBad(new int[0], "empty array");
    }
}
