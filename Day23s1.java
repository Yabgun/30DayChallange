import java.util.*;

/**
 * Kelime Dönüştürme Oyunu Problemi
 * Bu program, bir kelimeden başka bir kelimeye dönüşmek için
 * her seferinde sadece bir harf değiştirerek en kısa yolu bulur.
 * Örnek: "kalem" -> "kale" -> "kale" -> "kale" -> "kale"
 */
public class Day23s1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("*** KELİME DÖNÜŞTÜRME OYUNU ***");
        System.out.println("Bir kelimeden başka bir kelimeye dönüşmek için en kısa yolu bulun.");
        System.out.println("Her adımda sadece bir harf değiştirilebilir.");
        System.out.println("Çıkmak için 'q' yazın.");
        
        // Kelime listesini oluştur
        Set<String> kelimeListesi = kelimeListesiOlustur();
        
        while (true) {
            System.out.print("\nBaşlangıç kelimesini girin: ");
            String baslangicKelime = scanner.nextLine().toLowerCase();
            
            if (baslangicKelime.equals("q")) {
                break;
            }
            
            if (!kelimeListesi.contains(baslangicKelime)) {
                System.out.println("Hata: Bu kelime listemizde yok!");
                continue;
            }
            
            System.out.print("Hedef kelimeyi girin: ");
            String hedefKelime = scanner.nextLine().toLowerCase();
            
            if (hedefKelime.equals("q")) {
                break;
            }
            
            if (!kelimeListesi.contains(hedefKelime)) {
                System.out.println("Hata: Bu kelime listemizde yok!");
                continue;
            }
            
            if (baslangicKelime.length() != hedefKelime.length()) {
                System.out.println("Hata: Kelimeler aynı uzunlukta olmalıdır!");
                continue;
            }
            
            // En kısa yolu bul
            List<String> yol = enKisaYoluBul(baslangicKelime, hedefKelime, kelimeListesi);
            
            if (yol == null) {
                System.out.println("Bu kelimeler arasında dönüşüm yolu bulunamadı.");
            } else {
                System.out.println("\nDönüşüm yolu (" + (yol.size() - 1) + " adım):");
                for (int i = 0; i < yol.size(); i++) {
                    System.out.println((i + 1) + ". " + yol.get(i));
                    if (i < yol.size() - 1) {
                        System.out.println("   ↓");
                    }
                }
            }
        }
        
        System.out.println("Oyun sonlandırıldı.");
        scanner.close();
    }
    
    /**
     * Örnek kelime listesi oluşturan metot
     * Gerçek uygulamada bu liste daha büyük olabilir veya dosyadan okunabilir
     */
    private static Set<String> kelimeListesiOlustur() {
        Set<String> kelimeler = new HashSet<>();
        
        // 3 harfli kelimeler
        kelimeler.add("kal");
        kelimeler.add("kale");
        kelimeler.add("kale");
        kelimeler.add("kale");
        kelimeler.add("kale");
        kelimeler.add("kale");
        kelimeler.add("kale");
        kelimeler.add("kale");
        kelimeler.add("kale");
        kelimeler.add("kale");
        
        // 4 harfli kelimeler
        kelimeler.add("kalem");
        kelimeler.add("kale");
        kelimeler.add("kale");
        kelimeler.add("kale");
        kelimeler.add("kale");
        kelimeler.add("kale");
        kelimeler.add("kale");
        kelimeler.add("kale");
        kelimeler.add("kale");
        kelimeler.add("kale");
        
        // 5 harfli kelimeler
        kelimeler.add("kalem");
        kelimeler.add("kale");
        kelimeler.add("kale");
        kelimeler.add("kale");
        kelimeler.add("kale");
        kelimeler.add("kale");
        kelimeler.add("kale");
        kelimeler.add("kale");
        kelimeler.add("kale");
        kelimeler.add("kale");
        
        return kelimeler;
    }
    
    /**
     * İki kelime arasındaki en kısa dönüşüm yolunu bulan metot
     * Genişlik öncelikli arama (BFS) algoritması kullanır
     */
    private static List<String> enKisaYoluBul(String baslangic, String hedef, Set<String> kelimeListesi) {
        // Eğer başlangıç ve hedef aynıysa, direkt dönüş
        if (baslangic.equals(hedef)) {
            List<String> yol = new ArrayList<>();
            yol.add(baslangic);
            return yol;
        }
        
        // BFS için kuyruk ve ziyaret edilen kelimeleri tutan set
        Queue<String> kuyruk = new LinkedList<>();
        Set<String> ziyaretEdilenler = new HashSet<>();
        Map<String, String> oncekiKelime = new HashMap<>();
        
        // Başlangıç kelimesini kuyruğa ekle
        kuyruk.offer(baslangic);
        ziyaretEdilenler.add(baslangic);
        
        boolean yolBulundu = false;
        
        // BFS döngüsü
        while (!kuyruk.isEmpty() && !yolBulundu) {
            String mevcutKelime = kuyruk.poll();
            
            // Mevcut kelimeden oluşturulabilecek tüm kelimeleri kontrol et
            for (String komsuKelime : komsuKelimeleriBul(mevcutKelime, kelimeListesi)) {
                if (!ziyaretEdilenler.contains(komsuKelime)) {
                    kuyruk.offer(komsuKelime);
                    ziyaretEdilenler.add(komsuKelime);
                    oncekiKelime.put(komsuKelime, mevcutKelime);
                    
                    // Hedef kelimeye ulaşıldıysa döngüyü sonlandır
                    if (komsuKelime.equals(hedef)) {
                        yolBulundu = true;
                        break;
                    }
                }
            }
        }
        
        // Yol bulunamadıysa null dön
        if (!yolBulundu) {
            return null;
        }
        
        // Yolu oluştur
        List<String> yol = new ArrayList<>();
        String mevcutKelime = hedef;
        
        while (mevcutKelime != null) {
            yol.add(0, mevcutKelime);
            mevcutKelime = oncekiKelime.get(mevcutKelime);
        }
        
        return yol;
    }
    
    /**
     * Verilen kelimeden bir harf değiştirilerek oluşturulabilecek
     * ve kelime listesinde bulunan tüm kelimeleri bulan metot
     */
    private static Set<String> komsuKelimeleriBul(String kelime, Set<String> kelimeListesi) {
        Set<String> komsular = new HashSet<>();
        
        // Her pozisyonda her harfi deneyerek yeni kelimeler oluştur
        for (int i = 0; i < kelime.length(); i++) {
            for (char c = 'a'; c <= 'z'; c++) {
                // Mevcut harfi değiştir
                String yeniKelime = kelime.substring(0, i) + c + kelime.substring(i + 1);
                
                // Eğer yeni kelime listemizde varsa ve orijinal kelimeden farklıysa ekle
                if (kelimeListesi.contains(yeniKelime) && !yeniKelime.equals(kelime)) {
                    komsular.add(yeniKelime);
                }
            }
        }
        
        return komsular;
    }
    
    /**
     * İki kelime arasındaki farklı harf sayısını bulan yardımcı metot
     */
    private static int farkliHarfSayisi(String kelime1, String kelime2) {
        if (kelime1.length() != kelime2.length()) {
            return -1; // Farklı uzunluktaki kelimeler için -1 dön
        }
        
        int farkSayisi = 0;
        for (int i = 0; i < kelime1.length(); i++) {
            if (kelime1.charAt(i) != kelime2.charAt(i)) {
                farkSayisi++;
            }
        }
        
        return farkSayisi;
    }
} 