import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Day24s1 - Yazılım Yürütme Modelleri Gösterimi
 * Bu program, farklı yazılım yürütme modellerini (Stand-alone, Hybrid, SaaS) simüle eder.
 */
public class Day24s1 {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Yazılım Yürütme Modelleri Gösterimi");
        System.out.println("===================================");
        System.out.println("1. Stand-alone Model");
        System.out.println("2. Hybrid Model");
        System.out.println("3. Software as a Service (SaaS) Model");
        System.out.print("Lütfen incelemek istediğiniz modeli seçin (1-3): ");
        
        int secim = scanner.nextInt();
        
        switch (secim) {
            case 1:
                standAloneModelDemo();
                break;
            case 2:
                hybridModelDemo();
                break;
            case 3:
                saasModelDemo();
                break;
            default:
                System.out.println("Geçersiz seçim!");
        }
        
        scanner.close();
    }
    
    /**
     * Stand-alone model simülasyonu
     * Tüm işlemler kullanıcı bilgisayarında gerçekleşir
     */
    private static void standAloneModelDemo() {
        System.out.println("\nStand-alone Model Simülasyonu");
        System.out.println("---------------------------");
        System.out.println("Bu modelde yazılım tamamen kullanıcının bilgisayarında çalışır.");
        
        // Kullanıcı bilgisayarında çalışan işlemler
        System.out.println("Kullanıcı Bilgisayarında:");
        System.out.println(" - Kullanıcı arayüzü yükleniyor...");
        System.out.println(" - Ürün işlevselliği başlatılıyor...");
        System.out.println(" - Kullanıcı verileri yerel olarak depolanıyor...");
        
        // Stand-alone bir uygulamayı temsil eden sınıf
        LocalApplication app = new LocalApplication("Örnek Ofis Yazılımı");
        app.start();
        app.processData("Örnek doküman");
        app.save();
        
        System.out.println("\nBu modelin avantajları:");
        System.out.println(" - İnternet bağlantısı gerekmez");
        System.out.println(" - Veri güvenliği (veriler yerel olarak saklanır)");
        System.out.println(" - Hızlı erişim (ağ gecikmesi yoktur)");
        
        System.out.println("\nDezavantajları:");
        System.out.println(" - Güncellemeler manuel olarak yapılmalıdır");
        System.out.println(" - Farklı cihazlarda senkronizasyon zorluğu");
        System.out.println(" - Donanım gereksinimleri daha yüksek olabilir");
    }
    
    /**
     * Hybrid model simülasyonu
     * İşlemlerin bir kısmı kullanıcı bilgisayarında, bir kısmı sunucuda gerçekleşir
     */
    private static void hybridModelDemo() {
        System.out.println("\nHybrid Model Simülasyonu");
        System.out.println("----------------------");
        System.out.println("Bu modelde yazılımın bir kısmı kullanıcının bilgisayarında,");
        System.out.println("bazı özellikler ise geliştiricinin sunucularında çalışır.");
        
        // Kullanıcı bilgisayarında çalışan işlemler
        System.out.println("\nKullanıcı Bilgisayarında:");
        System.out.println(" - Kullanıcı arayüzü yükleniyor...");
        System.out.println(" - Temel işlevsellik başlatılıyor...");
        System.out.println(" - Bazı veriler yerel olarak işleniyor...");
        
        // Sunucuda çalışan işlemler
        System.out.println("\nSunucuda:");
        System.out.println(" - Gelişmiş özellikler hazır...");
        System.out.println(" - Kullanıcı verileri sunucuya yedekleniyor...");
        System.out.println(" - Güncellemeler otomatik olarak kontrol ediliyor...");
        
        // Hibrit bir uygulamayı temsil eden sınıf
        HybridApplication app = new HybridApplication("Örnek Hibrit Uygulama");
        app.start();
        app.processLocalData("Yerel veri");
        app.connectToServer();
        app.syncWithServer();
        app.useCloudFeature("Bulut özelliği");
        
        System.out.println("\nBu modelin avantajları:");
        System.out.println(" - Bazı özellikler çevrimdışı çalışabilir");
        System.out.println(" - Daha esnek bir kullanım sunar");
        System.out.println(" - Veri yedekleme ve senkronizasyon kolaylığı");
        
        System.out.println("\nDezavantajları:");
        System.out.println(" - Bazı özellikler için internet bağlantısı gerekir");
        System.out.println(" - Daha karmaşık mimari");
        System.out.println(" - Çevrimiçi/çevrimdışı durum yönetimi gerektir");
    }
    
    /**
     * SaaS model simülasyonu
     * Tüm işlemler sunucuda gerçekleşir, kullanıcı tarayıcı üzerinden erişir
     */
    private static void saasModelDemo() {
        System.out.println("\nSoftware as a Service (SaaS) Model Simülasyonu");
        System.out.println("-------------------------------------------");
        System.out.println("Bu modelde tüm ürün özellikleri geliştiricinin sunucularında");
        System.out.println("çalışır ve kullanıcı bunlara tarayıcı veya uygulama üzerinden erişir.");
        
        // Kullanıcı bilgisayarında çalışan işlemler
        System.out.println("\nKullanıcı Bilgisayarında:");
        System.out.println(" - Tarayıcı arayüzü yükleniyor...");
        System.out.println(" - Sunucuya bağlantı kuruluyor...");
        
        // Sunucuda çalışan işlemler
        System.out.println("\nSunucuda:");
        System.out.println(" - Kullanıcı kimlik doğrulaması yapılıyor...");
        System.out.println(" - Tüm ürün işlevselliği hazırlanıyor...");
        System.out.println(" - Kullanıcı verileri sunucuda işleniyor ve saklanıyor...");
        
        // SaaS bir uygulamayı temsil eden sınıf
        CloudApplication app = new CloudApplication("Örnek SaaS Uygulaması");
        app.openInBrowser();
        app.login("kullanici", "sifre");
        app.useCloudFeature("Belge düzenleme");
        app.saveToCloud();
        
        System.out.println("\nBu modelin avantajları:");
        System.out.println(" - Herhangi bir cihazdan erişim imkanı");
        System.out.println(" - Otomatik güncellemeler ve bakım");
        System.out.println(" - Düşük donanım gereksinimleri");
        
        System.out.println("\nDezavantajları:");
        System.out.println(" - Sürekli internet bağlantısı gerektirir");
        System.out.println(" - Veri gizliliği endişeleri (veriler uzak sunucularda saklanır)");
        System.out.println(" - Hizmete bağımlılık (sağlayıcı hizmeti durdurursa sorun oluşabilir)");
    }
}

