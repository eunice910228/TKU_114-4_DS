class Product {
    String id;
    String name;
    int stock;

    Product(String id, String name, int stock) {
        this.id = id;
        this.name = name;
        this.stock = stock;
    }

    @Override
    public String toString() {
        return id + " " + name + " 庫存=" + stock;
    }
}

class ProductNode {
    Product product;
    ProductNode left;
    ProductNode right;

    ProductNode(Product product) {
        this.product = product;
    }
}

public class ProductInventoryBst {
    private ProductNode root;

    boolean add(Product product) {
        if (product == null || find(product.id) != null) {
            return false;
        }
        root = add(root, product);
        return true;
    }

    private ProductNode add(ProductNode node, Product product) {
        if (node == null) {
            return new ProductNode(product);
        }
        if (product.id.compareTo(node.product.id) < 0) {
            node.left = add(node.left, product);
        } else {
            node.right = add(node.right, product);
        }
        return node;
    }

    Product find(String id) {
        ProductNode current = root;
        while (current != null) {
            int compare = id.compareTo(current.product.id);
            if (compare == 0) {
                return current.product;
            }
            current = (compare < 0) ? current.left : current.right;
        }
        return null;
    }

    boolean restock(String id, int amount) {
        Product product = find(id);
        if (product == null || amount <= 0) {
            return false;
        }
        product.stock = product.stock + amount;
        return true;
    }

    boolean deduct(String id, int amount) {
        Product product = find(id);
        if (product == null || amount <= 0 || product.stock < amount) {
            return false;
        }
        product.stock = product.stock - amount;
        return true;
    }

    boolean delete(String id) {
        if (find(id) == null) {
            return false;
        }
        root = delete(root, id);
        return true;
    }

    private ProductNode delete(ProductNode node, String id) {
        if (node == null) {
            return null;
        }
        int compare = id.compareTo(node.product.id);
        if (compare < 0) {
            node.left = delete(node.left, id);
        } else if (compare > 0) {
            node.right = delete(node.right, id);
        } else {
            if (node.left == null) {
                return node.right;
            }
            if (node.right == null) {
                return node.left;
            }
            ProductNode successor = node.right;
            while (successor.left != null) {
                successor = successor.left;
            }
            node.product = successor.product;
            node.right = delete(node.right, successor.product.id);
        }
        return node;
    }

    void inorderReport() {
        System.out.println("庫存報表（依商品編號）");
        inorderReport(root);
    }

    private void inorderReport(ProductNode node) {
        if (node == null) {
            return;
        }
        inorderReport(node.left);
        System.out.println("  " + node.product);
        inorderReport(node.right);
    }

    public static void main(String[] args) {
        ProductInventoryBst inventory = new ProductInventoryBst();
        System.out.println("add P30 = " + inventory.add(new Product("P30", "鍵盤", 12)));
        System.out.println("add P10 = " + inventory.add(new Product("P10", "滑鼠", 30)));
        System.out.println("add P50 = " + inventory.add(new Product("P50", "螢幕", 5)));
        System.out.println("add P20 = " + inventory.add(new Product("P20", "耳機", 8)));
        System.out.println("重複 add P10 = " + inventory.add(new Product("P10", "重複", 1)));
        inventory.inorderReport();

        System.out.println("find P20 = " + inventory.find("P20"));
        System.out.println("find P99 = " + inventory.find("P99"));

        System.out.println("restock P50 加 10 = " + inventory.restock("P50", 10));
        System.out.println("deduct P10 扣 25 = " + inventory.deduct("P10", 25));
        System.out.println("deduct P10 再扣 25（不足）= " + inventory.deduct("P10", 25));
        System.out.println("restock P99 = " + inventory.restock("P99", 5));
        inventory.inorderReport();

        System.out.println("delete P30（root）= " + inventory.delete("P30"));
        System.out.println("delete P99 = " + inventory.delete("P99"));
        inventory.inorderReport();
    }
}
