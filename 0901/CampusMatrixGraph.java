import java.util.ArrayList;
import java.util.List;
public class CampusMatrixGraph {
    private final List<String> places;
    private final boolean[][] matrix;
    CampusMatrixGraph(List<String> places) {
        this.places = new ArrayList<>(places);
        this.matrix = new boolean[places.size()][places.size()];
    }
    private int indexOf(String place) {
        return place == null ? -1 : places.indexOf(place);
    }
    boolean addEdge(String a, String b) {
        int x = indexOf(a);
        int y = indexOf(b);
        if (x < 0 || y < 0 || x == y || matrix[x][y]) {
            return false;
        }
        matrix[x][y] = true;
        matrix[y][x] = true;
        return true;
    }
    boolean removeEdge(String a, String b) {
        int x = indexOf(a);
        int y = indexOf(b);
        if (x < 0 || y < 0 || !matrix[x][y]) {
            return false;
        }
        matrix[x][y] = false;
        matrix[y][x] = false;
        return true;
    }
    int degree(String place) {
        int x = indexOf(place);
        if (x < 0) {
            return 0;
        }
        int count = 0;
        for (boolean connected : matrix[x]) {
            if (connected) {
                count++;
            }
        }
        return count;
    }
    List<String> neighbors(String place) {
        List<String> result = new ArrayList<>();
        int x = indexOf(place);
        if (x < 0) {
            return result;
        }
        for (int i = 0; i < places.size(); i++) {
            if (matrix[x][i]) {
                result.add(places.get(i));
            }
        }
        return result;
    }
    int edgeCount() {
        int total = 0;
        for (boolean[] row : matrix) {
            for (boolean connected : row) {
                if (connected) {
                    total++;
                }
            }
        }
        return total / 2;
    }
    public static void main(String[] args) {
        CampusMatrixGraph campus = new CampusMatrixGraph(List.of("大門", "圖書館", "宿舍", "體育館"));
        System.out.println("大門-圖書館=" + campus.addEdge("大門", "圖書館"));
        System.out.println("圖書館-宿舍=" + campus.addEdge("圖書館", "宿舍"));
        System.out.println("大門-體育館=" + campus.addEdge("大門", "體育館"));
        System.out.println("重複 大門-圖書館=" + campus.addEdge("大門", "圖書館"));
        System.out.println("self-loop=" + campus.addEdge("大門", "大門"));
        System.out.println("missing=" + campus.addEdge("大門", "操場"));
        System.out.println("edgeCount=" + campus.edgeCount());
        System.out.println("大門degree=" + campus.degree("大門") + " neighbors=" + campus.neighbors("大門"));
        System.out.println("removeEdge 大門-體育館=" + campus.removeEdge("大門", "體育館"));
        System.out.println("重複remove=" + campus.removeEdge("大門", "體育館"));
        System.out.println("remove後edgeCount=" + campus.edgeCount() + " 體育館degree=" + campus.degree("體育館"));
        System.out.println("missing degree=" + campus.degree("操場") + " neighbors=" + campus.neighbors("操場"));
    }
}
