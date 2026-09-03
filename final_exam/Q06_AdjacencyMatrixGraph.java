import java.util.ArrayList;
import java.util.List;
public class Q06_AdjacencyMatrixGraph {
    private final List<String> vertices;
    private final boolean[][] matrix;
    public Q06_AdjacencyMatrixGraph(List<String> vertices) {
        this.vertices = new ArrayList<>();
        if (vertices != null) {
            for (String vertex : vertices) {
                if (vertex != null && !this.vertices.contains(vertex)) {
                    this.vertices.add(vertex);
                }
            }
        }
        this.matrix = new boolean[this.vertices.size()][this.vertices.size()];
    }
    private int indexOf(String vertex) {
        if (vertex == null) {
            return -1;
        }
        return vertices.indexOf(vertex);
    }
    public boolean addEdge(String first, String second) {
        int x = indexOf(first);
        int y = indexOf(second);
        if (x < 0 || y < 0 || x == y || matrix[x][y]) {
            return false;
        }
        matrix[x][y] = true;
        matrix[y][x] = true;
        return true;
    }
    public boolean removeEdge(String first, String second) {
        int x = indexOf(first);
        int y = indexOf(second);
        if (x < 0 || y < 0 || !matrix[x][y]) {
            return false;
        }
        matrix[x][y] = false;
        matrix[y][x] = false;
        return true;
    }
    public boolean hasEdge(String first, String second) {
        int x = indexOf(first);
        int y = indexOf(second);
        if (x < 0 || y < 0) {
            return false;
        }
        return matrix[x][y];
    }
    public int degree(String vertex) {
        int x = indexOf(vertex);
        if (x < 0) {
            return 0;
        }
        int count = 0;
        for (int i = 0; i < vertices.size(); i++) {
            if (matrix[x][i]) {
                count++;
            }
        }
        return count;
    }
    public List<String> neighbors(String vertex) {
        List<String> result = new ArrayList<>();
        int x = indexOf(vertex);
        if (x < 0) {
            return result;
        }
        for (int i = 0; i < vertices.size(); i++) {
            if (matrix[x][i]) {
                result.add(vertices.get(i));
            }
        }
        return result;
    }
    public static void main(String[] args) {
        Q06_AdjacencyMatrixGraph graph = new Q06_AdjacencyMatrixGraph(
                List.of("A", "B", "C", "D"));
        System.out.println("A-B=" + graph.addEdge("A", "B"));
        System.out.println("A-C=" + graph.addEdge("A", "C"));
        System.out.println("B-D=" + graph.addEdge("B", "D"));
        System.out.println("重複 A-B=" + graph.addEdge("A", "B"));
        System.out.println("self-loop A-A=" + graph.addEdge("A", "A"));
        System.out.println("missing A-Z=" + graph.addEdge("A", "Z"));
        System.out.println("hasEdge B-A=" + graph.hasEdge("B", "A"));
        System.out.println("hasEdge C-D=" + graph.hasEdge("C", "D"));
        System.out.println("A neighbors=" + graph.neighbors("A"));
        System.out.println("A degree=" + graph.degree("A"));
        System.out.println("removeEdge A-C=" + graph.removeEdge("A", "C"));
        System.out.println("重複 removeEdge A-C=" + graph.removeEdge("A", "C"));
        System.out.println("remove後 A neighbors=" + graph.neighbors("A"));
        System.out.println("missing neighbors=" + graph.neighbors("Z"));
        System.out.println("missing degree=" + graph.degree("Z"));
        System.out.println("null hasEdge=" + graph.hasEdge(null, "A"));
    }
}
