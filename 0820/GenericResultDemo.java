class Result<T> {
    private final boolean success;
    private final String message;
    private final T data;
    private Result(boolean success, String message, T data) {
        this.success = success;
        this.message = (message == null) ? "" : message;
        this.data = data;
    }
    static <T> Result<T> ok(T data) {
        return new Result<>(true, "OK", data);
    }
    static <T> Result<T> fail(String message) {
        return new Result<>(false, message, null);
    }
    boolean isSuccess() {
        return success;
    }
    String getMessage() {
        return message;
    }
    T getData() {
        return data;
    }
    T orElse(T fallback) {
        return (success && data != null) ? data : fallback;
    }
    @Override
    public String toString() {
        if (success) {
            return "success=true data=" + data;
        }
        return "success=false message=" + message + " data=null";
    }
}
class UserService {
    static Result<String> findUserName(String id) {
        if (id == null || id.isBlank()) {
            return Result.fail("id不可為空");
        }
        if ("U001".equals(id)) {
            return Result.ok("Eunice");
        }
        return Result.fail("查無此使用者" + id);
    }
    static Result<Integer> parseAge(String text) {
        if (text == null || text.isBlank()) {
            return Result.fail("輸入為空");
        }
        try {
            int age = Integer.parseInt(text.trim());
            if (age < 0 || age > 150) {
                return Result.fail("年齡超出範圍" + age);
            }
            return Result.ok(age);
        } catch (NumberFormatException e) {
            return Result.fail("不是數字" + text);
        }
    }
}
public class GenericResultDemo {
    public static void main(String[] args) {
        Result<String> found = UserService.findUserName("U001");
        Result<String> notFound = UserService.findUserName("U999");
        Result<String> badInput = UserService.findUserName("  ");
        if (found.isSuccess()) {
            String name = found.getData();
            System.out.println("姓名長度=" + name.length());
        }
        System.out.println("found=" + found);
        System.out.println("notFound=" + notFound);
        System.out.println("badInput=" + badInput);
        Result<Integer> age = UserService.parseAge("28");
        Result<Integer> bad = UserService.parseAge("abc");
        Result<Integer> over = UserService.parseAge("200");
        if (age.isSuccess()) {
            int value = age.getData();
            System.out.println("十年後=" + (value + 10));
        }
        System.out.println("age=" + age);
        System.out.println("bad=" + bad);
        System.out.println("over=" + over);
        System.out.println("失敗時getData=" + notFound.getData());
        System.out.println("失敗時orElse=" + notFound.orElse("訪客"));
        System.out.println("bad.orElse(0)=" + bad.orElse(0));
        Result<int[]> scores = Result.ok(new int[]{ 90, 85, 78 });
        System.out.println("第一筆分數=" + scores.getData()[0]);
        Result<int[]> empty = Result.fail("尚未輸入成績");
        System.out.println("empty=" + empty + "，orElse長度=" + empty.orElse(new int[0]).length);
    }
}
