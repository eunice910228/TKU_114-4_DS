import java.util.Objects;
class LibraryMember {
    private final String memberId;
    private String name;
    private String email;
    LibraryMember(String memberId, String name, String email) {
        this.memberId = (memberId == null || memberId.isBlank()) ? "UNKNOWN" : memberId.trim();
        this.name = name;
        this.email = email;
    }
    @Override
    public String toString() {
        return memberId + " " + name + " " + email;
    }
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof LibraryMember member)) {
            return false;
        }
        return Objects.equals(memberId, member.memberId);
    }
    @Override
    public int hashCode() {
        return Objects.hash(memberId);
    }
}
public class MemberEqualityPractice {
    public static void main(String[] args) {
        LibraryMember a = new LibraryMember("M001", "Eunice", "eunice@old.com");
        LibraryMember b = new LibraryMember("M001", "Eunice Liu", "eunice@new.com");
        LibraryMember c = a;
        LibraryMember d = new LibraryMember("M002", "Bsy", "bsy@mail.com");
        System.out.println(a);
        System.out.println(b);
        System.out.println("a==b=" + (a == b));
        System.out.println("a.equals(b)=" + a.equals(b));
        System.out.println("a==c=" + (a == c));
        System.out.println("a.equals(d)=" + a.equals(d));
        System.out.println("a.equals(null)=" + a.equals(null));
        System.out.println("equals為true時hashCode相同=" + (a.hashCode() == b.hashCode()));
    }
}
