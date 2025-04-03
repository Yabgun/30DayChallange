import java.util.*;

/**
 * En Uzun Palindromik Alt Dizi Problemi
 * Bu program, verilen bir string içindeki en uzun palindromik alt diziyi bulur.
 * Palindrom: Baştan ve sondan okunduğunda aynı olan kelime veya dizilere denir.
 * Örnek: "kayak", "ey edip adanada pide ye"
 */
public class Day21s1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("*** EN UZUN PALİNDROMİK ALT DİZİ BULMA ***");
        System.out.println("Bir metin girin:");
        String metin = scanner.nextLine();
        
        // En uzun palindromik alt diziyi bul
        String enUzunPalindrom = enUzunPalindromikAltDizi(metin);
        
        // Sonuçları göster
        System.out.println("\nSonuçlar:");
        System.out.println("Girilen metin: " + metin);
        System.out.println("En uzun palindromik alt dizi: " + enUzunPalindrom);
        System.out.println("Uzunluk: " + enUzunPalindrom.length());
        
        // Tüm palindromik alt dizileri bul ve göster
        List<String> tumPalindromlar = tumPalindromikAltDizileriBul(metin);
        System.out.println("\nTüm palindromik alt diziler (" + tumPalindromlar.size() + " adet):");
        for (String palindrom : tumPalindromlar) {
            System.out.println("- " + palindrom + " (Uzunluk: " + palindrom.length() + ")");
        }
        
        scanner.close();
    }
    
    /**
     * En uzun palindromik alt diziyi bulan metot
     * Dinamik programlama yaklaşımı kullanır
     * Zaman karmaşıklığı: O(n²)
     * Alan karmaşıklığı: O(n²)
     */
    public static String enUzunPalindromikAltDizi(String s) {
        if (s == null || s.length() < 2) {
            return s;
        }
        
        int n = s.length();
        boolean[][] dp = new boolean[n][n];
        
        // Tek karakterli palindromlar
        for (int i = 0; i < n; i++) {
            dp[i][i] = true;
        }
        
        // İki karakterli palindromlar
        for (int i = 0; i < n - 1; i++) {
            if (s.charAt(i) == s.charAt(i + 1)) {
                dp[i][i + 1] = true;
            }
        }
        
        // Üç veya daha fazla karakterli palindromlar
        for (int len = 3; len <= n; len++) {
            for (int i = 0; i < n - len + 1; i++) {
                int j = i + len - 1;
                if (s.charAt(i) == s.charAt(j) && dp[i + 1][j - 1]) {
                    dp[i][j] = true;
                }
            }
        }
        
        // En uzun palindromu bul
        int maxLen = 0;
        int start = 0;
        
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (dp[i][j] && j - i + 1 > maxLen) {
                    maxLen = j - i + 1;
                    start = i;
                }
            }
        }
        
        return s.substring(start, start + maxLen);
    }
    
    /**
     * Tüm palindromik alt dizileri bulan metot
     * Merkez genişletme yaklaşımı kullanır
     * Zaman karmaşıklığı: O(n²)
     */
    public static List<String> tumPalindromikAltDizileriBul(String s) {
        List<String> palindromlar = new ArrayList<>();
        
        if (s == null || s.length() == 0) {
            return palindromlar;
        }
        
        // Tek karakterli palindromlar
        for (int i = 0; i < s.length(); i++) {
            palindromlar.add(s.substring(i, i + 1));
        }
        
        // Merkez genişletme yaklaşımı
        for (int i = 0; i < s.length() - 1; i++) {
            // Tek sayılı uzunluklu palindromlar
            genislet(s, i, i, palindromlar);
            
            // Çift sayılı uzunluklu palindromlar
            genislet(s, i, i + 1, palindromlar);
        }
        
        // Palindromları uzunluklarına göre sırala (büyükten küçüğe)
        palindromlar.sort((a, b) -> b.length() - a.length());
        
        return palindromlar;
    }
    
    /**
     * Merkez noktadan başlayarak palindromları genişleten yardımcı metot
     */
    private static void genislet(String s, int sol, int sag, List<String> palindromlar) {
        while (sol >= 0 && sag < s.length() && s.charAt(sol) == s.charAt(sag)) {
            palindromlar.add(s.substring(sol, sag + 1));
            sol--;
            sag++;
        }
    }
    
    /**
     * Alternatif çözüm: Manacher Algoritması
     * Zaman karmaşıklığı: O(n)
     * Alan karmaşıklığı: O(n)
     * Not: Bu metot daha karmaşık olduğu için ana çözümde kullanılmadı
     */
    public static String manacherAlgoritmasi(String s) {
        if (s == null || s.length() < 2) {
            return s;
        }
        
        // String'i özel karakterlerle genişlet (#a#b#a#)
        StringBuilder t = new StringBuilder("#");
        for (char c : s.toCharArray()) {
            t.append(c).append("#");
        }
        
        int n = t.length();
        int[] p = new int[n]; // p[i] = merkezi i olan en uzun palindromun yarıçapı
        int merkez = 0; // Şu ana kadar bulunan en uzun palindromun merkezi
        int sag = 0; // Şu ana kadar bulunan en uzun palindromun sağ sınırı
        
        for (int i = 0; i < n; i++) {
            if (i < sag) {
                // Mirror özelliğini kullan
                p[i] = Math.min(sag - i, p[2 * merkez - i]);
            }
            
            // Merkezi i olan palindromu genişlet
            int sol = i - (p[i] + 1);
            int yeniSag = i + (p[i] + 1);
            
            while (sol >= 0 && yeniSag < n && t.charAt(sol) == t.charAt(yeniSag)) {
                p[i]++;
                sol--;
                yeniSag++;
            }
            
            // En uzun palindromu güncelle
            if (i + p[i] > sag) {
                merkez = i;
                sag = i + p[i];
            }
        }
        
        // En uzun palindromu bul
        int maxLen = 0;
        int maxMerkez = 0;
        
        for (int i = 0; i < n; i++) {
            if (p[i] > maxLen) {
                maxLen = p[i];
                maxMerkez = i;
            }
        }
        
        // Orijinal string'deki indekslere dönüştür
        int baslangic = (maxMerkez - maxLen) / 2;
        int uzunluk = maxLen;
        
        return s.substring(baslangic, baslangic + uzunluk);
    }
} 