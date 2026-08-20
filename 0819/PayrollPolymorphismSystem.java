abstract class Employee {
    private final String id;
    private final String name;
    private final String department;
    Employee(String id, String name, String department) {
        this.id = (id == null || id.isBlank()) ? "UNKNOWN" : id;
        this.name = (name == null || name.isBlank()) ? "無名氏" : name;
        this.department = (department == null || department.isBlank()) ? "未分派" : department;
    }
    String getId()         { return id; }
    String getName()       { return name; }
    String getDepartment() { return department; }
    abstract int calculatePay();
    abstract String payType();
    @Override
    public String toString() {
        return String.format("%-6s %-9s %-8s %-8s %8d",
                id, name, department, payType(), calculatePay());
    }
}

    // 月薪制：固定月薪 + 全勤獎金。 
class MonthlySalaryEmployee extends Employee {
    private final int monthlySalary;
    private final boolean fullAttendance;
    MonthlySalaryEmployee(String id, String name, String department,
                          int monthlySalary, boolean fullAttendance) {
        super(id, name, department);
        this.monthlySalary = Math.max(0, monthlySalary);
        this.fullAttendance = fullAttendance;
    }
    @Override
    int calculatePay() {
        return monthlySalary + (fullAttendance ? 2000 : 0);
    }
    @Override
    String payType() { return "月薪"; }
}

    // 時薪制：時薪 × 時數，超過 160 小時的部分加給 1.34 倍。 
class HourlyEmployee extends Employee {
    private static final int NORMAL_HOURS = 160;
    private final int hourlyRate;
    private final int hours;
    HourlyEmployee(String id, String name, String department, int hourlyRate, int hours) {
        super(id, name, department);
        this.hourlyRate = Math.max(0, hourlyRate);
        this.hours = Math.max(0, hours);
    }
    @Override
    int calculatePay() {
        if (hours <= NORMAL_HOURS) {
            return hourlyRate * hours;
        }
        int overtime = hours - NORMAL_HOURS;
        return hourlyRate * NORMAL_HOURS + (int) (hourlyRate * overtime * 1.34);
    }
    @Override
    String payType() { return "時薪"; }
}

    // 業務：底薪 + 業績抽成，超過門檻的部分抽成加碼。
class SalesEmployee extends Employee {
    private static final int BONUS_THRESHOLD = 500_000;
    private final int baseSalary;
    private final int salesAmount;
    private final int commissionPercent;
    SalesEmployee(String id, String name, String department,
                  int baseSalary, int salesAmount, int commissionPercent) {
        super(id, name, department);
        this.baseSalary = Math.max(0, baseSalary);
        this.salesAmount = Math.max(0, salesAmount);
        this.commissionPercent = Math.max(0, Math.min(100, commissionPercent));
    }
    @Override
    int calculatePay() {
        int commission = salesAmount * commissionPercent / 100;
        if (salesAmount > BONUS_THRESHOLD) {
            int extra = salesAmount - BONUS_THRESHOLD;
            commission = commission + extra * 2 / 100;  
        }
        return baseSalary + commission;
    }
    @Override
    String payType() { return "業務"; }
}

public class PayrollPolymorphismSystem {
    static int totalPay(Employee[] employees) {
        if (employees == null) {
            return 0;
        }
        int sum = 0;
        for (Employee e : employees) {
            if (e != null) {
                sum = sum + e.calculatePay();
            }
        }
        return sum;
    }
    static Employee highestPaid(Employee[] employees) {
        if (employees == null) {
            return null;
        }
        Employee best = null;
        for (Employee e : employees) {
            if (e == null) {
                continue;
            }
            if (best == null || e.calculatePay() > best.calculatePay()) {
                best = e;
            }
        }
        return best;
    }
    static double averagePay(Employee[] employees) {
        if (employees == null) {
            return 0.0;
        }
        int count = 0;
        for (Employee e : employees) {
            if (e != null) {
                count++;
            }
        }
        return count == 0 ? 0.0 : (double) totalPay(employees) / count;
    }
    static void printReport(String label, Employee[] payroll) {
        System.out.println("=== " + label + " ===");
        System.out.printf("%-6s %-9s %-8s %-8s %8s%n", "編號", "姓名", "部門", "類型", "薪資");
        if (payroll != null) {
            for (Employee e : payroll) {
                if (e != null) {
                    System.out.println(e);
                }
            }
        }
        System.out.println("薪資總額：" + totalPay(payroll));
        System.out.printf("平均薪資：%.1f%n", averagePay(payroll));
        Employee top = highestPaid(payroll);
        if (top == null) {
            System.out.println("最高薪資：無資料");
        } else {
            System.out.println("最高薪資：" + top.getName() + " " + top.calculatePay()
                    + "（" + top.payType() + "）");
        }
        System.out.println();
    }
    public static void main(String[] args) {
        Employee[] payroll = {
            new MonthlySalaryEmployee("E101", "Eunice",   "研發", 61000, true),
            new MonthlySalaryEmployee("E102", "Bsy",   "研發", 54000, false),
            new HourlyEmployee      ("E103", "Candice", "客服", 210, 176),
            new HourlyEmployee      ("E104", "Julie",   "客服", 300, 120),
            new SalesEmployee       ("E105", "Jennifer",  "業務", 29000, 820_000, 5),
            new SalesEmployee       ("E106", "Vivian", "業務", 29000, 310_000, 5)
        };
        printReport("薪資明細", payroll);
		
        System.out.println("=== 邊界測試 ===");
        printReport("含 null 員工與空白欄位", new Employee[]{
            null,
            new MonthlySalaryEmployee(null, "  ", null, -5000, true),
            new HourlyEmployee("E199", "Test", "測試", -100, -20)
        });
        printReport("空陣列", new Employee[0]);
        printReport("陣列為 null", null);
    }
}