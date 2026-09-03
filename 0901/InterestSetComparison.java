import java.util.Set;
import java.util.TreeSet;
public class InterestSetComparison {
    static Set<String> union(Set<String> a, Set<String> b) {
        Set<String> result = new TreeSet<>(a);
        result.addAll(b);
        return result;
    }
    static Set<String> intersection(Set<String> a, Set<String> b) {
        Set<String> result = new TreeSet<>(a);
        result.retainAll(b);
        return result;
    }
    static Set<String> onlyFirst(Set<String> a, Set<String> b) {
        Set<String> result = new TreeSet<>(a);
        result.removeAll(b);
        return result;
    }
    public static void main(String[] args) {
        Set<String> eunice = new TreeSet<>(Set.of("咖啡", "唱歌", "閱讀", "數獨"));
        Set<String> bsy = new TreeSet<>(Set.of("唱歌", "健身", "數獨", "空翻"));
        System.out.println("Eunice=" + eunice);
        System.out.println("Bsy=" + bsy);
        System.out.println("union=" + union(eunice, bsy));
        System.out.println("intersection=" + intersection(eunice, bsy));
        System.out.println("只有Eunice=" + onlyFirst(eunice, bsy));
        System.out.println("只有Bsy=" + onlyFirst(bsy, eunice));
        System.out.println("輸入未被修改=" + (eunice.size() == 4 && bsy.size() == 4));
        Set<String> empty = new TreeSet<>();
        System.out.println("與空集合union=" + union(eunice, empty) + " intersection=" + intersection(eunice, empty));
    }
}