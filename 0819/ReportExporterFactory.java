interface ReportExporter {
    String export(String title, int[] values);
    String formatName();
}

class CsvExporter implements ReportExporter {
    @Override
    public String export(String title, int[] values) {
        if (values == null) {
            values = new int[0];
        }
        StringBuilder sb = new StringBuilder();
        sb.append("index,value\n");
        for (int i = 0; i < values.length; i++) {
            sb.append(i).append(',').append(values[i]).append('\n');
        }
        return sb.toString();
    }
    @Override
    public String formatName() { return "CSV"; }
}

class JsonExporter implements ReportExporter {
    @Override
    public String export(String title, int[] values) {
        if (values == null) {
            values = new int[0];
        }
        if (title == null || title.isBlank()) {
            title = "report";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("{\"標題\":\"").append(title.trim()).append("\",\"資料\":[");
        for (int i = 0; i < values.length; i++) {
            if (i > 0) {
                sb.append(',');
            }
            sb.append(values[i]);
        }
        sb.append("]}");
        return sb.toString();
    }
    @Override
    public String formatName() { return "JSON"; }
}

class TextExporter implements ReportExporter {
    @Override
    public String export(String title, int[] values) {
        if (values == null) {
            values = new int[0];
        }
        if (title == null || title.isBlank()) {
            title = "report";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("=== ").append(title.trim()).append(" ===\n");
        if (values.length == 0) {
            sb.append("  (無資料)\n");
        }
        for (int i = 0; i < values.length; i++) {
            sb.append("  [").append(i).append("] ").append(values[i]).append('\n');
        }
        return sb.toString();
    }
    @Override
    public String formatName() { return "TEXT"; }
}

public class ReportExporterFactory {

    static ReportExporter createExporter(String format) {
        if ("csv".equalsIgnoreCase(format)) {
            return new CsvExporter();
        }
        if ("json".equalsIgnoreCase(format)) {
            return new JsonExporter();
        }
        return new TextExporter();
    }

    static void exportReport(ReportExporter exporter, String title, int[] values) {
        if (exporter == null) {
            System.out.println("--- 未指定 exporter，無法輸出 ---\n");
            return;
        }
        System.out.println("--- " + exporter.formatName() + " ---");
        System.out.println(exporter.export(title, values));
    }

    public static void main(String[] args) {
        int[] data = { 1, 25, 88, 300 };

        System.out.println("=== 三種格式輸出同一份資料 ===");
        exportReport(createExporter("csv"), "第一季銷售", data);
        exportReport(createExporter("json"), "第一季銷售", data);
        exportReport(createExporter("text"), "第一季銷售", data);

        System.out.println("=== 不支援的格式回傳TextExporter ===");
        exportReport(createExporter("pdf"), "第一季銷售", data);

        System.out.println("=== 邊界：values 為 null ===");
        exportReport(createExporter("csv"), "空報表", null);

        System.out.println("=== 邊界：title 為 null 或空白 ===");
        exportReport(createExporter("json"), null, data);
        exportReport(createExporter("text"), "   ", data);

        System.out.println("=== 邊界：format 為 null、exporter 為 null ===");
        exportReport(createExporter(null), "第一季銷售", new int[0]);
        exportReport(null, "第一季銷售", data);
    }
}