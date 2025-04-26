import java.util.Scanner;
import java.util.Random;

public class Day43s1 {
    public static void main(String[] args) {
        SayiTahminOyunu oyun = new SayiTahminOyunu();
        oyun.baslat();
    }
}

class SayiTahminOyunu {
    private Scanner scanner;
    private Random random;
    private int minSayi;
    private int maxSayi;
    private int maxTahmin;
    private int toplamOyun;
    private int kazanilanOyun;
    private int enIyiTahmin;
    
    public SayiTahminOyunu() {
        scanner = new Scanner(System.in);
        random = new Random();
        minSayi = 1;
        maxSayi = 100;
        maxTahmin = 10;
        toplamOyun = 0;
        kazanilanOyun = 0;
        enIyiTahmin = Integer.MAX_VALUE;
    }
    
    public void baslat() {
        System.out.println("=== SAYI TAHMİN OYUNUNA HOŞ GELDİNİZ ===");
        System.out.println("Oyun kuralları:");
        System.out.println("- Bilgisayar " + minSayi + " ile " + maxSayi + " arasında bir sayı tutar");
        System.out.println("- Sizin " + maxTahmin + " hakkınız var");
        System.out.println("- Her tahmininizden sonra bilgisayar, tuttuğu sayının");
        System.out.println("  tahmin ettiğiniz sayıdan büyük veya küçük olduğunu söyler");
        System.out.println();
        
        boolean devamEt = true;
        while (devamEt) {
            oyunTuru();
            
            System.out.print("\nYeni bir oyun oynamak istiyor musunuz? (E/H): ");
            String cevap = scanner.nextLine().trim().toUpperCase();
            devamEt = cevap.equals("E");
            
            if (!devamEt) {
                sonuclariGoster();
            }
        }
        
        scanner.close();
    }
    
    private void oyunTuru() {
        toplamOyun++;
        int hedefSayi = random.nextInt(maxSayi - minSayi + 1) + minSayi;
        int kalanTahmin = maxTahmin;
        boolean dogruTahmin = false;
        int yapılanTahmin = 0;
        
        System.out.println("\n=== OYUN " + toplamOyun + " ===");
        System.out.println(minSayi + " ile " + maxSayi + " arasında bir sayı tuttum.");
        
        while (kalanTahmin > 0 && !dogruTahmin) {
            System.out.println("\nKalan tahmin hakkınız: " + kalanTahmin);
            int tahmin = sayiAl(minSayi, maxSayi);
            yapılanTahmin++;
            kalanTahmin--;
            
            if (tahmin == hedefSayi) {
                System.out.println("Tebrikler! Doğru tahmin ettiniz: " + hedefSayi);
                dogruTahmin = true;
                kazanilanOyun++;
                
                // En iyi skor güncelleme
                if (yapılanTahmin < enIyiTahmin) {
                    enIyiTahmin = yapılanTahmin;
                    System.out.println("Bu yeni bir rekor! En az tahmin sayısı: " + enIyiTahmin);
                }
            } else if (kalanTahmin == 0) {
                System.out.println("Tahmin hakkınız bitti. Doğru sayı " + hedefSayi + " idi.");
            } else if (tahmin < hedefSayi) {
                System.out.println("Çok küçük! Daha büyük bir sayı girin.");
            } else {
                System.out.println("Çok büyük! Daha küçük bir sayı girin.");
            }
        }
    }
    
    private int sayiAl(int min, int max) {
        int sayi = 0;
        boolean gecerliGiris = false;
        
        while (!gecerliGiris) {
            System.out.print("Tahmininizi girin (" + min + "-" + max + "): ");
            try {
                sayi = Integer.parseInt(scanner.nextLine().trim());
                if (sayi >= min && sayi <= max) {
                    gecerliGiris = true;
                } else {
                    System.out.println("Lütfen " + min + " ile " + max + " arasında bir sayı girin.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Lütfen geçerli bir sayı girin.");
            }
        }
        
        return sayi;
    }
    
    private void sonuclariGoster() {
        System.out.println("\n=== OYUN İSTATİSTİKLERİ ===");
        System.out.println("Toplam oynanan oyun: " + toplamOyun);
        System.out.println("Kazanılan oyun: " + kazanilanOyun);
        System.out.println("Kaybedilen oyun: " + (toplamOyun - kazanilanOyun));
        
        if (kazanilanOyun > 0) {
            System.out.println("En iyi tahmin sayınız: " + enIyiTahmin);
            double basariOrani = (double) kazanilanOyun / toplamOyun * 100;
            System.out.printf("Başarı oranınız: %.2f%%\n", basariOrani);
        }
        
        // Oyuncu değerlendirmesi
        if (toplamOyun > 0) {
            System.out.println("\n--- DEĞERLENDİRME ---");
            if (kazanilanOyun == 0) {
                System.out.println("Hiç kazanamadınız! Biraz daha pratik yapmanız gerekiyor.");
            } else if ((double) kazanilanOyun / toplamOyun < 0.3) {
                System.out.println("Daha fazla pratik yapmanız gerekiyor.");
            } else if ((double) kazanilanOyun / toplamOyun < 0.7) {
                System.out.println("İyi gidiyorsunuz, devam edin!");
            } else {
                System.out.println("Harikasınız! Siz bir tahmin ustasısınız!");
            }
        }
        
        System.out.println("\nOynadığınız için teşekkür ederiz!");
    }
} 