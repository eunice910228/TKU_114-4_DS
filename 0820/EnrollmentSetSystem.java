import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

class Enrollment {
    private final String studentId;
    private final String courseCode;
    private final String studentName;

    Enrollment(String studentId, String courseCode, String studentName) {
        this.studentId = studentId;
        this.courseCode = courseCode;
        this.studentName = studentName;
    }
    String getStudentId() {
        return studentId;
    }

    String getCourseCode() {
        return courseCode;
    }
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Enrollment enrollment)) {
            return false;
        }
        return Objects.equals(studentId, enrollment.studentId)
                && Objects.equals(courseCode, enrollment.courseCode);
    }
    @Override
    public int hashCode() {
        return Objects.hash(studentId, courseCode);
    }
    @Override
    public String toString() {
        return studentId + "-" + courseCode + " " + studentName;
    }
}

public class EnrollmentSetSystem {
    public static void main(String[] args) {
        Set<Enrollment> enrollments = new HashSet<>();

        System.out.println("新增報名");
        System.out.println("  A01 B101 = "
                + enrollments.add(new Enrollment("A01", "B101", "Eunice")));
        System.out.println("  A01 B102 = "
                + enrollments.add(new Enrollment("A01", "B102", "Eunice")));
        System.out.println("  A02 B101 = "
                + enrollments.add(new Enrollment("A02", "B101", "Bsy")));
        System.out.println("重複加入課程測試");
        System.out.println("  A01 B101 再加一次 = "
                + enrollments.add(new Enrollment("A01", "B101", "Eunice")));
        System.out.println("  名字換掉再測試一次 = "
                + enrollments.add(new Enrollment("A01", "B101", "幼妮")));
        System.out.println("  size = " + enrollments.size());
        Enrollment probe = new Enrollment("A01", "B101", "test");
        System.out.println("ID相同name不同");
        System.out.println("  probe = " + probe);
        System.out.println("  contains(probe) = " + enrollments.contains(probe));
        System.out.println("  remove(probe) = " + enrollments.remove(probe));
        System.out.println("  移除後 contains(probe) = " + enrollments.contains(probe));
        System.out.println("  size = " + enrollments.size());
        System.out.println("取消未定義的user與課程");
        System.out.println("  remove(A29 B399) = "
                + enrollments.remove(new Enrollment("A29", "B399", "不存在")));
        System.out.println("目前內容");
        for (Enrollment enrollment : enrollments) {
            System.out.println("  " + enrollment);
        }
        Enrollment a = new Enrollment("A03", "B103", "Candice");
        Enrollment b = new Enrollment("A03", "B103", "Candice");
        System.out.println("equals 與 hashCode");
        System.out.println("  a.equals(b) = " + a.equals(b));
        System.out.println("  hashCode 相同 = " + (a.hashCode() == b.hashCode()));
        System.out.println("  a.equals(null) = " + a.equals(null));
        System.out.println("  a.equals(string) = " + a.equals("A03-B103"));
    }
}
