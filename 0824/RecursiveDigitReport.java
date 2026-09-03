public class RecursiveDigitReport {
    static int digitSum(int n) {
        n = Math.abs(n);
        if (n < 10) {
            return n;
        }
        return n % 10 + digitSum(n / 10);
    }
    static int digitCount(int n) {
        n = Math.abs(n);
        if (n < 10) {
            return 1;
        }
        return 1 + digitCount(n / 10);
    }
    static int countDigit(int n, int digit) {
        n = Math.abs(n);
        int here = (n % 10 == digit) ? 1 : 0;
        if (n < 10) {
            return here;
        }
        return here + countDigit(n / 10, digit);
    }
    public static void main(String[] args) {
        for (int n : new int[]{ 50205, 0, -731 }) {
            System.out.println(n + " digitSum=" + digitSum(n) + " digitCount=" + digitCount(n)
                    + " countDigit(0)=" + countDigit(n, 0) + " countDigit(5)=" + countDigit(n, 5));
        }
    }
}
