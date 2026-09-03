import java.util.ArrayList;
import java.util.List;
public class WildcardNumberTools {
    static double average(List<? extends Number> values) {
        if (values == null || values.isEmpty()) {
            return 0.0;
        }
        double sum = 0.0;
        int count = 0;
        for (Number n : values) {
            if (n == null) {
                continue;
            }
            sum += n.doubleValue();
            count++;
        }
        return count == 0 ? 0.0 : sum / count;
    }
    static double maximum(List<? extends Number> values) {
        if (values == null || values.isEmpty()) {
            return Double.NaN;
        }
        double max = Double.NEGATIVE_INFINITY;
        boolean found = false;
        for (Number n : values) {
            if (n == null) {
                continue;
            }
            max = Math.max(max, n.doubleValue());
            found = true;
        }
        return found ? max : Double.NaN;
    }
    static void addRange(List<? super Integer> target, int start, int end) {
        if (target == null) {
            return;
        }
        if (start > end) {
            return;
        }
        for (int i = start; i <= end; i++) {
            target.add(i);
        }
    }
    public static void main(String[] args) {
        List<Integer> ints = new ArrayList<>(List.of(3, 9, 1, 7, 5));
        List<Double> doubles = new ArrayList<>(List.of(2.5, 8.75, 4.0));
        List<Long> longs = new ArrayList<>(List.of(100L, 250L));
        System.out.println("Integer " + ints + " avg=" + average(ints) + " max=" + maximum(ints));
        System.out.println("Double " + doubles + " avg=" + average(doubles) + " max=" + maximum(doubles));
        System.out.println("Long " + longs + " avg=" + average(longs) + " max=" + maximum(longs));
        List<Integer> intTarget = new ArrayList<>();
        List<Number> numberTarget = new ArrayList<>();
        List<Object> objectTarget = new ArrayList<>();
        addRange(intTarget, 1, 5);
        addRange(numberTarget, 10, 13);
        addRange(objectTarget, 100, 102);
        System.out.println("addRange到List<Integer>=" + intTarget);
        System.out.println("addRange到List<Number>=" + numberTarget);
        System.out.println("addRange到List<Object>=" + objectTarget);
        List<Integer> empty = new ArrayList<>();
        System.out.println("空list average=" + average(empty));
        System.out.println("空list maximum=" + maximum(empty));
        double m = maximum(empty);
        System.out.println("m==Double.NaN=" + (m == Double.NaN));
        System.out.println("Double.isNaN(m)=" + Double.isNaN(m));
        List<Integer> noChange = new ArrayList<>(List.of(99));
        addRange(noChange, 10, 3);
        System.out.println("start>end後=" + noChange);
        addRange(noChange, 5, 5);
        System.out.println("start==end後=" + noChange);
        System.out.println("average(null)=" + average(null));
        System.out.println("maximum(null)=" + maximum(null));
        addRange(null, 1, 3);
        List<Integer> withNull = new ArrayList<>();
        withNull.add(5);
        withNull.add(null);
        withNull.add(15);
        System.out.println("含null " + withNull + " avg=" + average(withNull) + " max=" + maximum(withNull));
        List<Integer> allNull = new ArrayList<>();
        allNull.add(null);
        System.out.println("全null " + allNull + " avg=" + average(allNull) + " max=" + maximum(allNull));
    }
}
