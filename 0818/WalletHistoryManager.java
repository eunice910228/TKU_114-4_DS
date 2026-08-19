final class WalletTransaction { 
    private final int sequence;
    private final String type;
    private final int amount;
    private final int balanceAfter;
    WalletTransaction(int sequence, String type, int amount, int balanceAfter) {
        this.sequence = sequence;
        this.type = type;
        this.amount = amount;
        this.balanceAfter = balanceAfter;
    }
    int getSequence() {
        return sequence;
    }
    String getType() {
        return type;
    }
    int getAmount() {
        return amount;
    }
    @Override
    public String toString() {
        return sequence + " " + type + " " + amount + " balance=" + balanceAfter;
    }
}

class DigitalWallet {
    private final String walletId;
    private final String owner;
    private int balance;
    private final WalletTransaction[] transactions;
    private int transactionCount;
    DigitalWallet(String walletId, String owner, int historyCapacity) {
        this.walletId = (walletId == null || walletId.isBlank())
                ? "UNKNOWN" : walletId.trim();
        this.owner = (owner == null || owner.isBlank()) ? "Unknown" : owner.trim();
        this.balance = 0;
        this.transactions = new WalletTransaction[Math.max(1, historyCapacity)];
        this.transactionCount = 0;
    }
    private boolean hasRoom() {
        return transactionCount < transactions.length;
    }
    boolean deposit(int amount) {
        if (amount <= 0 || !hasRoom()) {
            return false;
        }
        balance = balance + amount;
        record("DEPOSIT", amount);
        return true;
    }
    boolean pay(int amount) {
        if (amount <= 0 || amount > balance || !hasRoom()) {
            return false;
        }
        balance = balance - amount;
        record("PAY", amount);
        return true;
    }
    boolean refund(int amount) {
        if (amount <= 0 || !hasRoom()) {
            return false;
        }
        balance = balance + amount;
        record("REFUND", amount);
        return true;
    }
    boolean transferTo(DigitalWallet target, int amount) {
        if (target == null || target == this) {
            return false;
        }
        if (amount <= 0 || amount > this.balance) {
            return false;
        }
        if (!this.hasRoom() || !target.hasRoom()) {
            return false;
        }
        this.balance = balance - amount;
        this.record("TRANSFER_OUT", amount);
        target.balance = balance + amount; 
        target.record("TRANSFER_IN", amount);
        return true;
    }
    WalletTransaction findTransaction(int sequence) {
        for (int i = 0; i < transactionCount; i++) {
            if (transactions[i].getSequence() == sequence) {
                return transactions[i];
            }
        }
        return null;
    }
    int totalByType(String type) {
        if (type == null) {
            return 0;
        }
        int total = 0;
        for (int i = 0; i < transactionCount; i++) {
            if (type.equals(transactions[i].getType())) { 
                total = total + transactions[i].getAmount();
            }
        }
        return total;
    }
    private void record(String type, int amount) {
        transactions[transactionCount] =
                new WalletTransaction(transactionCount + 1, type, amount, balance);
        transactionCount++;
    }
    void printStatement() {
        System.out.println("--- " + walletId + " owner=" + owner
                + " balance=" + balance
                + " (" + transactionCount + "/" + transactions.length + ") ---");
        for (int i = 0; i < transactionCount; i++) {
            System.out.println(transactions[i]);
        }
    }
}

public class WalletHistoryManager {
    public static void main(String[] args) {
        DigitalWallet Eunice = new DigitalWallet("A01", "Eunice", 6);
        DigitalWallet Bsy = new DigitalWallet("A02", "Bsy", 2);
        System.out.println("=== 基本操作 ===");
        System.out.println("Eunice 儲值 3000：" + Eunice.deposit(3000));
        System.out.println("Eunice 付款 500：" + Eunice.pay(500));
        System.out.println("Eunice 付款 800：" + Eunice.pay(800)); 
        System.out.println("Eunice 退款 100：" + Eunice.refund(100));
        System.out.println("=== 轉帳測試 ===");
        System.out.println("Eunice -> Bsy 300：" + Eunice.transferTo(Bsy, 300));
        System.out.println("Eunice -> Bsy 200：" + Eunice.transferTo(Bsy, 200));
        // Bsy 的紀錄已滿（2/2），下一筆必須整筆失敗
        System.out.println("Eunice -> Bsy 100（Bsy 紀錄已滿）：" + Eunice.transferTo(Bsy, 100));
        System.out.println("Eunice -> Eunice 100（同一錢包）    ：" + Eunice.transferTo(Eunice, 100));
        System.out.println("=== 需求 1：findTransaction ===");
        System.out.println("查 sequence 2：" + Eunice.findTransaction(2));
        System.out.println("查 sequence 99：" + Eunice.findTransaction(99));
        System.out.println("=== 需求 2：totalByType ===");
        System.out.println("Eunice DEPOSIT 總額     ：" + Eunice.totalByType("DEPOSIT"));
        System.out.println("Eunice TRANSFER_OUT 總額：" + Eunice.totalByType("TRANSFER_OUT"));
        System.out.println("Bsy TRANSFER_IN 總額 ：" + Bsy.totalByType("TRANSFER_IN"));
        System.out.println("=== 需求 5：完整 statement ===");
        Eunice.printStatement();
        Bsy.printStatement();
    }
}
