import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;

public class Day49s1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Lütfen bir metin giriniz:");
        String metin = scanner.nextLine();
        
        // Metni kelimelere ayır
        String[] kelimeler = metin.split("\\s+");
        
        // Kelime sayılarını tutacak HashMap
        Map<String, Integer> kelimeSayilari = new HashMap<>();
        
        // Her kelimeyi say
        for (String kelime : kelimeler) {
            // Noktalama işaretlerini kaldır
            kelime = kelime.replaceAll("[^a-zA-ZğüşıöçĞÜŞİÖÇ]", "").toLowerCase();
            
            if (!kelime.isEmpty()) {
                kelimeSayilari.put(kelime, kelimeSayilari.getOrDefault(kelime, 0) + 1);
            }
        }
        
        // Sonuçları yazdır
        System.out.println("\nKelime Sayıları:");
        for (Map.Entry<String, Integer> entry : kelimeSayilari.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue() + " kez");
        }
        
        scanner.close();
    }
} 