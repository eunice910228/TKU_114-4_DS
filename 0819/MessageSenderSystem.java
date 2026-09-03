interface MessageSender {
    boolean send(String receiver, String message);
    default String channelName() {
        return "UNKNOWN";
    }
}
class MessageValidator {
    static boolean isValid(String receiver, String message) {
        if (receiver == null || receiver.isBlank()) {
            return false;
        }
        if (message == null || message.isBlank()) {
            return false;
        }
        return true;
    }
}
class EmailSender implements MessageSender {
    @Override
    public boolean send(String receiver, String message) {
        if (!MessageValidator.isValid(receiver, message)) {
            return false;
        }
        if (!receiver.contains("@")) {
            return false;
        }
        System.out.println("[EMAIL] " + receiver + " : " + message);
        return true;
    }
    @Override
    public String channelName() {
        return "EMAIL";
    }
}
class SmsSender implements MessageSender {
    private static final int MAX_LENGTH = 70;
    @Override
    public boolean send(String receiver, String message) {
        if (!MessageValidator.isValid(receiver, message)) {
            return false;
        }
        if (!isMobileNumber(receiver)) {
            return false;
        }
        String body = message.length() > MAX_LENGTH ? message.substring(0, MAX_LENGTH) + "..." : message;
        System.out.println("[SMS] " + receiver + " : " + body);
        return true;
    }
    @Override
    public String channelName() {
        return "SMS";
    }
    static boolean isMobileNumber(String number) {
        if (number == null || number.length() != 10) {
            return false;
        }
        if (!number.startsWith("09")) {
            return false;
        }
        for (char c : number.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }
}
class ConsoleSender implements MessageSender {
    @Override
    public boolean send(String receiver, String message) {
        if (!MessageValidator.isValid(receiver, message)) {
            return false;
        }
        System.out.println("[CONSOLE] " + receiver + " : " + message);
        return true;
    }
    @Override
    public String channelName() {
        return "CONSOLE";
    }
}
public class MessageSenderSystem {
    static void notifyUser(MessageSender sender, String receiver, String message) {
        if (sender == null) {
            System.out.println("未指定管道=false");
            return;
        }
        boolean ok = sender.send(receiver, message);
        System.out.println(sender.channelName() + "=" + ok);
    }
    public static void main(String[] args) {
        MessageSender email = new EmailSender();
        MessageSender sms = new SmsSender();
        MessageSender console = new ConsoleSender();
        notifyUser(email, "eunice@example.com", "您的訂單已出貨");
        notifyUser(sms, "0912345678", "驗證碼482913");
        notifyUser(console, "counter-01", "櫃台叫號A035");
        notifyUser(email, "eunice.example.com", "缺少@");
        notifyUser(sms, "12345", "門號不合法");
        notifyUser(sms, "09123456ab", "門號含英文");
        notifyUser(email, "", "receiver空白");
        notifyUser(console, "counter-01", "   ");
        notifyUser(sms, null, "receiver是null");
        notifyUser(null, "eunice@example.com", "sender是null");
        MessageSender[] senders = { email, sms, console };
        System.out.println("同一段流程跑過所有管道");
        for (MessageSender s : senders) {
            System.out.println(s.channelName());
        }
    }
}
