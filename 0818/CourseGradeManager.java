class CourseGrade {
    private final String studentId;
    private final String name;
    private final int usual;
    private final int midterm;
    private final int finalExam;
    private final int attendance;

    CourseGrade(String studentId, String name,
                int usual, int midterm, int finalExam, int attendance) {
        this.studentId = (studentId == null || studentId.isBlank())
                ? "UNKNOWN" : studentId.trim();
        this.name = (name == null || name.isBlank()) ? "Unknown" : name.trim();
        this.usual = clamp(usual);
        this.midterm = clamp(midterm);
        this.finalExam = clamp(finalExam);
        this.attendance = clamp(attendance);
    }
    private static int clamp(int score) {
        if (score < 0) return 0;
        if (score > 100) return 100;
        return score;
    }
    double calculateFinalScore() {
        return usual * 0.5 + midterm * 0.2 + finalExam * 0.2 + attendance * 0.1;
    }
    String getLevel() {
        double score = calculateFinalScore();
        if (score >= 90) return "A";
        if (score >= 80) return "B";
        if (score >= 70) return "C";
        if (score >= 60) return "D";
        return "F";
    }
    boolean passed() {
        return calculateFinalScore() >= 60;
    }
    String getName() {
        return name;
    }
    @Override
    public String toString() {
        return String.format("%s %-5s 平時=%3d 期中=%3d 期末=%3d 出席=%3d 總分=%5.1f 成績=%s",
                studentId, name, usual, midterm, finalExam, attendance,
                calculateFinalScore(), getLevel());
    }
}

public class CourseGradeManager {
    public static void main(String[] args) {
        CourseGrade[] grades = {
            new CourseGrade("A0001", "Eunice", 99, 87, 89, 95),
            new CourseGrade("A0002", "Phoebe", 73, 90, 82, 90),
            new CourseGrade("A0003", "Julie", 46, 53, 41, 62),
            new CourseGrade("A0004", "Jennifer", 120, -10, 75, 95)     // 測試越界值修正
        };
        System.out.println("=== 成績明細 ===");
        for (CourseGrade grade : grades) {
            System.out.println(grade);
        }
        double total = 0;
        CourseGrade highest = grades[0];
        for (CourseGrade grade : grades) {
            total += grade.calculateFinalScore();
            if (grade.calculateFinalScore() > highest.calculateFinalScore()) {
                highest = grade;
            }
        }

        System.out.println("=== 統計 ===");
        System.out.printf("全班平均：%.2f%n", total / grades.length);
        System.out.println("最高分：" + highest);

        System.out.println("=== 不及格名單 ===");
        boolean anyFailed = false;
        for (CourseGrade grade : grades) {
            if (!grade.passed()) {
                System.out.printf("%s（%.1f 分，%s）%n",
                        grade.getName(), grade.calculateFinalScore(), grade.getLevel());
                anyFailed = true;
            }
        }
        if (!anyFailed) {
            System.out.println("（無）");
        }
    }
}
