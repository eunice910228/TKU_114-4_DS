// 抽象基底類別：所有媒體檔案共同的屬性與行為
abstract class MediaFile {
    private final String fileName;
    private long sizeKb;

    MediaFile(String fileName, long sizeKb) {
        this.fileName = fileName;
        this.sizeKb = sizeKb;
    }

    String getFileName() { return fileName; }
    long getSizeKb()     { return sizeKb; }

    protected void setSizeKb(long sizeKb) { this.sizeKb = sizeKb; }

    abstract String describe();
}

// 能力介面：可播放
interface Playable {
    String play();
    String stop();
}

// 能力介面：可壓縮
interface Compressible {
    long compress(int level);
}

// 圖片：壓縮/不可播放
class ImageFile extends MediaFile implements Compressible {
    private final int width;
    private final int height;

    ImageFile(String fileName, long sizeKb, int width, int height) {
        super(fileName, sizeKb);
        this.width = width;
        this.height = height;
    }

    @Override
    String describe() {
        return "圖片 " + getFileName() + " " + width + "x" + height + " " + getSizeKb() + "KB";
    }

    @Override
    public long compress(int level) {
        setSizeKb(getSizeKb() * (10 - level) / 10);
        return getSizeKb();
    }
}

// 音訊：播放/壓縮
class AudioFile extends MediaFile implements Playable, Compressible {
    private final int seconds;

    AudioFile(String fileName, long sizeKb, int seconds) {
        super(fileName, sizeKb);
        this.seconds = seconds;
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
    public long compress(int level) {
        setSizeKb(getSizeKb() * (10 - level) / 10);
        return getSizeKb();
    }
}

// 影片：播放/壓縮
class VideoFile extends MediaFile implements Playable, Compressible {
    private final int seconds;
    private final String resolution;

    VideoFile(String fileName, long sizeKb, int seconds, String resolution) {
        super(fileName, sizeKb);
        this.seconds = seconds;
        this.resolution = resolution;
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
    public long compress(int level) {
        setSizeKb(getSizeKb() * (10 - level) / 10);
        return getSizeKb();
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
            if (f instanceof Playable p) {
                System.out.println("  " + p.play());
                System.out.println("  " + p.stop());
            }
            if (f instanceof Compressible c) {
                System.out.println("  壓縮(3) -> " + c.compress(3) + "KB");
            }
        }
    }
}