import java.io.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * Gelişmiş Dosya İşleme ve Analiz Programı
 * Bu program, belirtilen dizindeki dosyaları paralel olarak işler,
 * kelime frekanslarını hesaplar ve sonuçları dosyaya kaydeder.
 */
public class Day16s1 {
    private static final int THREAD_POOL_SIZE = 4;
    private static final ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("*** GELİŞMİŞ DOSYA ANALİZ PROGRAMI ***");
        
        try {
            // Kullanıcıdan dizin yolunu al
            System.out.print("Analiz edilecek dizin yolunu girin: ");
            String dizinYolu = scanner.nextLine();
            
            // Dosyaları bul
            List<File> dosyalar = dosyalariBul(dizinYolu);
            
            if (dosyalar.isEmpty()) {
                System.out.println("Belirtilen dizinde dosya bulunamadı!");
                return;
            }
            
            System.out.println("\nToplam " + dosyalar.size() + " dosya bulundu.");
            System.out.println("Dosyalar analiz ediliyor...\n");
            
            // Dosyaları paralel olarak işle
            List<Future<Map<String, Integer>>> gelecekler = new ArrayList<>();
            for (File dosya : dosyalar) {
                gelecekler.add(executorService.submit(() -> dosyaAnalizEt(dosya)));
            }
            
            // Sonuçları birleştir
            Map<String, Integer> toplamKelimeFrekansi = new ConcurrentHashMap<>();
            for (Future<Map<String, Integer>> gelecek : gelecekler) {
                Map<String, Integer> sonuc = gelecek.get();
                sonuc.forEach((kelime, frekans) -> 
                    toplamKelimeFrekansi.merge(kelime, frekans, Integer::sum));
            }
            
            // Sonuçları sırala ve göster
            List<Map.Entry<String, Integer>> siralanmisSonuclar = toplamKelimeFrekansi.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(10)
                .collect(Collectors.toList());
            
            // Sonuçları dosyaya kaydet
            sonuclariKaydet(siralanmisSonuclar, "analiz_sonuclari.txt");
            
            System.out.println("=== EN ÇOK KULLANILAN 10 KELİME ===");
            siralanmisSonuclar.forEach(entry -> 
                System.out.printf("%-20s: %d kez%n", entry.getKey(), entry.getValue()));
            
            System.out.println("\nSonuçlar 'analiz_sonuclari.txt' dosyasına kaydedildi.");
            
        } catch (Exception e) {
            System.err.println("Hata oluştu: " + e.getMessage());
            e.printStackTrace();
        } finally {
            executorService.shutdown();
            try {
                if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                    executorService.shutdownNow();
                }
            } catch (InterruptedException e) {
                executorService.shutdownNow();
                Thread.currentThread().interrupt();
            }
            scanner.close();
        }
    }
    
    private static List<File> dosyalariBul(String dizinYolu) {
        File dizin = new File(dizinYolu);
        List<File> dosyalar = new ArrayList<>();
        
        if (dizin.exists() && dizin.isDirectory()) {
            File[] dosyaListesi = dizin.listFiles((dir, name) -> name.toLowerCase().endsWith(".txt"));
            if (dosyaListesi != null) {
                dosyalar.addAll(Arrays.asList(dosyaListesi));
            }
        }
        
        return dosyalar;
    }
    
    private static Map<String, Integer> dosyaAnalizEt(File dosya) throws IOException {
        Map<String, Integer> kelimeFrekansi = new ConcurrentHashMap<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(dosya))) {
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
    
    private static void sonuclariKaydet(List<Map.Entry<String, Integer>> sonuclar, String dosyaAdi) 
            throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(dosyaAdi))) {
            writer.println("=== KELİME FREKANS ANALİZİ SONUÇLARI ===");
            writer.println("Tarih: " + new Date());
            writer.println("----------------------------------------");
            
            sonuclar.forEach(entry -> 
                writer.printf("%-20s: %d kez%n", entry.getKey(), entry.getValue()));
        }
    }
} 