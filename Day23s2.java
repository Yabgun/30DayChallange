import java.util.*;

/**
 * Kelime Zinciri Bulmaca Problemi
 * Bu program, verilen bir kelime listesinden en uzun kelime zincirini bulur.
 * Bir kelime zincirinde, her kelimenin son harfi bir sonraki kelimenin ilk harfi olmalıdır.
 * Örnek: kalem -> masa -> armut -> toprak
 */
public class Day23s2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("*** KELİME ZİNCİRİ BULMACA ***");
        System.out.println("Verilen kelime listesinden en uzun kelime zincirini bulun.");
        System.out.println("Bir kelime zincirinde, her kelimenin son harfi bir sonraki kelimenin ilk harfi olmalıdır.");
        
        // Kelime listesini oluştur
        List<String> kelimeListesi = kelimeListesiOlustur();
        
        // Kelime listesini göster
        System.out.println("\nKelime Listesi:");
        for (int i = 0; i < kelimeListesi.size(); i++) {
            System.out.println((i + 1) + ". " + kelimeListesi.get(i));
        }
        
        // En uzun kelime zincirini bul
        List<String> enUzunZincir = enUzunKelimeZinciriniBul(kelimeListesi);
        
        // Sonuçları göster
        System.out.println("\n*** SONUÇLAR ***");
        System.out.println("En uzun kelime zinciri (" + enUzunZincir.size() + " kelime):");
        for (int i = 0; i < enUzunZincir.size(); i++) {
            System.out.print(enUzunZincir.get(i));
            if (i < enUzunZincir.size() - 1) {
                System.out.print(" -> ");
            }
        }
        System.out.println();
        
        // Tüm olası kelime zincirlerini bul
        System.out.println("\nTüm olası kelime zincirleri:");
        List<List<String>> tumZincirler = tumKelimeZincirleriniBul(kelimeListesi);
        
        // Zincirleri uzunluklarına göre sırala (büyükten küçüğe)
        tumZincirler.sort((a, b) -> b.size() - a.size());
        
        // İlk 10 zinciri göster
        int gosterilecekZincirSayisi = Math.min(10, tumZincirler.size());
        for (int i = 0; i < gosterilecekZincirSayisi; i++) {
            List<String> zincir = tumZincirler.get(i);
            System.out.println((i + 1) + ". (" + zincir.size() + " kelime):");
            for (int j = 0; j < zincir.size(); j++) {
                System.out.print(zincir.get(j));
                if (j < zincir.size() - 1) {
                    System.out.print(" -> ");
                }
            }
            System.out.println();
        }
        
        scanner.close();
    }
    
    /**
     * Örnek kelime listesi oluşturan metot
     * Gerçek uygulamada bu liste daha büyük olabilir veya dosyadan okunabilir
     */
    private static List<String> kelimeListesiOlustur() {
        List<String> kelimeler = new ArrayList<>();
        
        // 3 harfli kelimeler
        kelimeler.add("kal");
        kelimeler.add("mas");
        kelimeler.add("arm");
        kelimeler.add("top");
        kelimeler.add("kar");
        kelimeler.add("el");
        kelimeler.add("sa");
        kelimeler.add("ut");
        kelimeler.add("ak");
        kelimeler.add("ra");
        
        // 4 harfli kelimeler
        kelimeler.add("kalem");
        kelimeler.add("masa");
        kelimeler.add("armut");
        kelimeler.add("toprak");
        kelimeler.add("kale");
        kelimeler.add("elma");
        kelimeler.add("sarı");
        kelimeler.add("utku");
        kelimeler.add("akıl");
        kelimeler.add("raki");
        
        // 5 harfli kelimeler
        kelimeler.add("kalem");
        kelimeler.add("masal");
        kelimeler.add("armut");
        kelimeler.add("toprak");
        kelimeler.add("kale");
        kelimeler.add("elma");
        kelimeler.add("sarı");
        kelimeler.add("utku");
        kelimeler.add("akıl");
        kelimeler.add("raki");
        
        return kelimeler;
    }
    
    /**
     * En uzun kelime zincirini bulan metot
     * Dinamik programlama yaklaşımı kullanır
     */
    private static List<String> enUzunKelimeZinciriniBul(List<String> kelimeListesi) {
        // Kelimeleri uzunluklarına göre sırala (küçükten büyüğe)
        List<String> siraliKelimeler = new ArrayList<>(kelimeListesi);
        siraliKelimeler.sort(Comparator.comparingInt(String::length));
        
        // Her kelime için en uzun zincir uzunluğunu tutan dizi
        int[] dp = new int[siraliKelimeler.size()];
        // Her kelime için önceki kelime indeksini tutan dizi
        int[] onceki = new int[siraliKelimeler.size()];
        
        // Başlangıç değerleri
        Arrays.fill(dp, 1);
        Arrays.fill(onceki, -1);
        
        // En uzun zinciri bul
        int enUzunZincirUzunlugu = 1;
        int enUzunZincirSonIndeks = 0;
        
        for (int i = 0; i < siraliKelimeler.size(); i++) {
            for (int j = 0; j < i; j++) {
                // Eğer j. kelimenin son harfi i. kelimenin ilk harfi ise
                if (siraliKelimeler.get(j).charAt(siraliKelimeler.get(j).length() - 1) == 
                    siraliKelimeler.get(i).charAt(0)) {
                    // Eğer j. kelimeye eklenerek daha uzun bir zincir oluşturulabiliyorsa
                    if (dp[j] + 1 > dp[i]) {
                        dp[i] = dp[j] + 1;
                        onceki[i] = j;
                        
                        // En uzun zinciri güncelle
                        if (dp[i] > enUzunZincirUzunlugu) {
                            enUzunZincirUzunlugu = dp[i];
                            enUzunZincirSonIndeks = i;
                        }
                    }
                }
            }
        }
        
        // En uzun zinciri oluştur
        List<String> enUzunZincir = new ArrayList<>();
        int mevcutIndeks = enUzunZincirSonIndeks;
        
        while (mevcutIndeks != -1) {
            enUzunZincir.add(0, siraliKelimeler.get(mevcutIndeks));
            mevcutIndeks = onceki[mevcutIndeks];
        }
        
        return enUzunZincir;
    }
    
    /**
     * Tüm olası kelime zincirlerini bulan metot
     * Derinlik öncelikli arama (DFS) algoritması kullanır
     */
    private static List<List<String>> tumKelimeZincirleriniBul(List<String> kelimeListesi) {
        List<List<String>> tumZincirler = new ArrayList<>();
        
        // Her kelimeyi başlangıç noktası olarak kullan
        for (String kelime : kelimeListesi) {
            List<String> zincir = new ArrayList<>();
            zincir.add(kelime);
            
            // Derinlik öncelikli arama ile tüm zincirleri bul
            dfs(kelimeListesi, kelime, zincir, tumZincirler);
        }
        
        return tumZincirler;
    }
    
    /**
     * Derinlik öncelikli arama (DFS) ile kelime zincirlerini bulan yardımcı metot
     */
    private static void dfs(List<String> kelimeListesi, String sonKelime, 
                           List<String> mevcutZincir, List<List<String>> tumZincirler) {
        // Mevcut zinciri sonuçlara ekle
        tumZincirler.add(new ArrayList<>(mevcutZincir));
        
        // Son kelimenin son harfi ile başlayan tüm kelimeleri bul
        char sonHarf = sonKelime.charAt(sonKelime.length() - 1);
        
        for (String kelime : kelimeListesi) {
            // Eğer kelime daha önce zincire eklenmemişse ve son harf ile başlıyorsa
            if (!mevcutZincir.contains(kelime) && kelime.charAt(0) == sonHarf) {
                // Kelimeyi zincire ekle ve devam et
                mevcutZincir.add(kelime);
                dfs(kelimeListesi, kelime, mevcutZincir, tumZincirler);
                // Geri al (backtrack)
                mevcutZincir.remove(mevcutZincir.size() - 1);
            }
        }
    }
    
    /**
     * İki kelimenin zincir oluşturup oluşturamayacağını kontrol eden yardımcı metot
     */
    private static boolean zincirOlusturabilirMi(String kelime1, String kelime2) {
        return kelime1.charAt(kelime1.length() - 1) == kelime2.charAt(0);
    }
} 