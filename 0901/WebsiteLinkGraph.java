import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class WebsiteLinkGraph {
    private final Map<String, List<String>> links = new HashMap<>();
    boolean addPage(String page) {
        if (page == null || page.isBlank()) {
            return false;
        }
        if (links.containsKey(page)) {
            return false;
        }
        links.put(page, new ArrayList<>());
        return true;
    }
    boolean addLink(String from, String to) {
        if (!links.containsKey(from) || !links.containsKey(to)) {
            return false;
        }
        List<String> targets = links.get(from);
        if (targets.contains(to)) {
            return false;
        }
        targets.add(to);
        return true;
    }
    List<String> outgoing(String page) {
        List<String> result = new ArrayList<>();
        if (links.containsKey(page)) {
            result.addAll(links.get(page));
            Collections.sort(result);
        }
        return result;
    }
    int incomingCount(String page) {
        int count = 0;
        for (List<String> targets : links.values()) {
            if (targets.contains(page)) {
                count++;
            }
        }
        return count;
    }
    void noIncomingReport() {
        System.out.println("沒有被連入的頁面");
        List<String> pages = new ArrayList<>(links.keySet());
        Collections.sort(pages);
        for (String page : pages) {
            if (incomingCount(page) == 0) {
                System.out.println(page);
            }
        }
    }
    void noOutgoingReport() {
        System.out.println("沒有連出的頁面");
        List<String> pages = new ArrayList<>(links.keySet());
        Collections.sort(pages);
        for (String page : pages) {
            if (links.get(page).isEmpty()) {
                System.out.println(page);
            }
        }
    }
    public static void main(String[] args) {
        WebsiteLinkGraph web = new WebsiteLinkGraph();
        System.out.println("addPage home=" + web.addPage("home"));
        System.out.println("addPage about=" + web.addPage("about"));
        System.out.println("addPage news=" + web.addPage("news"));
        System.out.println("addPage contact=" + web.addPage("contact"));
        System.out.println("重複 addPage home=" + web.addPage("home"));
        System.out.println("home->about=" + web.addLink("home", "about"));
        System.out.println("home->news=" + web.addLink("home", "news"));
        System.out.println("about->contact=" + web.addLink("about", "contact"));
        System.out.println("news->contact=" + web.addLink("news", "contact"));
        System.out.println("重複 home->about=" + web.addLink("home", "about"));
        System.out.println("不存在頁面 home->shop=" + web.addLink("home", "shop"));
        System.out.println("null頁面 addPage=" + web.addPage(null));
        System.out.println("空白頁面 addPage=" + web.addPage("  "));
        System.out.println("空graph的outgoing=" + new WebsiteLinkGraph().outgoing("home"));
        System.out.println("空graph的incoming=" + new WebsiteLinkGraph().incomingCount("home"));
        System.out.println("home的outgoing=" + web.outgoing("home"));
        System.out.println("contact的outgoing=" + web.outgoing("contact"));
        System.out.println("shop的outgoing=" + web.outgoing("shop"));
        System.out.println("contact的incoming數=" + web.incomingCount("contact"));
        System.out.println("home的incoming數=" + web.incomingCount("home"));
        web.noIncomingReport();
        web.noOutgoingReport();
    }
}
