import java.util.List;

public class Q03_EmployeePayroll {
    public static abstract class Employee {
        private final String id;
        private final String name;
        protected Employee(String id, String name) {
            if (id == null || id.isBlank()) {
                throw new IllegalArgumentException("id 不可為空");
            }
            if (name == null || name.isBlank()) {
                throw new IllegalArgumentException("name 不可為空");
            }
            this.id = id;
            this.name = name;
        }
        public String getId() {
            return id;
        }
        public String getName() {
            return name;
        }
        public abstract int monthlyPay();

        public String summary() {
            return id + "|" + name + "|" + monthlyPay();
        }
    }
    public static class SalariedEmployee extends Employee {
        private final int salary;

        public SalariedEmployee(String id, String name, int salary) {
            super(id, name);
            this.salary = Math.max(0, salary);
        }
        @Override
        public int monthlyPay() {
            return salary;
        }
    }
    public static class HourlyEmployee extends Employee {
        private final int hours;
        private final int hourlyRate;
        public HourlyEmployee(String id, String name, int hours, int hourlyRate) {
            super(id, name);
            this.hours = Math.max(0, hours);
            this.hourlyRate = Math.max(0, hourlyRate);
        }
        @Override
        public int monthlyPay() {
            if (hours <= 160) {
                return hours * hourlyRate;
            }
            int overtime = hours - 160;
            double pay = 160 * hourlyRate + overtime * hourlyRate * 1.5;
            return (int) pay;
        }
    }
    public static int totalPayroll(List<Employee> employees) {
        if (employees == null) {
            return 0;
        }
        int total = 0;
        for (Employee employee : employees) {
            if (employee != null) {
                total = total + employee.monthlyPay();
            }
        }
        return total;
    }
    public static void main(String[] args) {
        var employees = List.of(
                new SalariedEmployee("E1", "Amy", 50000),
                new HourlyEmployee("E2", "Bo", 170, 200)
        );
        System.out.println(employees.get(0).summary());
        System.out.println(employees.get(1).summary());
        System.out.println(totalPayroll(employees));
    }
}
