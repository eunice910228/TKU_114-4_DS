class Instructor {
    private String id;
    private String name;
    Instructor(String id, String name) {
        this.id = (id == null || id.isBlank()) ? "UNKNOWN" : id.trim();
        this.name = (name == null || name.isBlank()) ? "Unknown" : name.trim();
    }
    String label() {
        return id + " " + name;
    }
    String getName() {
        return name;
    }
}
class Course {
    private String courseCode;
    private String title;
    private Instructor instructor;
    Course(String courseCode, String title, Instructor instructor) {
        this.courseCode = (courseCode == null || courseCode.isBlank()) ? "UNKNOWN" : courseCode.trim();
        this.title = (title == null || title.isBlank()) ? "Untitled" : title.trim();
        this.instructor = instructor;
    }
    Instructor getInstructor() {
        return instructor;
    }
    String summary() {
        String who = (instructor == null) ? "未指派" : instructor.label();
        return courseCode + " " + title + " 授課者=" + who;
    }
}
public class CourseComposition {
    public static void main(String[] args) {
        Instructor lin = new Instructor("T001", "林老師");
        Instructor wang = new Instructor("T002", "王老師");
        Course java = new Course("A101", "Java入門", lin);
        Course ds = new Course("A201", "資料結構", lin);
        Course math = new Course("A210", "微積分", wang);
        Course project = new Course("A301", "專題", null);
        System.out.println(java.summary());
        System.out.println(ds.summary());
        System.out.println(math.summary());
        System.out.println(project.summary());
        System.out.println("兩門課共用同一位授課者物件=" + (java.getInstructor() == ds.getInstructor()));
        System.out.println("不同授課者=" + (java.getInstructor() == math.getInstructor()));
    }
}
