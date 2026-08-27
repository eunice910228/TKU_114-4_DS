public class Q08_RecursiveAudit {
    public static int sumValid(int[] data, int index) {
        if (data == null || index >= data.length) {
            return 0;
        }
        if (index < 0) {
            return sumValid(data, 0);
        }
        int here = (data[index] >= 0 && data[index] <= 100) ? data[index] : 0;
        return here + sumValid(data, index + 1);
    }
    public static int countOccurrences(int[] data, int index, int target) {
        if (data == null || index >= data.length) {
            return 0;
        }
        if (index < 0) {
            return countOccurrences(data, 0, target);
        }
        int here = (data[index] == target) ? 1 : 0;
        return here + countOccurrences(data, index + 1, target);
    }
    public static boolean isPalindrome(String text, int left, int right) {
        if (text == null) {
            return false;
        }
        if (left < 0) {
            return isPalindrome(text, 0, right);
        }
        if (right > text.length() - 1) {
            return isPalindrome(text, left, text.length() - 1);
        }
        if (left >= right) {
            return true;
        }
        char a = Character.toLowerCase(text.charAt(left));
        char b = Character.toLowerCase(text.charAt(right));
        if (a != b) {
            return false;
        }
        return isPalindrome(text, left + 1, right - 1);
    }
    public static void main(String[] args) {
        int[] data = {10, -1, 20, 101, 20};
        System.out.println(sumValid(data, 0));
        System.out.println(countOccurrences(data, 0, 20));
        System.out.println(isPalindrome("Level", 0, 4));
    }
}
