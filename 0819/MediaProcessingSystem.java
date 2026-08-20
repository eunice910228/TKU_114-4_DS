abstract class MediaFile {
    private final String fileName;
    private long sizeKb;

    MediaFile(String fileName, long sizeKb) {
        this.fileName = (fileName == null || fileName.isBlank()) ? "untitled" : fileName;
        this.sizeKb = Math.max(0, sizeKb);
    }

    String getFileName() { return fileName; }
    long getSizeKb()     { return sizeKb; }

    protected void setSizeKb(long sizeKb) { this.sizeKb = Math.max(0, sizeKb); }

    abstract String describe();

    abstract String operations();
}

interface Playable {
    String play();
    String stop();
}

interface Compressible {
    boolean compress(int level);
}

class ImageFile extends MediaFile implements Compressible {
    private final int width;
    private final int height;
    private boolean compressed;

    ImageFile(String fileName, long sizeKb, int width, int height) {
        super(fileName, sizeKb);
        this.width = Math.max(1, width);
        this.height = Math.max(1, height);
    }

    @Override
    String describe() {
        return "圖片 " + getFileName() + " " + width + "x" + height + " " + getSizeKb() + "KB";
    }

    @Override
    public boolean compress(int level) {
        if (compressed || level < 1 || level > 9) {
            return false;
        }
        setSizeKb(getSizeKb() * (10 - level) / 10);
        compressed = true;
        return true;
    }

    @Override
    String operations() {
        boolean ok = compress(3);
        return "  壓縮(3)=" + ok + " 大小=" + getSizeKb() + "KB";
    }
}

class AudioFile extends MediaFile implements Playable, Compressible {
    private final int seconds;
    private boolean compressed;

    AudioFile(String fileName, long sizeKb, int seconds) {
        super(fileName, sizeKb);
        this.seconds = Math.max(0, seconds);
    }

    @Override
    String describe() {
        return "音訊 " + getFileName() + " " + seconds + "秒 " + getSizeKb() + "KB";
    }

    @Override
    public String play() { return "播放音訊 " + getFileName(); }

    @Override
    public String stop() { return "停止音訊 " + getFileName(); }

    @Override
    public boolean compress(int level) {
        if (compressed || level < 1 || level > 9) {
            return false;
        }
        setSizeKb(getSizeKb() * (10 - level) / 10);
        compressed = true;
        return true;
    }

    @Override
    String operations() {
        boolean ok = compress(3);
        return "  " + play() + "\n  " + stop()
                + "\n  壓縮(3)=" + ok + " 大小=" + getSizeKb() + "KB";
    }
}

class VideoFile extends MediaFile implements Playable, Compressible {
    private final int seconds;
    private final String resolution;
    private boolean compressed;

    VideoFile(String fileName, long sizeKb, int seconds, String resolution) {
        super(fileName, sizeKb);
        this.seconds = Math.max(0, seconds);
        this.resolution = (resolution == null || resolution.isBlank()) ? "480p" : resolution;
    }

    @Override
    String describe() {
        return "影片 " + getFileName() + " " + resolution + " " + seconds + "秒 " + getSizeKb() + "KB";
    }

    @Override
    public String play() { return "播放影片 " + getFileName() + "（" + resolution + "）"; }

    @Override
    public String stop() { return "停止影片 " + getFileName(); }

    @Override
    public boolean compress(int level) {
        if (compressed || level < 1 || level > 9) {
            return false;
        }
        setSizeKb(getSizeKb() * (10 - level) / 10);
        compressed = true;
        return true;
    }

    @Override
    String operations() {
        boolean ok = compress(3);
        return "  " + play() + "\n  " + stop()
                + "\n  壓縮(3)=" + ok + " 大小=" + getSizeKb() + "KB";
    }
}

public class MediaProcessingSystem {
    public static void main(String[] args) {
        MediaFile[] library = {
            new ImageFile("123.png", 500, 800, 600),
            new AudioFile("abc.mp3", 48000, 234),
            new VideoFile("demo.mp4", 51200, 100, "800x600")
        };
        System.out.println("=== 檔案清單 ===");
        for (MediaFile f : library) {
            System.out.println("  " + f.describe());
        }

        System.out.println("\n=== 各檔案支援的操作 ===");
        for (MediaFile f : library) {
            System.out.println("[" + f.getFileName() + "]");
            System.out.println(f.operations());
        }
        System.out.println("\n=== 邊界測試 ===");
        MediaFile odd = new ImageFile(null, -500, -10, 0);
        System.out.println("  null 檔名/負數     " + odd.describe());
        ImageFile img = new ImageFile("a.png", 1000, 10, 10);
        System.out.println("  壓縮等級 11        " + img.compress(11));
        System.out.println("  壓縮等級 0         " + img.compress(0));
        System.out.println("  壓縮等級 3         " + img.compress(3) + " 大小=" + img.getSizeKb() + "KB");
        System.out.println("  重複壓縮           " + img.compress(3) + " 大小=" + img.getSizeKb() + "KB");
        MediaFile noRes = new VideoFile("clip.mp4", 800, -50, "  ");
        System.out.println("  空白解析度/負秒數  " + noRes.describe());
    }
}