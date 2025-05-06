import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

public class Day52s1 {
    private static Map<String, List<Double>> dersNotlari = new HashMap<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Öğrenci Not Takip Sistemi");
        System.out.println("------------------------");
        
        while (true) {
            System.out.println("\n1. Yeni Ders Ekle");
            System.out.println("2. Not Ekle");
            System.out.println("3. Ders Notlarını Görüntüle");
            System.out.println("4. Ders Ortalamasını Hesapla");
            System.out.println("5. Tüm Derslerin Ortalamasını Görüntüle");
            System.out.println("6. Çıkış");
            System.out.print("Seçiminiz (1-6): ");
            
            int secim = scanner.nextInt();
            scanner.nextLine(); // Buffer temizleme
            
            switch (secim) {
                case 1:
                    yeniDersEkle();
                    break;
                case 2:
                    notEkle();
                    break;
                case 3:
                    dersNotlariniGoster();
                    break;
                case 4:
                    dersOrtalamasiHesapla();
                    break;
                case 5:
                    tumDerslerinOrtalamasi();
                    break;
                case 6:
                    System.out.println("Program sonlandırılıyor...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Geçersiz seçim! Lütfen tekrar deneyin.");
            }
        }
    }

    private static void yeniDersEkle() {
        System.out.print("Ders adını girin: ");
        String dersAdi = scanner.nextLine();
        
        if (dersNotlari.containsKey(dersAdi)) {
            System.out.println("Bu ders zaten eklenmiş!");
            return;
        }
        
        dersNotlari.put(dersAdi, new ArrayList<>());
        System.out.println(dersAdi + " dersi başarıyla eklendi!");
    }

    private static void notEkle() {
        if (dersNotlari.isEmpty()) {
            System.out.println("Henüz ders eklenmemiş!");
            return;
        }

        System.out.println("\nMevcut Dersler:");
        for (String ders : dersNotlari.keySet()) {
            System.out.println("- " + ders);
        }
        
        System.out.print("\nNot eklemek istediğiniz dersin adını girin: ");
        String dersAdi = scanner.nextLine();
        
        if (!dersNotlari.containsKey(dersAdi)) {
            System.out.println("Böyle bir ders bulunamadı!");
            return;
        }
        
        System.out.print("Not değerini girin (0-100): ");
        double not = scanner.nextDouble();
        scanner.nextLine();
        
        if (not < 0 || not > 100) {
            System.out.println("Geçersiz not değeri! Not 0-100 arasında olmalıdır.");
            return;
        }
        
        dersNotlari.get(dersAdi).add(not);
        System.out.println("Not başarıyla eklendi!");
    }

    private static void dersNotlariniGoster() {
        if (dersNotlari.isEmpty()) {
            System.out.println("Henüz ders eklenmemiş!");
            return;
        }

        System.out.println("\nDers Notları:");
        System.out.println("-------------");
        
        for (Map.Entry<String, List<Double>> entry : dersNotlari.entrySet()) {
            System.out.println("\n" + entry.getKey() + ":");
            if (entry.getValue().isEmpty()) {
                System.out.println("  Henüz not girilmemiş");
            } else {
                for (int i = 0; i < entry.getValue().size(); i++) {
                    System.out.printf("  %d. Not: %.2f\n", i + 1, entry.getValue().get(i));
                }
            }
        }
    }

    private static void dersOrtalamasiHesapla() {
        if (dersNotlari.isEmpty()) {
            System.out.println("Henüz ders eklenmemiş!");
            return;
        }

        System.out.println("\nMevcut Dersler:");
        for (String ders : dersNotlari.keySet()) {
            System.out.println("- " + ders);
        }
        
        System.out.print("\nOrtalamasını hesaplamak istediğiniz dersin adını girin: ");
        String dersAdi = scanner.nextLine();
        
        if (!dersNotlari.containsKey(dersAdi)) {
            System.out.println("Böyle bir ders bulunamadı!");
            return;
        }
        
        List<Double> notlar = dersNotlari.get(dersAdi);
        if (notlar.isEmpty()) {
            System.out.println("Bu derse henüz not girilmemiş!");
            return;
        }
        
        double toplam = 0;
        for (double not : notlar) {
            toplam += not;
        }
        
        double ortalama = toplam / notlar.size();
        System.out.printf("\n%s dersinin ortalaması: %.2f\n", dersAdi, ortalama);
        
        // Harf notu hesaplama
        String harfNotu = harfNotuHesapla(ortalama);
        System.out.println("Harf Notu: " + harfNotu);
    }

    private static void tumDerslerinOrtalamasi() {
        if (dersNotlari.isEmpty()) {
            System.out.println("Henüz ders eklenmemiş!");
            return;
        }

        System.out.println("\nTüm Derslerin Ortalaması:");
        System.out.println("------------------------");
        
        double genelToplam = 0;
        int toplamNotSayisi = 0;
        
        for (Map.Entry<String, List<Double>> entry : dersNotlari.entrySet()) {
            List<Double> notlar = entry.getValue();
            if (!notlar.isEmpty()) {
                double dersToplam = 0;
                for (double not : notlar) {
                    dersToplam += not;
                }
                double dersOrtalama = dersToplam / notlar.size();
                System.out.printf("%s: %.2f\n", entry.getKey(), dersOrtalama);
                
                genelToplam += dersToplam;
                toplamNotSayisi += notlar.size();
            }
        }
        
        if (toplamNotSayisi > 0) {
            double genelOrtalama = genelToplam / toplamNotSayisi;
            System.out.println("------------------------");
            System.out.printf("Genel Ortalama: %.2f\n", genelOrtalama);
            System.out.println("Genel Harf Notu: " + harfNotuHesapla(genelOrtalama));
        }
    }

    private static String harfNotuHesapla(double ortalama) {
        if (ortalama >= 90) return "AA";
        else if (ortalama >= 80) return "BA";
        else if (ortalama >= 70) return "BB";
        else if (ortalama >= 60) return "CB";
        else if (ortalama >= 50) return "CC";
        else if (ortalama >= 40) return "DC";
        else if (ortalama >= 30) return "DD";
        else return "FF";
    }
} 