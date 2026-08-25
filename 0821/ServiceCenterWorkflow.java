import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

class ServiceTicket {
    private String id;
    private String customer;
    private String issue;
    private String status;
    ServiceTicket(String id, String customer, String issue) {
        this.id = id;
        this.customer = customer;
        this.issue = issue;
        this.status = "WAITING";
    }
    String getId() {
        return id;
    }
    String getStatus() {
        return status;
    }
    void markDone() {
        status = "DONE";
    }
    void markWaiting() {
        status = "WAITING";
    }
    void markCancelled() {
        status = "CANCELLED";
    }
    @Override
    public String toString() {
        return id + " " + customer + " " + status;
    }
}

class ServiceCenter {
    private Map<String, ServiceTicket> index = new LinkedHashMap<>();
    private Deque<ServiceTicket> waiting = new ArrayDeque<>();
    private Deque<ServiceTicket> completed = new ArrayDeque<>();
    private Set<String> usedIds = new HashSet<>();
    boolean createTicket(String id, String customer, String issue) {
        if (!usedIds.add(id)) {
            return false;
        }
        ServiceTicket ticket = new ServiceTicket(id, customer, issue);
        index.put(id, ticket);
        waiting.offerLast(ticket);
        return true;
    }
    ServiceTicket processNext() {
        ServiceTicket ticket = waiting.pollFirst();
        if (ticket == null) {
            return null;
        }
        ticket.markDone();
        completed.push(ticket);
        return ticket;
    }
    boolean cancelWaiting(String id) {
        ServiceTicket ticket = index.get(id);
        if (ticket == null || !ticket.getStatus().equals("WAITING")) {
            return false;
        }
        Iterator<ServiceTicket> iterator = waiting.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getId().equals(id)) {
                iterator.remove();
                break;
            }
        }
        ticket.markCancelled();
        return true;
    }
    ServiceTicket undoLastCompletion() {
        ServiceTicket ticket = completed.pollFirst();
        if (ticket == null) {
            return null;
        }
        ticket.markWaiting();
        waiting.offerFirst(ticket);
        return ticket;
    }
    ServiceTicket findById(String id) {
        return index.get(id);
    }
    int waitingCount() {
        return waiting.size();
    }
    void printSummary() {
        System.out.println("Queue 等候 =" + waiting);
        System.out.println("Stack 完成 =" + completed);
        System.out.println("Set 用過的 id =" + usedIds.size() + "個");
        System.out.println("Map 全部狀態 =" + index.values());
        System.out.println("等候 " + waiting.size() + "，完成" + completed.size()
                + "，總計 " + index.size());
    }
}

public class ServiceCenterWorkflow {
    public static void main(String[] args) {
        ServiceCenter center = new ServiceCenter();
        System.out.println("空 Queue");
        System.out.println("processNext() =" + center.processNext());
        System.out.println("undoLastCompletion() =" + center.undoLastCompletion());
        System.out.println("cancelWaiting(A01) =" + center.cancelWaiting("A01"));
        System.out.println("findById(A01) =" + center.findById("A01"));
        System.out.println("建立 ticket");
        System.out.println("A01 =" + center.createTicket("A01", "Eunice", "網路異常"));
        System.out.println("A02 =" + center.createTicket("A02", "Bsy", "帳號鎖定"));
        System.out.println("A03 =" + center.createTicket("A03", "Cindy", "退款查詢"));
        System.out.println("A04 =" + center.createTicket("A04", "Dora", "設備報修"));
        System.out.println("A05 =" + center.createTicket("A05", "Ella", "帳單疑問"));
        center.printSummary();
        System.out.println("重複 id");
        System.out.println("再建 A02 =" + center.createTicket("A02", "別人", "別的問題"));
        System.out.println("處理，先進先出");
        System.out.println("processNext() =" + center.processNext());
        System.out.println("processNext() =" + center.processNext());
        center.printSummary();
        System.out.println("取消尚未處理的 ticket");
        System.out.println("cancelWaiting(A04) =" + center.cancelWaiting("A04"));
        center.printSummary();
        System.out.println("取消不存在或不可取消的 ticket");
        System.out.println("cancelWaiting(A77) 不存在 =" + center.cancelWaiting("A77"));
        System.out.println("cancelWaiting(A01) 已完成 =" + center.cancelWaiting("A01"));
        System.out.println("cancelWaiting(A04) 已取消 =" + center.cancelWaiting("A04"));
        System.out.println("連續兩次 undo");
        System.out.println("第1次 =" + center.undoLastCompletion());
        center.printSummary();
        System.out.println("第2次 =" + center.undoLastCompletion());
        center.printSummary();
        System.out.println("第3次 =" + center.undoLastCompletion());
        //System.out.println("findById，已取消或已完成的也查得到");
        System.out.println("findById(A01) =" + center.findById("A01"));
        System.out.println("findById(A04) =" + center.findById("A04"));
        System.out.println("findById(A77) =" + center.findById("A77"));
        //System.out.println("連續處理到空");
        while (center.waitingCount() > 0) {
            System.out.println("processNext() =" + center.processNext());
        }
        System.out.println("processNext() = " + center.processNext());
        center.printSummary();
        //System.out.println("取消後的 id 仍不可重用");
        System.out.println("再建 A04 =" + center.createTicket("A04", "新客人", "新問題"));
    }
}
