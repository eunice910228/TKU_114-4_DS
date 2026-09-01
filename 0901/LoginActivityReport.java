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
    void record(String account, String ip) {
        loginCount.put(account, loginCount.getOrDefault(account, 0) + 1);
        Set<String> ips = accountIps.get(account);
        if (ips == null) {
            ips = new HashSet<>();
            accountIps.put(account, ips);
        }
        ips.add(ip);
    }
    int countOf(String account) {
        return loginCount.getOrDefault(account, 0);
    }
    void countReport() {
        System.out.println("每個帳號登入次數");
        List<String> accounts = new ArrayList<>(loginCount.keySet());
        Collections.sort(accounts);
        for (String account : accounts) {
            System.out.println(account + "=" + loginCount.get(account) + "次");
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
        report.countReport();
        System.out.println("查無帳號 次數=" + report.countOf("nobody"));
    }
}
