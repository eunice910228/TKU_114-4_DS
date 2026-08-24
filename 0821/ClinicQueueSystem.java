import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

class Patient {
    private String chartNo;
    private String name;
    private int age;
    Patient(String chartNo, String name, int age) {
        this.chartNo = chartNo;
        this.name = name;
        this.age = age;
    }
    String getChartNo() {
        return chartNo;
    }
    @Override
    public String toString() {
        return chartNo + " " + name + " " + age + "歲";
    }
}

class ClinicQueue {
    private Deque<Patient> waiting = new ArrayDeque<>();
    private Map<String, Patient> index = new HashMap<>();
    private List<Patient> completed = new ArrayList<>();
    boolean register(Patient patient) {
        if (patient == null || index.containsKey(patient.getChartNo())) {
            return false;
        }
        waiting.offerLast(patient);
        index.put(patient.getChartNo(), patient);
        return true;
    }
    boolean cancel(String chartNo) {
        if (!index.containsKey(chartNo)) {
            return false;
        }
        Iterator<Patient> iterator = waiting.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getChartNo().equals(chartNo)) {
                iterator.remove();
                break;
            }
        }
        index.remove(chartNo);
        return true;
    }
    Patient peekNext() {
        return waiting.peekFirst();
    }
    Patient callNext() {
        Patient patient = waiting.pollFirst();
        if (patient == null) {
            return null;
        }
        index.remove(patient.getChartNo());
        completed.add(patient);
        return patient;
    }
    int positionOf(String chartNo) {
        int position = 1;
        for (Patient patient : waiting) {
            if (patient.getChartNo().equals(chartNo)) {
                return position;
            }
            position++;
        }
        return -1;
    }
    int waitingCount() {
        return waiting.size();
    }
    boolean isEmpty() {
        return waiting.isEmpty();
    }
    List<Patient> getCompleted() {
        return completed;
    }
    void printWaiting() {
        System.out.println("  等候中 " + waiting.size() + " 位 = " + waiting);
    }
}

public class ClinicQueueSystem {
    public static void main(String[] args) {
        ClinicQueue clinic = new ClinicQueue();
        System.out.println("空診所");
        System.out.println("  peekNext() = " + clinic.peekNext());
        System.out.println("  callNext() = " + clinic.callNext());
        System.out.println("  cancel(E001) = " + clinic.cancel("E001"));
        System.out.println();
        System.out.println("掛號");
        System.out.println("  E001 = " + clinic.register(new Patient("E001", "Eunice", 24)));
        System.out.println("  E002 = " + clinic.register(new Patient("E002", "Bsy", 28)));
        System.out.println("  E003 = " + clinic.register(new Patient("E003", "Candice", 21)));
        System.out.println("  E004 = " + clinic.register(new Patient("E004", "Vivian", 45)));
        System.out.println("  E005 = " + clinic.register(new Patient("E005", "James", 42)));
        clinic.printWaiting();
        System.out.println();
        System.out.println("重複掛號與 null");
        System.out.println("  再掛 E002 = " + clinic.register(new Patient("E002", "Bsy", 28)));
        System.out.println("  掛號 null = " + clinic.register(null));
        System.out.println();
        System.out.println("查詢等候位置");
        System.out.println("  E001 = 第 " + clinic.positionOf("E001") + " 位");
        System.out.println("  E004 = 第 " + clinic.positionOf("E004") + " 位");
        System.out.println("  E022 = " + clinic.positionOf("E022") + " 表示不在等候中");
        System.out.println();
        System.out.println("叫號，先掛先看");
        System.out.println("  peekNext() = " + clinic.peekNext() + " 只看不叫");
        System.out.println("  callNext() = " + clinic.callNext());
        clinic.printWaiting();
        System.out.println("  callNext() = " + clinic.callNext());
        clinic.printWaiting();
        System.out.println();
        System.out.println("取消中間的病歷號");
        System.out.println("  取消 E004 = " + clinic.cancel("E004"));
        clinic.printWaiting();
        System.out.println();
        System.out.println("取消不存在或已看診的病歷號");
        System.out.println("  取消 E022 = " + clinic.cancel("E022"));
        System.out.println("  取消 E001 = " + clinic.cancel("E001"));
        System.out.println("  取消 E004 = " + clinic.cancel("E004"));
        System.out.println();
        System.out.println("取消後補掛，排在最後");
        System.out.println("  E006 = " + clinic.register(new Patient("E006", "Ailsa", 16)));
        clinic.printWaiting();
        System.out.println();
        System.out.println("連續叫號到空");
        while (!clinic.isEmpty()) {
            System.out.println("  callNext() = " + clinic.callNext());
        }
        System.out.println("  callNext() = " + clinic.callNext());
        System.out.println();
        System.out.println("當日完成清單");
        List<Patient> done = clinic.getCompleted();
        for (int i = 0; i < done.size(); i++) {
            System.out.println("  " + (i + 1) + ". " + done.get(i));
        }
        System.out.println("  共 " + done.size() + " 人次");
        System.out.println("  E004 被取消，不在完成清單");
    }
}
