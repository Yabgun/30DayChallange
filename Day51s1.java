import java.util.Scanner;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Day51s1 {
    private static Map<String, Double> gelirler = new HashMap<>();
    private static Map<String, Double> giderler = new HashMap<>();
    private static Map<String, Double> bütçeLimitleri = new HashMap<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Kişisel Bütçe Takip Sistemi");
        System.out.println("---------------------------");
        
        while (true) {
            System.out.println("\n1. Gelir Ekle");
            System.out.println("2. Gider Ekle");
            System.out.println("3. Bütçe Limiti Belirle");
            System.out.println("4. Gelir Raporu");
            System.out.println("5. Gider Raporu");
            System.out.println("6. Bütçe Durumu");
            System.out.println("7. Çıkış");
            System.out.print("Seçiminiz (1-7): ");
            
            int secim = scanner.nextInt();
            scanner.nextLine(); // Buffer temizleme
            
            switch (secim) {
                case 1:
                    gelirEkle();
                    break;
                case 2:
                    giderEkle();
                    break;
                case 3:
                    butceLimitiBelirle();
                    break;
                case 4:
                    gelirRaporu();
                    break;
                case 5:
                    giderRaporu();
                    break;
                case 6:
                    butceDurumu();
                    break;
                case 7:
                    System.out.println("Program sonlandırılıyor...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Geçersiz seçim! Lütfen tekrar deneyin.");
            }
        }
    }

    private static void gelirEkle() {
        System.out.print("Gelir kategorisi girin (örn: Maaş, Freelance, Yatırım): ");
        String kategori = scanner.nextLine();
        
        System.out.print("Gelir miktarı girin: ");
        double miktar = scanner.nextDouble();
        scanner.nextLine();
        
        gelirler.put(kategori, gelirler.getOrDefault(kategori, 0.0) + miktar);
        System.out.println("Gelir başarıyla eklendi!");
    }

    private static void giderEkle() {
        System.out.print("Gider kategorisi girin (örn: Market, Faturalar, Eğlence): ");
        String kategori = scanner.nextLine();
        
        System.out.print("Gider miktarı girin: ");
        double miktar = scanner.nextDouble();
        scanner.nextLine();
        
        giderler.put(kategori, giderler.getOrDefault(kategori, 0.0) + miktar);
        System.out.println("Gider başarıyla eklendi!");
    }

    private static void butceLimitiBelirle() {
        System.out.print("Kategori girin: ");
        String kategori = scanner.nextLine();
        
        System.out.print("Aylık bütçe limiti girin: ");
        double limit = scanner.nextDouble();
        scanner.nextLine();
        
        bütçeLimitleri.put(kategori, limit);
        System.out.println("Bütçe limiti başarıyla belirlendi!");
    }

    private static void gelirRaporu() {
        if (gelirler.isEmpty()) {
            System.out.println("Henüz gelir kaydı bulunmuyor!");
            return;
        }

        System.out.println("\nGelir Raporu (" + LocalDate.now() + ")");
        System.out.println("----------------");
        double toplamGelir = 0;
        
        for (Map.Entry<String, Double> entry : gelirler.entrySet()) {
            System.out.printf("%s: %.2f TL\n", entry.getKey(), entry.getValue());
            toplamGelir += entry.getValue();
        }
        
        System.out.println("----------------");
        System.out.printf("Toplam Gelir: %.2f TL\n", toplamGelir);
    }

    private static void giderRaporu() {
        if (giderler.isEmpty()) {
            System.out.println("Henüz gider kaydı bulunmuyor!");
            return;
        }

        System.out.println("\nGider Raporu (" + LocalDate.now() + ")");
        System.out.println("----------------");
        double toplamGider = 0;
        
        for (Map.Entry<String, Double> entry : giderler.entrySet()) {
            System.out.printf("%s: %.2f TL\n", entry.getKey(), entry.getValue());
            toplamGider += entry.getValue();
        }
        
        System.out.println("----------------");
        System.out.printf("Toplam Gider: %.2f TL\n", toplamGider);
    }

    private static void butceDurumu() {
        double toplamGelir = gelirler.values().stream().mapToDouble(Double::doubleValue).sum();
        double toplamGider = giderler.values().stream().mapToDouble(Double::doubleValue).sum();
        
        System.out.println("\nBütçe Durumu (" + LocalDate.now() + ")");
        System.out.println("----------------");
        System.out.printf("Toplam Gelir: %.2f TL\n", toplamGelir);
        System.out.printf("Toplam Gider: %.2f TL\n", toplamGider);
        System.out.printf("Net Durum: %.2f TL\n", toplamGelir - toplamGider);
        
        System.out.println("\nKategori Bazlı Bütçe Durumu:");
        System.out.println("----------------");
        
        for (Map.Entry<String, Double> entry : bütçeLimitleri.entrySet()) {
            String kategori = entry.getKey();
            double limit = entry.getValue();
            double gider = giderler.getOrDefault(kategori, 0.0);
            double kalan = limit - gider;
            
            System.out.printf("%s:\n", kategori);
            System.out.printf("  Limit: %.2f TL\n", limit);
            System.out.printf("  Harcanan: %.2f TL\n", gider);
            System.out.printf("  Kalan: %.2f TL\n", kalan);
            System.out.println("----------------");
        }
    }
} 