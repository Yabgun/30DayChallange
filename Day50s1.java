import java.util.Scanner;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Day50s1 {
    private static Map<String, ArrayList<String>> hedefler = new HashMap<>();
    private static Map<String, ArrayList<String>> tamamlananlar = new HashMap<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Günlük Aktivite ve Hedef Takip Sistemi");
        System.out.println("-------------------------------------");
        
        while (true) {
            System.out.println("\n1. Yeni Hedef Ekle");
            System.out.println("2. Hedefleri Görüntüle");
            System.out.println("3. Hedef Tamamla");
            System.out.println("4. Tamamlanan Hedefleri Görüntüle");
            System.out.println("5. Çıkış");
            System.out.print("Seçiminiz (1-5): ");
            
            int secim = scanner.nextInt();
            scanner.nextLine(); // Buffer temizleme
            
            switch (secim) {
                case 1:
                    hedefEkle();
                    break;
                case 2:
                    hedefleriGoster();
                    break;
                case 3:
                    hedefTamamla();
                    break;
                case 4:
                    tamamlananlariGoster();
                    break;
                case 5:
                    System.out.println("Program sonlandırılıyor...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Geçersiz seçim! Lütfen tekrar deneyin.");
            }
        }
    }

    private static void hedefEkle() {
        System.out.print("Hedef kategorisi girin (örn: Spor, Okuma, İş): ");
        String kategori = scanner.nextLine();
        
        System.out.print("Hedef açıklaması girin: ");
        String hedef = scanner.nextLine();
        
        if (!hedefler.containsKey(kategori)) {
            hedefler.put(kategori, new ArrayList<>());
        }
        
        hedefler.get(kategori).add(hedef);
        System.out.println("Hedef başarıyla eklendi!");
    }

    private static void hedefleriGoster() {
        if (hedefler.isEmpty()) {
            System.out.println("Henüz hedef eklenmemiş!");
            return;
        }

        System.out.println("\nMevcut Hedefler:");
        System.out.println("----------------");
        for (Map.Entry<String, ArrayList<String>> entry : hedefler.entrySet()) {
            System.out.println("\nKategori: " + entry.getKey());
            for (int i = 0; i < entry.getValue().size(); i++) {
                System.out.println((i + 1) + ". " + entry.getValue().get(i));
            }
        }
    }

    private static void hedefTamamla() {
        if (hedefler.isEmpty()) {
            System.out.println("Tamamlanacak hedef bulunamadı!");
            return;
        }

        hedefleriGoster();
        System.out.print("\nTamamlanan hedefin kategorisini girin: ");
        String kategori = scanner.nextLine();
        
        if (!hedefler.containsKey(kategori)) {
            System.out.println("Geçersiz kategori!");
            return;
        }

        System.out.print("Tamamlanan hedefin numarasını girin: ");
        int hedefNo = scanner.nextInt();
        scanner.nextLine();

        if (hedefNo < 1 || hedefNo > hedefler.get(kategori).size()) {
            System.out.println("Geçersiz hedef numarası!");
            return;
        }

        String tamamlananHedef = hedefler.get(kategori).remove(hedefNo - 1);
        
        if (!tamamlananlar.containsKey(kategori)) {
            tamamlananlar.put(kategori, new ArrayList<>());
        }
        
        tamamlananlar.get(kategori).add(tamamlananHedef + " - " + LocalDate.now());
        System.out.println("Hedef başarıyla tamamlandı!");
    }

    private static void tamamlananlariGoster() {
        if (tamamlananlar.isEmpty()) {
            System.out.println("Henüz tamamlanan hedef bulunmuyor!");
            return;
        }

        System.out.println("\nTamamlanan Hedefler:");
        System.out.println("-------------------");
        for (Map.Entry<String, ArrayList<String>> entry : tamamlananlar.entrySet()) {
            System.out.println("\nKategori: " + entry.getKey());
            for (String hedef : entry.getValue()) {
                System.out.println("- " + hedef);
            }
        }
    }
} 