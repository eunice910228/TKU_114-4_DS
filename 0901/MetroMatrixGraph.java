public class MetroMatrixGraph {
    private final String[] stations;
    private final boolean[][] matrix;
    MetroMatrixGraph(String[] stations) {
        if (stations == null || stations.length == 0) {
            throw new IllegalArgumentException("stations 不可為空");
        }
        this.stations = stations;
        this.matrix = new boolean[stations.length][stations.length];
    }
    private int indexOf(String station) {
        for (int i = 0; i < stations.length; i++) {
            if (stations[i].equals(station)) {
                return i;
            }
        }
        return -1;
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
    boolean hasEdge(String a, String b) {
        int x = indexOf(a);
        int y = indexOf(b);
        if (x < 0 || y < 0) {
            return false;
        }
        return matrix[x][y];
    }
    void neighbors(String station) {
        int x = indexOf(station);
        System.out.print(station + "的鄰站=");
        if (x < 0) {
            System.out.println("查無此站");
            return;
        }
        for (int i = 0; i < stations.length; i++) {
            if (matrix[x][i]) {
                System.out.print(stations[i] + " ");
            }
        }
        System.out.println();
    }
    int degree(String station) {
        int x = indexOf(station);
        if (x < 0) {
            return 0;
        }
        int count = 0;
        for (int i = 0; i < stations.length; i++) {
            if (matrix[x][i]) {
                count++;
            }
        }
        return count;
    }
    int edgeCount() {
        int total = 0;
        for (int i = 0; i < stations.length; i++) {
            for (int j = 0; j < stations.length; j++) {
                if (matrix[i][j]) {
                    total++;
                }
            }
        }
        return total / 2;
    }
    void matrixReport() {
        System.out.println("matrix報表");
        System.out.print("      ");
        for (String station : stations) {
            System.out.print(station + " ");
        }
        System.out.println();
        for (int i = 0; i < stations.length; i++) {
            System.out.print("  " + stations[i] + " ");
            for (int j = 0; j < stations.length; j++) {
                System.out.print(matrix[i][j] ? "1   " : "0   ");
            }
            System.out.println();
        }
    }
    public static void main(String[] args) {
        String[] stations = { "台北", "板橋", "淡水", "新店" };
        MetroMatrixGraph metro = new MetroMatrixGraph(stations);
        System.out.println("台北-板橋=" + metro.addEdge("台北", "板橋"));
        System.out.println("台北-淡水=" + metro.addEdge("台北", "淡水"));
        System.out.println("板橋-新店=" + metro.addEdge("板橋", "新店"));
        System.out.println("重複 台北-板橋=" + metro.addEdge("台北", "板橋"));
        System.out.println("不存在站 台北-高雄=" + metro.addEdge("台北", "高雄"));
        System.out.println("自己連自己 台北-台北=" + metro.addEdge("台北", "台北"));
        System.out.println("hasEdge 台北-板橋=" + metro.hasEdge("台北", "板橋"));
        System.out.println("hasEdge 淡水-新店=" + metro.hasEdge("淡水", "新店"));
        metro.neighbors("台北");
        metro.neighbors("高雄");
        System.out.println("台北degree=" + metro.degree("台北"));
        System.out.println("新店degree=" + metro.degree("新店"));
        System.out.println("查無站degree=" + metro.degree("高雄"));
        MetroMatrixGraph single = new MetroMatrixGraph(new String[]{ "孤站" });
        System.out.println("單站edge總數=" + single.edgeCount()
                + "，degree=" + single.degree("孤站"));
        System.out.println("edge總數=" + metro.edgeCount());
        metro.matrixReport();
    }
}
