class Task {
    private String id;
    private String title;
    private int priority;
    Task(String id, String title, int priority) {
        this.id = id;
        this.title = title;
        this.priority = priority;
    }
    String getId() {
        return id;
    }
    @Override
    public String toString() {
        return id + " " + title + " P" + priority;
    }
}

class TaskNode {
    Task task;
    TaskNode next;
    TaskNode(Task task) {
        this.task = task;
        this.next = null;
    }
}

class TaskLinkedList {
    private TaskNode head;
    private TaskNode tail;
    private int size;
    boolean addLast(Task task) {
        if (task == null || findById(task.getId()) != null) {
            return false;
        }
        TaskNode node = new TaskNode(task);
        if (head == null) {
            head = node;
            tail = node;
        } else {
            tail.next = node;
            tail = node;
        }
        size++;
        return true;
    }
    Task findById(String id) {
        TaskNode node = findNode(id);
        if (node == null) {
            return null;
        }
        return node.task;
    }
    private TaskNode findNode(String id) {
        if (id == null) {
            return null;
        }
        TaskNode current = head;
        while (current != null) {
            if (current.task.getId().equals(id)) {
                return current;
            }
            current = current.next;
        }
        return null;
    }
    int size() {
        return size;
    }
    boolean isEmpty() {
        return head == null;
    }
    void printAll() {
        if (head == null) {
            System.out.println("空清單 size=0 head=null tail=null");
            return;
        }
        String line = "    ";
        TaskNode current = head;
        while (current != null) {
            line = line + current.task;
            if (current.next != null) {
                line = line + " -> ";
            }
            current = current.next;
        }
        System.out.println(line + "  size=" + size
                + " head=" + head.task.getId() + " tail=" + tail.task.getId());
    }
}

public class LinkedTaskListSystem {
    static void show(String label, boolean ok, TaskLinkedList list) {
        System.out.println("  " + label + " = " + ok);
        list.printAll();
    }
    public static void main(String[] args) {
        TaskLinkedList list = new TaskLinkedList();
        System.out.println("空 list");
        list.printAll();
        System.out.println("isEmpty = " + list.isEmpty());
        System.out.println("findById(A01) = " + list.findById("A01"));
        System.out.println("addLast 建立清單");
        show("addLast(A01)", list.addLast(new Task("A01", "寫報告", 2)), list);
        show("addLast(A02)", list.addLast(new Task("A02", "開會", 1)), list);
        show("addLast(A03)", list.addLast(new Task("A03", "回信", 3)), list);
        System.out.println("重複 id 與 null");
        show("addLast(A02 重複)", list.addLast(new Task("A02", "重複的", 5)), list);
        show("addLast(null)", list.addLast(null), list);
        System.out.println("findById");
        System.out.println("findById(A02) = " + list.findById("A02"));
        System.out.println("findById(A77) = " + list.findById("A77"));
        System.out.println("findById(null) = " + list.findById(null));
		
		
		
		
		
		
    }
}
