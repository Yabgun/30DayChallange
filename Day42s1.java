import java.util.Scanner;
import java.util.Random;

public class Day42s1 {
    public static void main(String[] args) {
        TasKagitMakas oyun = new TasKagitMakas();
        oyun.baslat();
    }
}

class TasKagitMakas {
    private Scanner scanner;
    private Random random;
    private int kullaniciPuan;
    private int bilgisayarPuan;
    private int beraberlik;
    private int turSayisi;

    public TasKagitMakas() {
        scanner = new Scanner(System.in);
        random = new Random();
        kullaniciPuan = 0;
        bilgisayarPuan = 0;
        beraberlik = 0;
        turSayisi = 0;
    }

    public void baslat() {
        System.out.println("=== TAŞ KAĞIT MAKAS OYUNUNA HOŞ GELDİNİZ ===");
        System.out.println("Oyun kuralları:");
        System.out.println("- Taş makası yener");
        System.out.println("- Kağıt taşı yener");
        System.out.println("- Makas kağıdı yener");
        System.out.println();

        boolean devamEt = true;
        while (devamEt) {
            oyunTuru();
            
            System.out.print("\nDevam etmek istiyor musunuz? (E/H): ");
            String cevap = scanner.nextLine().trim().toUpperCase();
            devamEt = cevap.equals("E");
            
            if (!devamEt) {
                sonuclariGoster();
            }
        }
    }

    private void oyunTuru() {
        turSayisi++;
        System.out.println("\n=== TUR " + turSayisi + " ===");
        
        System.out.println("Seçiminizi yapın:");
        System.out.println("1 - Taş");
        System.out.println("2 - Kağıt");
        System.out.println("3 - Makas");
        
        int kullaniciSecimi = 0;
        boolean gecerliSecim = false;
        
        while (!gecerliSecim) {
            System.out.print("Seçiminiz (1-3): ");
            try {
                kullaniciSecimi = Integer.parseInt(scanner.nextLine().trim());
                if (kullaniciSecimi >= 1 && kullaniciSecimi <= 3) {
                    gecerliSecim = true;
                } else {
                    System.out.println("Lütfen 1 ile 3 arasında bir sayı girin.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Lütfen geçerli bir sayı girin.");
            }
        }
        
        int bilgisayarSecimi = random.nextInt(3) + 1; // 1, 2 veya 3
        
        System.out.println("\nSizin seçiminiz: " + secimToString(kullaniciSecimi));
        System.out.println("Bilgisayarın seçimi: " + secimToString(bilgisayarSecimi));
        
        String sonuc = kazananBelirle(kullaniciSecimi, bilgisayarSecimi);
        System.out.println(sonuc);
        
        puanGuncelle(sonuc);
        puanTablosuGoster();
    }
    
    private String secimToString(int secim) {
        switch (secim) {
            case 1: return "Taş";
            case 2: return "Kağıt";
            case 3: return "Makas";
            default: return "Geçersiz";
        }
    }
    
    private String kazananBelirle(int kullanici, int bilgisayar) {
        if (kullanici == bilgisayar) {
            return "Beraberlik!";
        }
        
        // Taş(1) Makası(3) yener, Kağıt(2) Taşı(1) yener, Makas(3) Kağıdı(2) yener
        if ((kullanici == 1 && bilgisayar == 3) || 
            (kullanici == 2 && bilgisayar == 1) || 
            (kullanici == 3 && bilgisayar == 2)) {
            return "Siz kazandınız!";
        } else {
            return "Bilgisayar kazandı!";
        }
    }
    
    private void puanGuncelle(String sonuc) {
        switch (sonuc) {
            case "Siz kazandınız!":
                kullaniciPuan++;
                break;
            case "Bilgisayar kazandı!":
                bilgisayarPuan++;
                break;
            case "Beraberlik!":
                beraberlik++;
                break;
        }
    }
    
    private void puanTablosuGoster() {
        System.out.println("\n--- PUAN DURUMU ---");
        System.out.println("Sizin puanınız: " + kullaniciPuan);
        System.out.println("Bilgisayarın puanı: " + bilgisayarPuan);
        System.out.println("Beraberlik: " + beraberlik);
    }
    
    private void sonuclariGoster() {
        System.out.println("\n=== OYUN SONU ===");
        System.out.println("Toplam oynanan tur: " + turSayisi);
        System.out.println("Sizin toplam puanınız: " + kullaniciPuan);
        System.out.println("Bilgisayarın toplam puanı: " + bilgisayarPuan);
        System.out.println("Toplam beraberlik: " + beraberlik);
        
        System.out.println("\nSonuç: ");
        if (kullaniciPuan > bilgisayarPuan) {
            System.out.println("Tebrikler! Oyunu kazandınız!");
        } else if (bilgisayarPuan > kullaniciPuan) {
            System.out.println("Üzgünüm, bilgisayar kazandı!");
        } else {
            System.out.println("Oyun berabere bitti!");
        }
        
        // Oyun istatistikleri
        double kullaniciKazanmaOrani = (double) kullaniciPuan / turSayisi * 100;
        double bilgisayarKazanmaOrani = (double) bilgisayarPuan / turSayisi * 100;
        double beraberlikOrani = (double) beraberlik / turSayisi * 100;
        
        System.out.println("\n--- İSTATİSTİKLER ---");
        System.out.printf("Sizin kazanma oranınız: %.1f%%\n", kullaniciKazanmaOrani);
        System.out.printf("Bilgisayarın kazanma oranı: %.1f%%\n", bilgisayarKazanmaOrani);
        System.out.printf("Beraberlik oranı: %.1f%%\n", beraberlikOrani);
    }
} 