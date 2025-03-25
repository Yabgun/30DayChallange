import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Alışveriş Sepeti Uygulaması
 * Bu uygulama basit bir alışveriş sepeti işlevselliği sunar:
 * - Ürün ekleme
 * - Ürün çıkarma
 * - Sepeti görüntüleme
 * - Toplam fiyatı hesaplama
 */
public class Day13s1 {
    public static void main(String[] args) {
        ShoppingCart cart = new ShoppingCart();
        Scanner scanner = new Scanner(System.in);
        
        // Örnek ürünler ekle
        Product elma = new Product(1, "Elma", 5.99);
        Product ekmek = new Product(2, "Ekmek", 2.50);
        Product sut = new Product(3, "Süt", 7.75);
        Product peynir = new Product(4, "Peynir", 25.90);
        
        // Ürün listesi
        Map<Integer, Product> products = new HashMap<>();
        products.put(elma.getId(), elma);
        products.put(ekmek.getId(), ekmek);
        products.put(sut.getId(), sut);
        products.put(peynir.getId(), peynir);
        
        boolean devam = true;
        
        System.out.println("=== Alışveriş Sepeti Uygulaması ===");
        
        while (devam) {
            System.out.println("\nİşlemler:");
            System.out.println("1. Ürünleri Listele");
            System.out.println("2. Sepete Ürün Ekle");
            System.out.println("3. Sepetten Ürün Çıkar");
            System.out.println("4. Sepeti Görüntüle");
            System.out.println("5. Çıkış");
            
            System.out.print("\nLütfen bir işlem seçin (1-5): ");
            int secim = scanner.nextInt();
            
            switch (secim) {
                case 1:
                    listProducts(products);
                    break;
                case 2:
                    addProductToCart(cart, products, scanner);
                    break;
                case 3:
                    removeProductFromCart(cart, scanner);
                    break;
                case 4:
                    viewCart(cart);
                    break;
                case 5:
                    devam = false;
                    System.out.println("Programdan çıkılıyor. İyi günler!");
                    break;
                default:
                    System.out.println("Geçersiz işlem! Lütfen 1-5 arasında bir sayı girin.");
            }
        }
        
        scanner.close();
    }
    
    // Ürünleri listele
    private static void listProducts(Map<Integer, Product> products) {
        System.out.println("\n=== Mevcut Ürünler ===");
        System.out.println("ID\tÜrün\t\tFiyat");
        System.out.println("-----------------------");
        
        for (Product product : products.values()) {
            System.out.printf("%d\t%-10s\t%.2f TL\n", 
                    product.getId(), product.getName(), product.getPrice());
        }
    }
    
    // Sepete ürün ekle
    private static void addProductToCart(ShoppingCart cart, Map<Integer, Product> products, Scanner scanner) {
        listProducts(products);
        
        System.out.print("\nEklemek istediğiniz ürünün ID'sini girin: ");
        int productId = scanner.nextInt();
        
        if (products.containsKey(productId)) {
            System.out.print("Miktar: ");
            int quantity = scanner.nextInt();
            
            if (quantity > 0) {
                cart.addItem(products.get(productId), quantity);
                System.out.println(quantity + " adet " + products.get(productId).getName() + " sepete eklendi.");
            } else {
                System.out.println("Miktar pozitif bir sayı olmalıdır!");
            }
        } else {
            System.out.println("Geçersiz ürün ID'si!");
        }
    }
    
    // Sepetten ürün çıkar
    private static void removeProductFromCart(ShoppingCart cart, Scanner scanner) {
        if (cart.isEmpty()) {
            System.out.println("Sepetiniz boş!");
            return;
        }
        
        viewCart(cart);
        
        System.out.print("\nÇıkarmak istediğiniz ürünün ID'sini girin: ");
        int productId = scanner.nextInt();
        
        if (cart.containsProduct(productId)) {
            System.out.print("Çıkarmak istediğiniz miktar: ");
            int quantity = scanner.nextInt();
            
            if (quantity > 0) {
                cart.removeItem(productId, quantity);
                System.out.println("Ürün sepetten çıkarıldı.");
            } else {
                System.out.println("Miktar pozitif bir sayı olmalıdır!");
            }
        } else {
            System.out.println("Bu ürün sepetinizde bulunmuyor!");
        }
    }
    
    // Sepeti görüntüle
    private static void viewCart(ShoppingCart cart) {
        List<CartItem> items = cart.getItems();
        
        if (items.isEmpty()) {
            System.out.println("\nSepetiniz boş!");
            return;
        }
        
        System.out.println("\n=== Sepetiniz ===");
        System.out.println("ID\tÜrün\t\tFiyat\t\tMiktar\tToplam");
        System.out.println("-----------------------------------------------------");
        
        for (CartItem item : items) {
            Product product = item.getProduct();
            System.out.printf("%d\t%-10s\t%.2f TL\t%d\t%.2f TL\n", 
                    product.getId(), product.getName(), product.getPrice(), 
                    item.getQuantity(), item.getTotal());
        }
        
        System.out.println("-----------------------------------------------------");
        System.out.printf("Genel Toplam: %.2f TL\n", cart.getTotalPrice());
    }
}

// Ürün sınıfı
class Product {
    private int id;
    private String name;
    private double price;
    
    public Product(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }
    
    public int getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public double getPrice() {
        return price;
    }
}

// Sepet öğesi sınıfı
class CartItem {
    private Product product;
    private int quantity;
    
    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }
    
    public Product getProduct() {
        return product;
    }
    
    public int getQuantity() {
        return quantity;
    }
    
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    public double getTotal() {
        return product.getPrice() * quantity;
    }
}

// Alışveriş sepeti sınıfı
class ShoppingCart {
    private List<CartItem> items;
    
    public ShoppingCart() {
        this.items = new ArrayList<>();
    }
    
    // Sepete ürün ekle
    public void addItem(Product product, int quantity) {
        // Ürün zaten sepette var mı kontrol et
        for (CartItem item : items) {
            if (item.getProduct().getId() == product.getId()) {
                item.setQuantity(item.getQuantity() + quantity);
                return;
            }
        }
        
        // Yoksa yeni öğe olarak ekle
        items.add(new CartItem(product, quantity));
    }
    
    // Sepetten ürün çıkar
    public void removeItem(int productId, int quantity) {
        for (int i = 0; i < items.size(); i++) {
            CartItem item = items.get(i);
            if (item.getProduct().getId() == productId) {
                if (item.getQuantity() <= quantity) {
                    // Eğer çıkarılacak miktar mevcut miktara eşit veya büyükse, ürünü tamamen kaldır
                    items.remove(i);
                } else {
                    // Aksi halde miktarı azalt
                    item.setQuantity(item.getQuantity() - quantity);
                }
                return;
            }
        }
    }
    
    // Sepet boş mu kontrol et
    public boolean isEmpty() {
        return items.isEmpty();
    }
    
    // Ürün sepette var mı kontrol et
    public boolean containsProduct(int productId) {
        for (CartItem item : items) {
            if (item.getProduct().getId() == productId) {
                return true;
            }
        }
        return false;
    }
    
    // Sepetteki tüm öğeleri al
    public List<CartItem> getItems() {
        return items;
    }
    
    // Toplam fiyatı hesapla
    public double getTotalPrice() {
        double total = 0;
        for (CartItem item : items) {
            total += item.getTotal();
        }
        return total;
    }
} 