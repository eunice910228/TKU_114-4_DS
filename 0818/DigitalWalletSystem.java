class Wallet {
    private final String walletId;
    private final String owner;
    private int balance;
    private int times;
	// 宣告錢包屬性
    Wallet(String walletId, String owner) {
        this.walletId = (walletId == null || walletId.isBlank())
                ? "UNKNOWN" : walletId.trim();
        this.owner = (owner == null || owner.isBlank()) ? "Unknown" : owner.trim();
        this.balance = 0;        

    }
    // 驗證失敗 : 直接離開
    boolean deposit(int amount) {
        if (amount <= 0) {
            return false;              
        }
		balance = balance + amount;
        times++;
        return true;
    }
    // 支付
    boolean pay(int amount) {
        if (amount <= 0 || amount > balance) {
            return false;              
        }
        balance = balance - amount;
        times++;
        return true;
    }
	//  退款
    boolean refund(int amount) {
        if (amount <= 0) {
            return false;
        }
        balance = balance+amount;
        times++;
        return true;
    }
    int getBalance() {
        return balance;
    }
    int getTimes() {
        return times;
    }
    @Override
    public String toString() {
        return walletId + " owner=" + owner
                + " balance=" + balance
                + " 交易成功次數="  + times;
    }
}
//測試錢包功能
public class DigitalWalletSystem {
    public static void main(String[] args) {
        Wallet wallet = new Wallet("A01", "Eunice");
        System.out.println("錢包資訊：" + wallet);
        System.out.println("正常儲值 2000：" + wallet.deposit(2000));
        System.out.println("正常付款 270：" + wallet.pay(270));
        System.out.println("餘額不足 1800：" + wallet.pay(1800));
        System.out.println("負數金額 -500：" + wallet.deposit(-500));
        System.out.println("退款 100：" + wallet.refund(100));
        System.out.println("錢包資訊");
        System.out.println(wallet);
        System.out.println("成功交易數是否等於3：" + (wallet.getTimes() == 3));
    }
}
