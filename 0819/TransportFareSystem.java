abstract class Transport {
    private final String routeName;
    Transport(String routeName) {
        this.routeName = (routeName == null || routeName.isBlank()) ? "UNKNOWN" : routeName;
    }
    String getRouteName() {
        return routeName;
    }
    abstract int calculateFare(int distance);
    protected int safeDistance(int distance) {
        return Math.max(0, distance);
    }
}
class Bus extends Transport {
    private final int flatFare;
    Bus(String routeName, int flatFare) {
        super(routeName);
        this.flatFare = Math.max(0, flatFare);
    }
    @Override
    int calculateFare(int distance) {
        int km = safeDistance(distance);
        int sections = 1 + km / 10;
        return flatFare * sections;
    }
}
class Taxi extends Transport {
    private final int startingFare;
    private final int perKm;
    Taxi(String routeName, int startingFare, int perKm) {
        super(routeName);
        this.startingFare = Math.max(0, startingFare);
        this.perKm = Math.max(0, perKm);
    }
    @Override
    int calculateFare(int distance) {
        int km = safeDistance(distance);
        if (km <= 1) {
            return startingFare;
        }
        return startingFare + (km - 1) * perKm;
    }
}
class Metro extends Transport {
    private final int baseFare;
    Metro(String routeName, int baseFare) {
        super(routeName);
        this.baseFare = Math.max(0, baseFare);
    }
    @Override
    int calculateFare(int distance) {
        int km = safeDistance(distance);
        return baseFare + (km / 5) * 5;
    }
}
public class TransportFareSystem {
    public static void main(String[] args) {
        Transport[] transports = {
            new Bus("紅3", 15),
            new Taxi("市區叫車", 85, 25),
            new Metro("板南線", 20),
            new Bus("藍1", 15)
        };
        int distance = 12;
        System.out.println("距離" + distance + "公里的票價");
        for (Transport t : transports) {
            System.out.println(t.getRouteName() + "=" + t.calculateFare(distance) + "元");
        }
        System.out.println("邊界測試");
        for (Transport t : transports) {
            System.out.println(t.getRouteName() + " -5km=" + t.calculateFare(-5) + "元，0km=" + t.calculateFare(0) + "元");
        }
    }
}
