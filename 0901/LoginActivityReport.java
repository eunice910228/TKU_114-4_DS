import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
public class LoginActivityReport {
    private final Map<String, Integer> loginCount = new HashMap<>();
    private final Map<String, Set<String>> accountIps = new HashMap<>();
    boolean record(String account, String ip) {
        if (account == null || account.isBlank() || ip == null || ip.isBlank()) {
            return false;
        }
        loginCount.put(account, loginCount.getOrDefault(account, 0) + 1);
        Set<String> ips = accountIps.get(account);
        if (ips == null) {
            ips = new HashSet<>();
            accountIps.put(account, ips);
        }
        ips.add(ip);
        return true;
    }
    int countOf(String account) {
        return loginCount.getOrDefault(account, 0);
    }
    int distinctIpOf(String account) {
        Set<String> ips = accountIps.get(account);
        return ips == null ? 0 : ips.size();
    }
    void countReport() {
        System.out.println("每個帳號登入次數");
        List<String> accounts = new ArrayList<>(loginCount.keySet());
        Collections.sort(accounts);
        for (String account : accounts) {
            System.out.println(account + "=" + loginCount.get(account) + "次");
        }
    }
    void abnormalReport(int limit) {
        System.out.println("異常重複登入（超過" + limit + "次或IP超過2個）");
        List<String> accounts = new ArrayList<>(loginCount.keySet());
        Collections.sort(accounts);
        for (String account : accounts) {
            int count = loginCount.get(account);
            int ipCount = accountIps.get(account).size();
            if (count > limit || ipCount > 2) {
                System.out.println(account + " 登入" + count + "次，IP" + ipCount + "個");
            }
        }
    }
    public static void main(String[] args) {
        LoginActivityReport report = new LoginActivityReport();
        report.record("eunice", "192.168.0.1");
        report.record("eunice", "192.168.0.1");
        report.record("bsy", "10.0.0.5");
        report.record("candice", "172.16.0.9");
        report.record("candice", "172.16.0.10");
        report.record("candice", "172.16.0.11");
        report.record("candice", "172.16.0.11");
        report.record("doris", "10.0.0.7");
        report.record("doris", "10.0.0.7");
        report.record("doris", "10.0.0.7");
        report.record("doris", "10.0.0.7");
        System.out.println("null帳號 record=" + report.record(null, "1.1.1.1"));
        System.out.println("空白IP record=" + report.record("eunice", "  "));
        report.countReport();
        System.out.println("candice 不同IP數=" + report.distinctIpOf("candice"));
        System.out.println("bsy 不同IP數=" + report.distinctIpOf("bsy"));
        System.out.println("查無帳號 次數=" + report.countOf("nobody"));
        System.out.println("查無帳號 IP數=" + report.distinctIpOf("nobody"));
        System.out.println("空報表測試");
        new LoginActivityReport().countReport();
        report.abnormalReport(3);
    }
}
