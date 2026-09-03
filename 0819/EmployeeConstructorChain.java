abstract class EmployeeBase {
    private final String id;
    private final String name;
    EmployeeBase(String id, String name) {
        System.out.println("constructor EmployeeBase");
        this.id = (id == null || id.isBlank()) ? "UNKNOWN" : id;
        this.name = (name == null || name.isBlank()) ? "無名氏" : name;
    }
    String getId() {
        return id;
    }
    String getName() {
        return name;
    }
    abstract int calculatePay();
    String describe() {
        return id + " " + name + " 薪資=" + calculatePay();
    }
}
class FullTimeEmployee extends EmployeeBase {
    private final int monthlySalary;
    FullTimeEmployee(String id, String name, int monthlySalary) {
        super(id, name);
        System.out.println("constructor FullTimeEmployee");
        this.monthlySalary = Math.max(0, monthlySalary);
    }
    @Override
    int calculatePay() {
        return monthlySalary;
    }
}
class PartTimeEmployee extends EmployeeBase {
    private final int hourlyRate;
    private final int hours;
    PartTimeEmployee(String id, String name, int hourlyRate, int hours) {
        super(id, name);
        System.out.println("constructor PartTimeEmployee");
        this.hourlyRate = Math.max(0, hourlyRate);
        this.hours = Math.max(0, hours);
    }
    @Override
    int calculatePay() {
        return hourlyRate * hours;
    }
}
public class EmployeeConstructorChain {
    public static void main(String[] args) {
        System.out.println("建立FullTimeEmployee");
        EmployeeBase eunice = new FullTimeEmployee("E101", "Eunice", 52000);
        System.out.println("建立PartTimeEmployee");
        EmployeeBase bsy = new PartTimeEmployee("E102", "Bsy", 220, 80);
        System.out.println("實際執行順序=super先跑父類別constructor，再跑子類別本身");
        EmployeeBase[] staff = { eunice, bsy };
        for (EmployeeBase e : staff) {
            System.out.println(e.describe());
        }
        System.out.println("邊界測試");
        EmployeeBase test1 = new FullTimeEmployee("E103", "Candice", -50000);
        EmployeeBase test2 = new PartTimeEmployee("E104", "Doris", -200, -10);
        EmployeeBase test3 = new FullTimeEmployee(null, "  ", 30000);
        for (EmployeeBase e : new EmployeeBase[]{ test1, test2, test3 }) {
            System.out.println(e.describe());
        }
    }
}
