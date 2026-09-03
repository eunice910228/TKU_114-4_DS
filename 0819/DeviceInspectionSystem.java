abstract class Device {
    private final String deviceId;
    private final String location;
    Device(String deviceId, String location) {
        this.deviceId = (deviceId == null || deviceId.isBlank()) ? "UNKNOWN" : deviceId;
        this.location = (location == null || location.isBlank()) ? "未指定" : location;
    }
    String getDeviceId() {
        return deviceId;
    }
    String getLocation() {
        return location;
    }
    abstract String runDiagnostic();
}
class Laptop extends Device {
    private final int batteryPercent;
    Laptop(String deviceId, String location, int batteryPercent) {
        super(deviceId, location);
        this.batteryPercent = Math.max(0, Math.min(100, batteryPercent));
    }
    @Override
    String runDiagnostic() {
        String health = batteryPercent < 20 ? "電池偏低" : "正常";
        return "筆電 電量=" + batteryPercent + "% " + health;
    }
}
class Printer extends Device {
    private final int tonerPercent;
    private int cleanCount;
    Printer(String deviceId, String location, int tonerPercent) {
        super(deviceId, location);
        this.tonerPercent = Math.max(0, Math.min(100, tonerPercent));
    }
    @Override
    String runDiagnostic() {
        String health = tonerPercent < 15 ? "碳粉不足" : "正常";
        return "印表機 碳粉=" + tonerPercent + "% " + health;
    }
    String cleanPrintHead() {
        cleanCount++;
        return "已清潔噴頭第" + cleanCount + "次";
    }
}
class Router extends Device {
    private final int connectedClients;
    Router(String deviceId, String location, int connectedClients) {
        super(deviceId, location);
        this.connectedClients = Math.max(0, connectedClients);
    }
    @Override
    String runDiagnostic() {
        String health = connectedClients > 50 ? "負載偏高" : "正常";
        return "路由器 連線數=" + connectedClients + " " + health;
    }
}
public class DeviceInspectionSystem {
    public static void main(String[] args) {
        Device[] devices = {
            new Laptop("D-001", "3F辦公室", 15),
            new Printer("D-002", "3F影印間", 8),
            new Router("D-003", "機房", 62),
            new Printer("D-004", "2F櫃台", 90),
            new Laptop("D-005", "會議室", 88)
        };
        System.out.println("全設備健檢");
        for (Device d : devices) {
            System.out.println(d.getDeviceId() + " " + d.getLocation() + " " + d.runDiagnostic());
        }
        System.out.println("只有印表機才做的維護");
        for (Device d : devices) {
            if (d instanceof Printer printer) {
                System.out.println(printer.getDeviceId() + " " + printer.cleanPrintHead());
            }
        }
        System.out.println("邊界測試");
        Device[] edge = {
            new Laptop("D-006", null, -30),
            new Printer(null, "倉庫", 250),
            new Router("D-008", "  ", -5)
        };
        for (Device d : edge) {
            System.out.println(d.getDeviceId() + " " + d.getLocation() + " " + d.runDiagnostic());
        }
    }
}
