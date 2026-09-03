import java.util.ArrayDeque;
import java.util.Deque;
public class BrowserBackStack {
    private final Deque<String> history = new ArrayDeque<>();
    boolean visit(String url) {
        if (url == null || url.isBlank()) {
            return false;
        }
        history.push(url);
        return true;
    }
    String back() {
        if (history.size() <= 1) {
            return null;
        }
        history.pop();
        return history.peek();
    }
    String current() {
        return history.peek();
    }
    public static void main(String[] args) {
        BrowserBackStack browser = new BrowserBackStack();
        System.out.println("空stack current=" + browser.current());
        System.out.println("空stack back=" + browser.back());
        System.out.println("visit home=" + browser.visit("home"));
        System.out.println("visit news=" + browser.visit("news"));
        System.out.println("visit sports=" + browser.visit("sports"));
        System.out.println("空白url visit=" + browser.visit("  "));
        System.out.println("current=" + browser.current());
        System.out.println("back=" + browser.back());
        System.out.println("back=" + browser.back());
        System.out.println("只剩一頁再back=" + browser.back());
        System.out.println("current=" + browser.current());
    }
}
