import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Dosya İşleme ve Veri Analizi Programı
 * Bu program, bir metin dosyasındaki kelimeleri analiz eder,
 * frekanslarını hesaplar ve istatistikler sunar.
 */
public class Day18s1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("*** DOSYA ANALİZ PROGRAMI ***");
        System.out.println("Bu program, bir metin dosyasındaki kelimeleri analiz eder.");
        
        while (true) {
            System.out.print("\nAnaliz edilecek dosya yolunu girin (çıkmak için 'q'): ");
            String dosyaYolu = scanner.nextLine();
            
            if (dosyaYolu.equalsIgnoreCase("q")) {
                System.out.println("Program sonlandırılıyor...");
                break;
            }
            
            try {
                // Dosyayı oku ve kelimeleri analiz et
                Map<String, Integer> kelimeFrekansi = dosyayiAnalizEt(dosyaYolu);
                
                if (kelimeFrekansi.isEmpty()) {
                    System.out.println("Dosya boş veya okunamadı!");
                    continue;
                }
                
                // İstatistikleri hesapla
                int toplamKelime = kelimeFrekansi.values().stream().mapToInt(Integer::intValue).sum();
                int benzersizKelime = kelimeFrekansi.size();
                
                // En çok kullanılan kelimeleri bul
                List<Map.Entry<String, Integer>> enCokKullanilanlar = kelimeFrekansi.entrySet()
                    .stream()
                    .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                    .limit(10)
                    .collect(Collectors.toList());
                
                // Sonuçları göster
                System.out.println("\n=== ANALİZ SONUÇLARI ===");
                System.out.println("Dosya: " + dosyaYolu);
                System.out.println("Toplam Kelime Sayısı: " + toplamKelime);
                System.out.println("Benzersiz Kelime Sayısı: " + benzersizKelime);
                System.out.println("\nEn Çok Kullanılan 10 Kelime:");
                System.out.println("------------------------");
                
                enCokKullanilanlar.forEach(entry -> 
                    System.out.printf("%-20s: %d kez%n", entry.getKey(), entry.getValue()));
                
                // Sonuçları dosyaya kaydet
                sonuclariKaydet(dosyaYolu, kelimeFrekansi, toplamKelime, benzersizKelime);
                System.out.println("\nSonuçlar 'analiz_sonuclari.txt' dosyasına kaydedildi.");
                
            } catch (FileNotFoundException e) {
                System.out.println("Hata: Dosya bulunamadı - " + e.getMessage());
            } catch (IOException e) {
                System.out.println("Hata: Dosya okuma hatası - " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Beklenmeyen bir hata oluştu: " + e.getMessage());
            }
        }
        
        scanner.close();
    }
    
    private static Map<String, Integer> dosyayiAnalizEt(String dosyaYolu) throws IOException {
        Map<String, Integer> kelimeFrekansi = new HashMap<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(dosyaYolu))) {
            String satir;
            while ((satir = reader.readLine()) != null) {
                // Satırı kelimelere ayır ve frekansları hesapla
                Arrays.stream(satir.toLowerCase().split("\\W+"))
                    .filter(kelime -> !kelime.isEmpty())
                    .forEach(kelime -> kelimeFrekansi.merge(kelime, 1, Integer::sum));
            }
        }
        
        return kelimeFrekansi;
    }
    
    private static void sonuclariKaydet(String dosyaYolu, Map<String, Integer> kelimeFrekansi,
            int toplamKelime, int benzersizKelime) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter("analiz_sonuclari.txt"))) {
            writer.println("=== DOSYA ANALİZ SONUÇLARI ===");
            writer.println("Tarih: " + new Date());
            writer.println("Dosya: " + dosyaYolu);
            writer.println("------------------------");
            writer.println("Toplam Kelime Sayısı: " + toplamKelime);
            writer.println("Benzersiz Kelime Sayısı: " + benzersizKelime);
            writer.println("\nKelime Frekansları:");
            writer.println("------------------------");
            
            // Kelimeleri alfabetik sırayla yazdır
            kelimeFrekansi.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(entry -> writer.printf("%-20s: %d kez%n", 
                    entry.getKey(), entry.getValue()));
        }
    }
} 