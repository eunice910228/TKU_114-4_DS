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
    boolean addFirst(Task task) {
        if (task == null || findById(task.getId()) != null) {
            return false;
        }
        TaskNode node = new TaskNode(task);
        if (head == null) {
            head = node;
            tail = node;
        } else {
            node.next = head;
            head = node;
        }
        size++;
        return true;
    }
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
    boolean removeById(String id) {
        if (id == null || head == null) {
            return false;
        }
        if (head.task.getId().equals(id)) {
            head = head.next;
            if (head == null) {
                tail = null;
            }
            size--;
            return true;
        }
        TaskNode previous = head;
        TaskNode current = head.next;
        while (current != null) {
            if (current.task.getId().equals(id)) {
                previous.next = current.next;
                if (current == tail) {
                    tail = previous;
                }
                size--;
                return true;
            }
            previous = current;
            current = current.next;
        }
        return false;
    }
	
    boolean insertAfter(String existingId, Task task) {
        if (task == null || findById(task.getId()) != null) {
            return false;
        }
        TaskNode target = findNode(existingId);
        if (target == null) {
            return false;
        }
        TaskNode node = new TaskNode(task);
        node.next = target.next;
        target.next = node;
        if (target == tail) {
            tail = node;
        }
        size++;
        return true;
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
        System.out.println("isEmpty =" + list.isEmpty());
        System.out.println("findById(A01) =" + list.findById("A01"));
        System.out.println("removeById(A01) =" + list.removeById("A01"));
        System.out.println("insertAfter(A01, A09) ="
                + list.insertAfter("A01", new Task("A09", "測試", 3)));
        System.out.println("addLast 建立清單");
        show("addLast(A01)", list.addLast(new Task("A01", "寫報告", 2)), list);
        show("addLast(A02)", list.addLast(new Task("A02", "開會", 1)), list);
        show("addLast(A03)", list.addLast(new Task("A03", "回信", 3)), list);
        System.out.println("addFirst");
        show("addFirst(A00)", list.addFirst(new Task("A00", "緊急處理", 1)), list);
        System.out.println("重複 id 與 null");
        show("addLast(A02 重複)", list.addLast(new Task("A02", "重複的", 5)), list);
        show("addLast(null)", list.addLast(null), list);
        System.out.println("findById");
        System.out.println("findById(A02) =" + list.findById("A02"));
        System.out.println("findById(A77) =" + list.findById("A77"));
        System.out.println("findById(null) =" + list.findById(null));
        System.out.println("insertAfter");
        show("insertAfter(A01, A05)",
                list.insertAfter("A01", new Task("A05", "校稿", 2)), list);
        show("insertAfter(A03, A04) 插在 tail 後面",
                list.insertAfter("A03", new Task("A04", "歸檔", 4)), list);
        show("insertAfter(A77, A06) 參考節點不存在",
                list.insertAfter("A77", new Task("A06", "不會被加入", 3)), list);
        show("insertAfter(A01, A02) 重複 id",
                list.insertAfter("A01", new Task("A02", "重複", 3)), list);
        System.out.println("刪除 head");
        show("removeById(A00)", list.removeById("A00"), list);
        System.out.println("刪除 middle");
        show("removeById(A02)", list.removeById("A02"), list);
        System.out.println("刪除 tail");
        show("removeById(A04)", list.removeById("A04"), list);
        System.out.println("刪除找不到的 id");
        show("removeById(A77)", list.removeById("A77"), list);
        show("removeById(null)", list.removeById(null), list);
        System.out.println("清空");
        show("removeById(A01)", list.removeById("A01"), list);
        show("removeById(A05)", list.removeById("A05"), list);
        show("removeById(A03)", list.removeById("A03"), list);
        show("removeById(A03) 再刪一次", list.removeById("A03"), list);
        System.out.println("清空後，正常使用");
        show("addFirst(A10)", list.addFirst(new Task("A10", "新任務", 1)), list);
        show("addLast(A11)", list.addLast(new Task("A11", "第二筆", 2)), list);
    }
}