// Stand-alone uygulama sınıfı
class LocalApplication {
    private String name;
    private List<String> localData;
    
    public LocalApplication(String name) {
        this.name = name;
        this.localData = new ArrayList<>();
        System.out.println("  > " + name + " uygulaması yerel olarak başlatıldı.");
    }
    
    public void start() {
        System.out.println("  > " + name + " uygulaması çalıştırılıyor (kullanıcı bilgisayarında).");
    }
    
    public void processData(String data) {
        System.out.println("  > Veri işleniyor: " + data + " (tamamen yerel olarak)");
        localData.add(data);
    }
    
    public void save() {
        System.out.println("  > Veriler yerel diske kaydediliyor: " + localData.size() + " öğe.");
    }
}

// Hibrit uygulama sınıfı
class HybridApplication {
    private String name;
    private List<String> localData;
    private boolean connectedToServer;
    
    public HybridApplication(String name) {
        this.name = name;
        this.localData = new ArrayList<>();
        this.connectedToServer = false;
        System.out.println("  > " + name + " hibrit uygulaması başlatıldı.");
    }
    
    public void start() {
        System.out.println("  > " + name + " uygulaması yerel olarak çalıştırılıyor.");
    }
    
    public void processLocalData(String data) {
        System.out.println("  > Veri yerel olarak işleniyor: " + data);
        localData.add(data);
    }
    
    public void connectToServer() {
        System.out.println("  > Sunucuya bağlanılıyor...");
        connectedToServer = true;
    }
    
    public void syncWithServer() {
        if (connectedToServer) {
            System.out.println("  > Yerel veriler sunucu ile senkronize ediliyor: " + localData.size() + " öğe.");
        } else {
            System.out.println("  > Sunucu bağlantısı yok. Senkronizasyon yapılamıyor.");
        }
    }
    
    public void useCloudFeature(String feature) {
        if (connectedToServer) {
            System.out.println("  > Sunucu tabanlı özellik kullanılıyor: " + feature);
        } else {
            System.out.println("  > Sunucu bağlantısı yok. " + feature + " kullanılamıyor.");
        }
    }
}

// SaaS uygulama sınıfı
class CloudApplication {
    private String name;
    private boolean loggedIn;
    
    public CloudApplication(String name) {
        this.name = name;
        this.loggedIn = false;
        System.out.println("  > " + name + " bulut uygulaması hazırlanıyor.");
    }
    
    public void openInBrowser() {
        System.out.println("  > " + name + " tarayıcıda açılıyor.");
    }
    
    public void login(String username, String password) {
        System.out.println("  > Kullanıcı girişi yapılıyor: " + username);
        loggedIn = true;
    }
    
    public void useCloudFeature(String feature) {
        if (loggedIn) {
            System.out.println("  > Bulut özelliği kullanılıyor: " + feature + " (tüm işleme sunucuda)");
        } else {
            System.out.println("  > Giriş yapılmamış. " + feature + " kullanılamıyor.");
        }
    }
    
    public void saveToCloud() {
        if (loggedIn) {
            System.out.println("  > Veriler bulutta saklanıyor.");
        } else {
            System.out.println("  > Giriş yapılmamış. Buluta kayıt yapılamıyor.");
        }
    }
} 