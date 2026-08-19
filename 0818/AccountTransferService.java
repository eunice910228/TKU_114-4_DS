class Account {
    private final String id;
    private int balance;
    Account(String id, int balance) {
        this.id = (id == null || id.isBlank()) ? "UNKNOWN" : id.trim();
        this.balance = Math.max(0, balance);
    }
    boolean withdraw(int amount) {
        if (amount <= 0 || amount > balance) {
            return false;
        }
        balance = balance - amount;
        return true;
    }
    void deposit(int amount) {
        if (amount > 0) {
            balance = balance + amount;
        }
    }
    int getBalance() {
        return balance;
    }
    @Override
    public String toString() {
        return id + " balance=" + balance;
    }
}

class TransferService {
    static boolean transfer(Account source, Account target, int amount) {
        if (source == null || target == null) {
            return false;
        }
        if (source == target) {
            return false; 
        }
        if (amount <= 0) {
            return false;
        }
        if (!source.withdraw(amount)) { 
            return false;
        }
        target.deposit(amount);
        return true;
    }
}

public class AccountTransferService {
    public static void main(String[] args) {
        Account e = new Account("Eunice", 1000);
        Account b = new Account("Bsy", 200);
        System.out.println("初始：" + e + " / " + b);
        System.out.println("=== 四種測試情境 ===");
        System.out.println("成功轉帳 800：" + TransferService.transfer(e, b, 800));
        System.out.println("餘額不足 3000：" + TransferService.transfer(e, b, 3000));
        System.out.println("同帳戶轉帳 100：" + TransferService.transfer(e, e, 100));
        System.out.println("目標為 null 100：" + TransferService.transfer(e, null, 100));
        System.out.println("=== 最終狀態 ===");
        System.out.println(e);
        System.out.println(b);
        System.out.println("總金額（1200）："
                + (e.getBalance() + b.getBalance() == 1200));
    }
}
