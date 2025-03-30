import java.util.Scanner;
import java.util.Random;

/**
 * Kelime Tahmin Oyunu
 * Bilgisayarın seçtiği kelimeyi harf harf tahmin etmeye çalışan bir oyun.
 */
public class Day17s1 {
    private static final String[] KELIMELER = {
        "JAVA", "PROGRAMLAMA", "BILGISAYAR", "ALGORITMA", "VERITABANI",
        "INTERNET", "YAZILIM", "GELISTIRICI", "KODLAMA", "TEKNOLOJI"
    };
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        
        System.out.println("*** KELİME TAHMİN OYUNU ***");
        System.out.println("Bilgisayarın seçtiği kelimeyi harf harf tahmin etmeye çalışın!");
        
        while (true) {
            // Yeni oyun başlat
            String secilenKelime = KELIMELER[random.nextInt(KELIMELER.length)];
            char[] gizliKelime = new char[secilenKelime.length()];
            boolean[] tahminEdilenHarfler = new boolean[secilenKelime.length()];
            
            // Gizli kelimeyi oluştur
            for (int i = 0; i < secilenKelime.length(); i++) {
                gizliKelime[i] = '_';
            }
            
            int kalanHak = 6; // Adam asmaca tarzı 6 hak
            boolean oyunBitti = false;
            
            while (!oyunBitti && kalanHak > 0) {
                // Mevcut durumu göster
                System.out.println("\nKalan Hak: " + kalanHak);
                System.out.println("Kelime: " + new String(gizliKelime));
                
                // Kullanıcıdan tahmin al
                System.out.print("Bir harf tahmin edin: ");
                String tahmin = scanner.nextLine().toUpperCase();
                
                if (tahmin.length() != 1 || !Character.isLetter(tahmin.charAt(0))) {
                    System.out.println("Lütfen geçerli bir harf girin!");
                    continue;
                }
                
                char tahminHarf = tahmin.charAt(0);
                boolean harfBulundu = false;
                
                // Tahmin edilen harfi kontrol et
                for (int i = 0; i < secilenKelime.length(); i++) {
                    if (secilenKelime.charAt(i) == tahminHarf && !tahminEdilenHarfler[i]) {
                        gizliKelime[i] = tahminHarf;
                        tahminEdilenHarfler[i] = true;
                        harfBulundu = true;
                    }
                }
                
                // Sonuçları kontrol et
                if (!harfBulundu) {
                    kalanHak--;
                    System.out.println("Yanlış tahmin! Kalan hak: " + kalanHak);
                } else {
                    System.out.println("Doğru tahmin!");
                }
                
                // Oyunun bitip bitmediğini kontrol et
                oyunBitti = true;
                for (boolean tahminEdildi : tahminEdilenHarfler) {
                    if (!tahminEdildi) {
                        oyunBitti = false;
                        break;
                    }
                }
            }
            
            // Oyun sonucunu göster
            System.out.println("\n=== OYUN SONU ===");
            if (oyunBitti) {
                System.out.println("Tebrikler! Kelimeyi doğru tahmin ettiniz!");
                System.out.println("Kelime: " + secilenKelime);
            } else {
                System.out.println("Üzgünüm, hakkınız bitti!");
                System.out.println("Doğru kelime: " + secilenKelime);
            }
            
            // Yeni oyun oynamak isteyip istemediğini sor
            System.out.print("\nYeni oyun oynamak ister misiniz? (E/H): ");
            String cevap = scanner.nextLine().toUpperCase();
            if (!cevap.equals("E")) {
                System.out.println("Oyun sonlandırılıyor. İyi günler!");
                break;
            }
        }
        
        scanner.close();
    }
} 