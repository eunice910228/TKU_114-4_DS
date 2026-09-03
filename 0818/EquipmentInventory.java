class Equipment {
    private String id;
    private String name;
    private int availableCount;
    Equipment(String id, String name, int availableCount) {
        this.id = (id == null || id.isBlank()) ? "Unknown" : id.trim();
        this.name = (name == null || name.isBlank()) ? "Unknown" : name.trim();
        this.availableCount = Math.max(0, availableCount);
    }
    boolean borrowOne() {
        if (availableCount <= 0) {
            return false;
        }
        availableCount--;
        return true;
    }
    void returnItems(int quantity) {
        if (quantity > 0) {
            availableCount += quantity;
        }
    }	
    int getAvailableCount() {
        return availableCount;
    }
    @Override
    public String toString() {
        return id + " " + name + " available=" + availableCount;
    }
}
public class EquipmentInventory {
    public static void main(String[] args) {
        Equipment projector = new Equipment("E101", "投影機", 2);
        Equipment cable = new Equipment("   ", "HDMI線", -5);
        System.out.println(projector);
        System.out.println(cable);
        System.out.println("借投影機1=" + projector.borrowOne());
        System.out.println("借投影機2=" + projector.borrowOne());
        System.out.println("借投影機3=" + projector.borrowOne());
        System.out.println("借HDMI線=" + cable.borrowOne());
        projector.returnItems(1);
        cable.returnItems(-3);
        cable.returnItems(4);
        System.out.println(projector);
        System.out.println(cable);
        System.out.println("庫存皆非負=" + (projector.getAvailableCount() >= 0 && cable.getAvailableCount() >= 0));
    }
}
