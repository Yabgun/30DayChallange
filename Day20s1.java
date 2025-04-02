import java.util.*;

/**
 * Kelime Zinciri Oyunu
 * Bu program, kullanıcıdan kelimeler alır ve her kelimenin son harfi,
 * bir sonraki kelimenin ilk harfi olmalıdır.
 * Oyun, geçersiz bir kelime girildiğinde veya kullanıcı çıkmak istediğinde biter.
 */
public class Day20s1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<String> kelimeZinciri = new ArrayList<>();
        
        System.out.println("*** KELİME ZİNCİRİ OYUNU ***");
        System.out.println("Her kelimenin son harfi, bir sonraki kelimenin ilk harfi olmalıdır.");
        System.out.println("Çıkmak için 'q' yazın.");
        
        // İlk kelimeyi al
        System.out.print("\nİlk kelimeyi girin: ");
        String ilkKelime = scanner.nextLine().toLowerCase();
        
        if (ilkKelime.equals("q")) {
            System.out.println("Oyun sonlandırıldı.");
            return;
        }
        
        kelimeZinciri.add(ilkKelime);
        char sonHarf = ilkKelime.charAt(ilkKelime.length() - 1);
        
        while (true) {
            System.out.print("\nSon harf '" + sonHarf + "' ile başlayan bir kelime girin: ");
            String yeniKelime = scanner.nextLine().toLowerCase();
            
            if (yeniKelime.equals("q")) {
                break;
            }
            
            // Kelime kontrolü
            if (yeniKelime.charAt(0) != sonHarf) {
                System.out.println("Hata: Kelime '" + sonHarf + "' harfi ile başlamalı!");
                continue;
            }
            
            if (kelimeZinciri.contains(yeniKelime)) {
                System.out.println("Hata: Bu kelime zaten kullanıldı!");
                continue;
            }
            
            kelimeZinciri.add(yeniKelime);
            sonHarf = yeniKelime.charAt(yeniKelime.length() - 1);
            
            // Kelime zincirini göster
            System.out.println("\nKelime Zinciri:");
            for (int i = 0; i < kelimeZinciri.size(); i++) {
                System.out.print(kelimeZinciri.get(i));
                if (i < kelimeZinciri.size() - 1) {
                    System.out.print(" -> ");
                }
            }
            System.out.println();
        }
        
        // Oyun sonu istatistikleri
        System.out.println("\n*** OYUN SONU ***");
        System.out.println("Toplam kelime sayısı: " + kelimeZinciri.size());
        System.out.println("Kelime zinciri:");
        for (int i = 0; i < kelimeZinciri.size(); i++) {
            System.out.println((i + 1) + ". " + kelimeZinciri.get(i));
        }
        
        scanner.close();
    }
} 