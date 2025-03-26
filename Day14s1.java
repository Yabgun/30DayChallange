import java.util.Scanner;

/**
 * Sayı Tahmin Oyunu
 * Bilgisayarın 1-100 arasında tuttuğu rastgele sayıyı tahmin etmeye çalışan basit bir oyun.
 */
public class Day14s1 {
    public static void main(String[] args) {
        // Rastgele sayı üret (1-100 arası)
        int rastgeleSayi = (int) (Math.random() * 100) + 1;
        int tahmin = 0;
        int denemeSayisi = 0;
        boolean dogruTahmin = false;
        
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("*** SAYI TAHMİN OYUNU ***");
        System.out.println("1 ile 100 arasında bir sayı tuttum. Tahmin edebilir misin?");
        
        // Doğru tahmin edilene kadar devam et
        while (!dogruTahmin) {
            System.out.print("Tahmininiz: ");
            
            // Geçerli bir sayı girilip girilmediğini kontrol et
            if (scanner.hasNextInt()) {
                tahmin = scanner.nextInt();
                denemeSayisi++;
                
                // Tahminleri kontrol et
                if (tahmin < 1 || tahmin > 100) {
                    System.out.println("Lütfen 1 ile 100 arasında bir sayı girin!");
                } else if (tahmin < rastgeleSayi) {
                    System.out.println("Daha BÜYÜK bir sayı girin!");
                } else if (tahmin > rastgeleSayi) {
                    System.out.println("Daha KÜÇÜK bir sayı girin!");
                } else {
                    dogruTahmin = true;
                    System.out.println("\nTEBRİKLER! " + denemeSayisi + " denemede doğru tahmin ettiniz.");
                    
                    // Performans değerlendirmesi
                    if (denemeSayisi <= 3) {
                        System.out.println("Şanslı günündesin! Mükemmel!");
                    } else if (denemeSayisi <= 7) {
                        System.out.println("Çok iyi bir skor!");
                    } else if (denemeSayisi <= 10) {
                        System.out.println("İyi bir skor.");
                    } else {
                        System.out.println("Bir dahaki sefere daha iyi olabilir.");
                    }
                }
            } else {
                // Geçersiz giriş
                System.out.println("Lütfen geçerli bir sayı girin!");
                scanner.next(); // Geçersiz girişi temizle
            }
        }
        
        scanner.close();
    }
} 