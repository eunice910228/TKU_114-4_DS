import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedHashMap;
import java.util.Map;

class Delivery {
    private String id;
    private String destination;
    private double weightKg;
    private boolean done;
    Delivery(String id, String destination, double weightKg) {
        this.id = id;
        this.destination = destination;
        this.weightKg = weightKg;
    }
    String getId() {
        return id;
    }
    double getWeightKg() {
        return weightKg;
    }
    void complete() {
        done = true;
    }
    void reopen() {
        done = false;
    }
    @Override
    public String toString() {
        return id + " " + destination + " " + weightKg + "kg done=" + done;
    }
}

class DeliveryWorkflow {
    private Map<String, Delivery> index = new LinkedHashMap<>();
    private Deque<Delivery> waiting = new ArrayDeque<>();
    private Deque<Delivery> completed = new ArrayDeque<>();
    boolean add(Delivery delivery) {
        if (delivery == null || index.containsKey(delivery.getId())) {
            return false;
        }
        index.put(delivery.getId(), delivery);
        waiting.offerLast(delivery);
        return true;
    }
    Delivery processNext() {
        Delivery delivery = waiting.pollFirst();
        if (delivery == null) {
            return null;
        }
        delivery.complete();
        completed.push(delivery);
        return delivery;
    }
    Delivery undo() {
        Delivery delivery = completed.pollFirst();
        if (delivery == null) {
            return null;
        }
        delivery.reopen();
        waiting.offerFirst(delivery);
        return delivery;
    }
    Delivery findById(String id) {
        return index.get(id);
    }
    int waitingCount() {
        return waiting.size();
    }
    double waitingWeight() {
        double sum = 0;
        for (Delivery delivery : waiting) {
            sum = sum + delivery.getWeightKg();
        }
        return Math.round(sum * 10) / 10.0;
    }
    void printSummary() {
        System.out.println("  Queue 等候 = " + waiting);
        System.out.println("  Stack 完成 = " + completed);
        System.out.println("  Map keys = " + index.keySet());
        System.out.println("  總 " + index.size() + " 筆，等候 " + waiting.size()
                + " 筆 " + waitingWeight() + "kg，完成 " + completed.size() + " 筆");
    }
}

public class DeliveryWorkflowSystem {
    public static void main(String[] args) {
        DeliveryWorkflow workflow = new DeliveryWorkflow();
        System.out.println("初始系統");
        System.out.println("  processNext() = " + workflow.processNext());
        System.out.println("  undo() = " + workflow.undo());
        System.out.println("  findById(D001) = " + workflow.findById("D001"));
        System.out.println("新增配送單");
        System.out.println("  D001 = " + workflow.add(new Delivery("D001", "新店", 4.7)));
        System.out.println("  D002 = " + workflow.add(new Delivery("D002", "南港", 12.0)));
        System.out.println("  D003 = " + workflow.add(new Delivery("D003", "中和", 1.2)));
        System.out.println("  D004 = " + workflow.add(new Delivery("D004", "台中", 13.5)));
        System.out.println("  D005 = " + workflow.add(new Delivery("D005", "高雄", 7.3)));
        System.out.println("重複 id 與 null");
        System.out.println("  新增 D002 = " + workflow.add(new Delivery("D002", "重複", 1.0)));
        System.out.println("  新增 null = " + workflow.add(null));
        workflow.printSummary();
        System.out.println("依 id 查詢");
        System.out.println("  findById(D003) = " + workflow.findById("D003"));
        System.out.println("  findById(D999) = " + workflow.findById("D999"));
        System.out.println("處理配送，先進先出");
        System.out.println("  processNext() = " + workflow.processNext());
        System.out.println("  processNext() = " + workflow.processNext());
        System.out.println("  processNext() = " + workflow.processNext());
        workflow.printSummary();
        System.out.println("undo");
        System.out.println("  undo() = " + workflow.undo());
        workflow.printSummary();
        System.out.println("連續 undo");
        System.out.println("  undo() = " + workflow.undo());
        System.out.println("  undo() = " + workflow.undo());
        workflow.printSummary();
        System.out.println("undo 到空");
        System.out.println("  undo() = " + workflow.undo());
        System.out.println("連續處理到空");
        while (workflow.waitingCount() > 0) {
            System.out.println("  processNext() = " + workflow.processNext());
        }
        System.out.println("  processNext() = " + workflow.processNext());
        workflow.printSummary();
        System.out.println("已完成單據");
        System.out.println("  findById(D001) = " + workflow.findById("D001"));
    }
}