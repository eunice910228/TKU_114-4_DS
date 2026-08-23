import java.util.ArrayDeque;
import java.util.Deque;

class TextEditor {
    private Deque<String> undoStack = new ArrayDeque<>();
    private Deque<String> redoStack = new ArrayDeque<>();
    private String content = "";

    boolean type(String text) {
        if (text == null || text.isEmpty()) {
            return false;
        }
        undoStack.push(content);
        content = content + text;
        redoStack.clear();
        return true;
    }

    boolean delete(int n) {
        if (n <= 0 || content.isEmpty()) {
            return false;
        }
        undoStack.push(content);
        int keep = Math.max(0, content.length() - n);
        content = content.substring(0, keep);
        redoStack.clear();
        return true;
    }

    boolean undo() {
        if (undoStack.isEmpty()) {
            return false;
        }
        redoStack.push(content);
        content = undoStack.pollFirst();
        return true;
    }

    boolean redo() {
        if (redoStack.isEmpty()) {
            return false;
        }
        undoStack.push(content);
        content = redoStack.pollFirst();
        return true;
    }

    String getContent() {
        return content;
    }

    boolean canUndo() {
        return !undoStack.isEmpty();
    }

    boolean canRedo() {
        return !redoStack.isEmpty();
    }

    int redoDepth() {
        return redoStack.size();
    }

    String state() {
        return "內容=[" + content + "] undo=" + undoStack.size()
                + " redo=" + redoStack.size();
    }
}

public class TextEditorHistory {

    static void show(String label, boolean ok, TextEditor editor) {
        System.out.println("  " + label + " = " + ok + "  " + editor.state());
    }

    public static void main(String[] args) {
        TextEditor editor = new TextEditor();

        System.out.println("空stack");
        show("undo()", editor.undo(), editor);
        show("redo()", editor.redo(), editor);
        System.out.println("輸入文字");
        show("type(Hello)", editor.type("Hello"), editor);
        show("type( )", editor.type(" "), editor);
        show("type(World)", editor.type("World"), editor);
        show("type(!)", editor.type("!"), editor);
        System.out.println("undo 兩次");
        show("undo()", editor.undo(), editor);
        show("undo()", editor.undo(), editor);
        System.out.println("redo 一次");
        show("redo()", editor.redo(), editor);
        System.out.println("redo 還有東西時輸入新內容，redo 會被清空");
        System.out.println("  輸入前 redo 深度 = " + editor.redoDepth());
        show("type(?)", editor.type("?"), editor);
        System.out.println("  輸入後 redo 深度 = " + editor.redoDepth());
        show("redo()", editor.redo(), editor);
        System.out.println("delete 也可以 undo");
        show("delete(6)", editor.delete(6), editor);
        show("undo()", editor.undo(), editor);
        show("redo()", editor.redo(), editor);
        System.out.println("連續 undo 到底");
        while (editor.canUndo()) {
            show("undo()", editor.undo(), editor);
        }
        show("undo()", editor.undo(), editor);
        System.out.println("連續 redo 到底");
        while (editor.canRedo()) {
            show("redo()", editor.redo(), editor);
        }
        show("redo()", editor.redo(), editor);
        System.out.println("不合法輸入");
        TextEditor other = new TextEditor();
        show("type(null)", other.type(null), other);
        show("type(空字串)", other.type(""), other);
        show("delete(5) 內容為空", other.delete(5), other);
        show("type(abc)", other.type("abc"), other);
        show("delete(0)", other.delete(0), other);
        show("delete(-1)", other.delete(-1), other);
        show("delete(99) 超過長度", other.delete(99), other);
    }
}
