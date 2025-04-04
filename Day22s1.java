import java.util.*;

/**
 * Kelime Matrisi Bulmaca Problemi
 * Bu program, verilen bir matris içinde kelimeleri bulur.
 * Kelimeler yatay, dikey veya çapraz olabilir.
 * Aynı kelime birden fazla kez bulunabilir.
 */
public class Day22s1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("*** KELİME MATRİSİ BULMACA ***");
        
        // Matris boyutlarını al
        System.out.print("Matris satır sayısını girin: ");
        int satir = scanner.nextInt();
        System.out.print("Matris sütun sayısını girin: ");
        int sutun = scanner.nextInt();
        
        // Matrisi oluştur
        char[][] matris = new char[satir][sutun];
        
        // Matrisi doldur
        System.out.println("\nMatrisi karakter karakter girin:");
        for (int i = 0; i < satir; i++) {
            for (int j = 0; j < sutun; j++) {
                matris[i][j] = scanner.next().charAt(0);
            }
        }
        
        // Matrisi göster
        System.out.println("\nMatris:");
        matrisiGoster(matris);
        
        // Aranacak kelimeleri al
        System.out.print("\nKaç kelime aramak istiyorsunuz? ");
        int kelimeSayisi = scanner.nextInt();
        
        List<String> aranacakKelimeler = new ArrayList<>();
        for (int i = 0; i < kelimeSayisi; i++) {
            System.out.print((i + 1) + ". kelimeyi girin: ");
            aranacakKelimeler.add(scanner.next().toLowerCase());
        }
        
        // Kelimeleri bul ve sonuçları göster
        System.out.println("\n*** SONUÇLAR ***");
        for (String kelime : aranacakKelimeler) {
            List<BulunanKelime> sonuclar = kelimeyiBul(matris, kelime);
            
            if (sonuclar.isEmpty()) {
                System.out.println("'" + kelime + "' kelimesi matriste bulunamadı.");
            } else {
                System.out.println("'" + kelime + "' kelimesi " + sonuclar.size() + " kez bulundu:");
                for (BulunanKelime sonuc : sonuclar) {
                    System.out.println("- " + sonuc.yon + " yönünde, başlangıç: (" + 
                                      (sonuc.baslangicSatir + 1) + "," + (sonuc.baslangicSutun + 1) + ")");
                }
            }
        }
        
        scanner.close();
    }
    
    /**
     * Matrisi ekrana yazdıran metot
     */
    public static void matrisiGoster(char[][] matris) {
        for (int i = 0; i < matris.length; i++) {
            for (int j = 0; j < matris[i].length; j++) {
                System.out.print(matris[i][j] + " ");
            }
            System.out.println();
        }
    }
    
    /**
     * Verilen kelimeyi matriste arayan metot
     * Tüm yönlerde arama yapar ve bulunan sonuçları döndürür
     */
    public static List<BulunanKelime> kelimeyiBul(char[][] matris, String kelime) {
        List<BulunanKelime> sonuclar = new ArrayList<>();
        
        // Tüm başlangıç noktalarını kontrol et
        for (int i = 0; i < matris.length; i++) {
            for (int j = 0; j < matris[i].length; j++) {
                // Eğer ilk harf eşleşiyorsa, tüm yönlerde kontrol et
                if (Character.toLowerCase(matris[i][j]) == kelime.charAt(0)) {
                    // Yatay sağa
                    if (j + kelime.length() - 1 < matris[i].length) {
                        if (kelimeyiKontrolEt(matris, i, j, 0, 1, kelime)) {
                            sonuclar.add(new BulunanKelime(i, j, "Yatay sağa"));
                        }
                    }
                    
                    // Yatay sola
                    if (j - kelime.length() + 1 >= 0) {
                        if (kelimeyiKontrolEt(matris, i, j, 0, -1, kelime)) {
                            sonuclar.add(new BulunanKelime(i, j, "Yatay sola"));
                        }
                    }
                    
                    // Dikey aşağı
                    if (i + kelime.length() - 1 < matris.length) {
                        if (kelimeyiKontrolEt(matris, i, j, 1, 0, kelime)) {
                            sonuclar.add(new BulunanKelime(i, j, "Dikey aşağı"));
                        }
                    }
                    
                    // Dikey yukarı
                    if (i - kelime.length() + 1 >= 0) {
                        if (kelimeyiKontrolEt(matris, i, j, -1, 0, kelime)) {
                            sonuclar.add(new BulunanKelime(i, j, "Dikey yukarı"));
                        }
                    }
                    
                    // Çapraz sağ aşağı
                    if (i + kelime.length() - 1 < matris.length && j + kelime.length() - 1 < matris[i].length) {
                        if (kelimeyiKontrolEt(matris, i, j, 1, 1, kelime)) {
                            sonuclar.add(new BulunanKelime(i, j, "Çapraz sağ aşağı"));
                        }
                    }
                    
                    // Çapraz sağ yukarı
                    if (i - kelime.length() + 1 >= 0 && j + kelime.length() - 1 < matris[i].length) {
                        if (kelimeyiKontrolEt(matris, i, j, -1, 1, kelime)) {
                            sonuclar.add(new BulunanKelime(i, j, "Çapraz sağ yukarı"));
                        }
                    }
                    
                    // Çapraz sol aşağı
                    if (i + kelime.length() - 1 < matris.length && j - kelime.length() + 1 >= 0) {
                        if (kelimeyiKontrolEt(matris, i, j, 1, -1, kelime)) {
                            sonuclar.add(new BulunanKelime(i, j, "Çapraz sol aşağı"));
                        }
                    }
                    
                    // Çapraz sol yukarı
                    if (i - kelime.length() + 1 >= 0 && j - kelime.length() + 1 >= 0) {
                        if (kelimeyiKontrolEt(matris, i, j, -1, -1, kelime)) {
                            sonuclar.add(new BulunanKelime(i, j, "Çapraz sol yukarı"));
                        }
                    }
                }
            }
        }
        
        return sonuclar;
    }
    
    /**
     * Belirli bir yönde kelimeyi kontrol eden yardımcı metot
     */
    private static boolean kelimeyiKontrolEt(char[][] matris, int baslangicSatir, int baslangicSutun, 
                                            int satirYonu, int sutunYonu, String kelime) {
        for (int k = 0; k < kelime.length(); k++) {
            int satir = baslangicSatir + k * satirYonu;
            int sutun = baslangicSutun + k * sutunYonu;
            
            if (Character.toLowerCase(matris[satir][sutun]) != kelime.charAt(k)) {
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * Bulunan kelime bilgilerini tutan iç sınıf
     */
    private static class BulunanKelime {
        int baslangicSatir;
        int baslangicSutun;
        String yon;
        
        BulunanKelime(int baslangicSatir, int baslangicSutun, String yon) {
            this.baslangicSatir = baslangicSatir;
            this.baslangicSutun = baslangicSutun;
            this.yon = yon;
        }
    }
} 