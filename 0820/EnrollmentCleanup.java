import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class EnrollmentCleanup {

    static String normalize(String name) {
        return name.trim().toLowerCase();
    }
    public static void main(String[] args) {
        List<String> rosters = new ArrayList<>();
        rosters.add("Eunice");
        rosters.add("Bsy");
        rosters.add(null);
        rosters.add("Eunice");
        rosters.add("   ");
        rosters.add("candice");
        rosters.add("Bsy ");
        rosters.add("");
        rosters.add("Doris");
        rosters.add("Eunice");
        System.out.println("清理前 = " + rosters);
        System.out.println("size = " + rosters.size());
        int removed = 0;
        Iterator<String> iterator = rosters.iterator();
        while (iterator.hasNext()) {
            String name = iterator.next();
            if (name == null || name.isBlank()) {
                iterator.remove();
                removed++;
            }
        }
        System.out.println("用 Iterator.remove() 移除 " + removed + " 筆 null 或空白");
        System.out.println("移除後 = " + rosters);
        Set<String> seen = new HashSet<>();
        List<String> duplicated = new ArrayList<>();
        for (String name : rosters) {
            String key = normalize(name);
            if (!seen.add(key) && !duplicated.contains(key)) {
                duplicated.add(key);
            }
        }
        System.out.println("重複報告");
        System.out.println("  不重複姓名 = " + seen.size() + " 人");
        System.out.println("  重複姓名 = " + duplicated);
        for (String dup : duplicated) {
            List<String> originals = new ArrayList<>();
            for (String name : rosters) {
                if (normalize(name).equals(dup)) {
                    originals.add(name);
                }
            }
            System.out.println("  " + dup + " 出現 " + originals.size() + " 次 " + originals);
        }
        Set<String> kept = new HashSet<>();
        Iterator<String> second = rosters.iterator();
        while (second.hasNext()) {
            String name = second.next();
            if (!kept.add(normalize(name))) {
                second.remove();
            }
        }
        System.out.println("清理後 = " + rosters);
        System.out.println("size = " + rosters.size());
        System.out.println("Set.size = " + kept.size());
        List<String> allBad = new ArrayList<>();
        allBad.add(null);
        allBad.add("");
        allBad.add("   ");
        Iterator<String> third = allBad.iterator();
        while (third.hasNext()) {
            String name = third.next();
            if (name == null || name.isBlank()) {
                third.remove();
            }
        }
        System.out.println("全部不合法的清單清理後 = " + allBad);
        List<String> emptyList = new ArrayList<>();
        Iterator<String> fourth = emptyList.iterator();
        System.out.println("空 list 的 hasNext() = " + fourth.hasNext());
    }
}
