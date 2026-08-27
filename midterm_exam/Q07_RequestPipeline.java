import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class Q07_RequestPipeline {
    public static boolean isBalanced(String text) {
        if (text == null) {
            return false;
        }
        Deque<Character> stack = new ArrayDeque<>();
        for (char symbol : text.toCharArray()) {
            if (symbol == '(' || symbol == '[' || symbol == '{') {
                stack.push(symbol);
            } else if (symbol == ')' || symbol == ']' || symbol == '}') {
                if (stack.isEmpty() || !matches(stack.pop(), symbol)) {
                    return false;
                }
            }
        }
        return stack.isEmpty();
    }
    static boolean matches(char open, char close) {
        return (open == '(' && close == ')')
                || (open == '[' && close == ']')
                || (open == '{' && close == '}');
    }
    public static List<String> process(String[] commands) {
        List<String> result = new ArrayList<>();
        if (commands == null) {
            return result;
        }
        Deque<String> normal = new ArrayDeque<>();
        Deque<String> urgent = new ArrayDeque<>();
        for (String command : commands) {
            if (command == null || command.isBlank()) {
                continue;
            }
            String trimmed = command.trim();
            if (trimmed.equals("PROCESS")) {
                if (!urgent.isEmpty()) {
                    result.add(urgent.pollFirst());
                } else if (!normal.isEmpty()) {
                    result.add(normal.pollFirst());
                } else {
                    result.add("EMPTY");
                }
            } else if (trimmed.startsWith("NORMAL ")) {
                String id = trimmed.substring(7).trim();
                if (!id.isEmpty() && !id.contains(" ")) {
                    normal.offerLast(id);
                }
            } else if (trimmed.startsWith("URGENT ")) {
                String id = trimmed.substring(7).trim();
                if (!id.isEmpty() && !id.contains(" ")) {
                    urgent.offerLast(id);
                }
            }
        }
        return result;
    }
    public static void main(String[] args) {
        String[] commands = {
                "NORMAL N1", "URGENT U1", "NORMAL N2", "PROCESS", "PROCESS", "PROCESS"
        };
        System.out.println(isBalanced("a{b[c](d)}"));
        System.out.println(isBalanced("([)]"));
        System.out.println(process(commands));
    }
}