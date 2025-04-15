import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class Day33s1 {
    public static void main(String[] args) {
        CryptogramSolver solver = new CryptogramSolver();
        solver.solve();
    }
}

class CryptogramSolver {
    private static final String TURKISH_ALPHABET = "ABCÇDEFGĞHIİJKLMNOÖPRSŞTUÜVYZ";
    private static final String[] COMMON_WORDS = {
        "VE", "BİR", "BU", "DA", "DE", "İÇİN", "AMA", "VEYA",
        "ANCAK", "ÇÜNKÜ", "EĞER", "İLE", "Kİ", "O", "ON", "SEN",
        "BEN", "BİZ", "SİZ", "ONLAR", "BUNLAR", "ŞUNLAR"
    };
    
    private Map<Character, Character> substitutionMap;
    private Scanner scanner;

    public CryptogramSolver() {
        this.substitutionMap = new HashMap<>();
        this.scanner = new Scanner(System.in);
    }

    public void solve() {
        System.out.println("Kriptogram Çözücüye Hoş Geldiniz!");
        System.out.println("Şifrelenmiş metni girin:");
        String encryptedText = scanner.nextLine().toUpperCase();
        
        System.out.println("\nŞifrelenmiş Metin:");
        System.out.println(encryptedText);
        
        // Harf frekanslarını analiz et
        Map<Character, Integer> frequency = analyzeFrequency(encryptedText);
        
        // En sık kullanılan harfleri bul
        List<Character> frequentLetters = getFrequentLetters(frequency);
        
        // Olası çözümleri dene
        System.out.println("\nOlası Çözümler:");
        trySolutions(encryptedText, frequentLetters);
    }

    private Map<Character, Integer> analyzeFrequency(String text) {
        Map<Character, Integer> frequency = new HashMap<>();
        for (char c : text.toCharArray()) {
            if (TURKISH_ALPHABET.indexOf(c) != -1) {
                frequency.put(c, frequency.getOrDefault(c, 0) + 1);
            }
        }
        return frequency;
    }

    private List<Character> getFrequentLetters(Map<Character, Integer> frequency) {
        List<Character> letters = new ArrayList<>(frequency.keySet());
        Collections.sort(letters, (a, b) -> frequency.get(b) - frequency.get(a));
        return letters;
    }

    private void trySolutions(String encryptedText, List<Character> frequentLetters) {
        // Türkçe'de en sık kullanılan harfler
        String commonLetters = "AEİNRLDKIMTUSOBÜŞYZÇĞHCÖPVG";
        
        // İlk 5 harfi dene
        for (int i = 0; i < Math.min(5, frequentLetters.size()); i++) {
            substitutionMap.clear();
            char encryptedChar = frequentLetters.get(i);
            char possibleChar = commonLetters.charAt(i);
            
            substitutionMap.put(encryptedChar, possibleChar);
            
            // Tek harfli kelimeleri çöz
            solveSingleLetterWords(encryptedText);
            
            // Yaygın kelimeleri dene
            solveCommonWords(encryptedText);
            
            // Kalan harfleri tahmin et
            guessRemainingLetters(encryptedText);
            
            // Çözümü göster
            System.out.println("\nÇözüm " + (i + 1) + ":");
            printSolution(encryptedText);
        }
    }

    private void solveSingleLetterWords(String text) {
        String[] words = text.split(" ");
        for (String word : words) {
            if (word.length() == 1) {
                char c = word.charAt(0);
                if (TURKISH_ALPHABET.indexOf(c) != -1 && !substitutionMap.containsKey(c)) {
                    substitutionMap.put(c, 'V'); // Türkçe'de tek harfli kelime genellikle 'V'
                }
            }
        }
    }

    private void solveCommonWords(String text) {
        String[] words = text.split(" ");
        for (String word : words) {
            for (String common : COMMON_WORDS) {
                if (word.length() == common.length()) {
                    tryMatch(word, common);
                }
            }
        }
    }

    private void tryMatch(String encrypted, String common) {
        Map<Character, Character> tempMap = new HashMap<>(substitutionMap);
        boolean match = true;
        
        for (int i = 0; i < encrypted.length(); i++) {
            char encChar = encrypted.charAt(i);
            char comChar = common.charAt(i);
            
            if (tempMap.containsKey(encChar)) {
                if (tempMap.get(encChar) != comChar) {
                    match = false;
                    break;
                }
            } else {
                tempMap.put(encChar, comChar);
            }
        }
        
        if (match) {
            substitutionMap.putAll(tempMap);
        }
    }

    private void guessRemainingLetters(String text) {
        String remainingLetters = TURKISH_ALPHABET;
        for (char c : substitutionMap.keySet()) {
            remainingLetters = remainingLetters.replace(String.valueOf(substitutionMap.get(c)), "");
        }
        
        for (char c : text.toCharArray()) {
            if (TURKISH_ALPHABET.indexOf(c) != -1 && !substitutionMap.containsKey(c)) {
                if (!remainingLetters.isEmpty()) {
                    substitutionMap.put(c, remainingLetters.charAt(0));
                    remainingLetters = remainingLetters.substring(1);
                }
            }
        }
    }

    private void printSolution(String encryptedText) {
        StringBuilder solution = new StringBuilder();
        for (char c : encryptedText.toCharArray()) {
            if (TURKISH_ALPHABET.indexOf(c) != -1) {
                solution.append(substitutionMap.getOrDefault(c, '?'));
            } else {
                solution.append(c);
            }
        }
        System.out.println(solution.toString());
        
        // Kullanılan harf eşleşmelerini göster
        System.out.println("\nHarf Eşleşmeleri:");
        for (char c : substitutionMap.keySet()) {
            System.out.println(c + " -> " + substitutionMap.get(c));
        }
    }
} 