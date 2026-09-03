interface Exportable {
    String export(String format);
    long exportedSize();
}
interface Compressible {
    boolean compress(int level);
    double compressionRatio();
}
class BackupDocument implements Exportable, Compressible {
    private final String title;
    private final long originalSize;
    private long currentSize;
    private boolean compressed;
    BackupDocument(String title, long originalSize) {
        this.title = (title == null || title.isBlank()) ? "UNTITLED" : title;
        this.originalSize = Math.max(0, originalSize);
        this.currentSize = this.originalSize;
    }
    String getTitle() {
        return title;
    }
    @Override
    public String export(String format) {
        String f = (format == null || format.isBlank()) ? "txt" : format.toLowerCase();
        return title + "." + f;
    }
    @Override
    public long exportedSize() {
        return currentSize;
    }
    @Override
    public boolean compress(int level) {
        if (compressed) {
            return false;
        }
        if (level < 1 || level > 9) {
            return false;
        }
        double keep = 1.0 - level * 0.08;
        currentSize = (long) (originalSize * Math.max(0.2, keep));
        compressed = true;
        return true;
    }
    @Override
    public double compressionRatio() {
        if (originalSize == 0) {
            return 0.0;
        }
        return (double) currentSize / originalSize;
    }
}
public class DocumentCapabilityDemo {
    public static void main(String[] args) {
        BackupDocument doc = new BackupDocument("2026Q1報表", 10000);
        Exportable asExportable = doc;
        Compressible asCompressible = doc;
        System.out.println("兩個reference是同一物件=" + (asExportable == asCompressible));
        System.out.println("export(pdf)=" + asExportable.export("pdf"));
        System.out.println("exportedSize=" + asExportable.exportedSize());
        // asExportable.compress(5); 編譯錯誤：Exportable 沒有 compress
        System.out.println("compress(5)=" + asCompressible.compress(5));
        System.out.println("compressionRatio=" + asCompressible.compressionRatio());
        // asCompressible.export("pdf"); 編譯錯誤：Compressible 沒有 export
        System.out.println("壓縮後exportedSize=" + asExportable.exportedSize());
        System.out.println("重複壓縮=" + asCompressible.compress(3));
        BackupDocument fresh = new BackupDocument("測試", 1000);
        System.out.println("等級0=" + fresh.compress(0));
        System.out.println("等級10=" + fresh.compress(10));
        BackupDocument empty = new BackupDocument("", 0);
        System.out.println("空標題檔名=" + empty.export(null));
        System.out.println("0bytes壓縮比=" + empty.compressionRatio());
    }
}
