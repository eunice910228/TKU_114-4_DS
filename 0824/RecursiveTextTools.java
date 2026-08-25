public class RecursiveTextTools {
    static String reverse(String text) {
        if (text == null || text.length() <= 1) {
            return text == null ? "" : text;
        }
        return reverse(text.substring(1)) + text.charAt(0);
    }
    static boolean isPalindrome(String text) {
        if (text == null) {
            return true;
        }
        String cleaned = clean(text.toLowerCase());
        return check(cleaned, 0, cleaned.length() - 1);
    }
    private static String clean(String text) {
        if (text.isEmpty()) {
            return "";
        }
        char first = text.charAt(0);
        String rest = clean(text.substring(1));
        if (first == ' ') {
            return rest;
        }
        return first + rest;
    }
    private static boolean check(String text, int left, int right) {
        if (left >= right) {
            return true;
        }
        if (text.charAt(left) != text.charAt(right)) {
            return false;
        }
        return check(text, left + 1, right - 1);
    }
    public static void main(String[] args) {
        System.out.println("reverse");
        System.out.println("  \"\" -> \"" + reverse("") + "\"");
        System.out.println("  \"a\" -> \"" + reverse("a") + "\"");
        System.out.println("  \"Level\" -> \"" + reverse("Level") + "\"");
        System.out.println("  \"Java Tree\" -> \"" + reverse("Java Tree") + "\"");
        System.out.println("isPalindrome");
        System.out.println("  \"\" -> " + isPalindrome(""));
        System.out.println("  \"a\" -> " + isPalindrome("a"));
        System.out.println("  \"Level\" -> " + isPalindrome("Level"));
        System.out.println("  \"never odd or even\" -> " + isPalindrome("never odd or even"));
        System.out.println("  \"Java\" -> " + isPalindrome("Java"));

    }
}
