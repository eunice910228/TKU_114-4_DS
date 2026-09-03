public class RecursiveCallReport {
    static int sum(int[] data, int index) {
        if (index >= data.length) {
            System.out.println("index=" + index + " 到底，回傳0");
            return 0;
        }
        System.out.println("index=" + index + " current=" + data[index] + " 往下呼叫");
        int rest = sum(data, index + 1);
        int result = data[index] + rest;
        System.out.println("index=" + index + " recursive result=" + rest + " return=" + result);
        return result;
    }
    static void run(String title, int[] data) {
        System.out.println(title);
        System.out.println("總和=" + sum(data, 0));
    }
    public static void main(String[] args) {
        run("一般陣列", new int[]{ 3, 8, 5 });
        run("單一元素", new int[]{ 42 });
        run("empty array", new int[0]);
    }
}
