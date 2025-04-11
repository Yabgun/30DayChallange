import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class Day29s1 {
    public static void main(String[] args) {
        WordPuzzle game = new WordPuzzle();
        game.play();
    }
}

class WordPuzzle {
    private static final String[] TURKISH_WORDS = {
        "KALEM", "KITAP", "DEFTER", "SILGI", "CETVEL",
        "OKUL", "OGRENCI", "OGRETMEN", "SINIF", "DERS",
        "BILGISAYAR", "TELEFON", "TABLET", "INTERNET", "YAZICI",
        "MASKE", "DEZENFEKTAN", "SABUN", "SU", "HAVLU",
        "ARABA", "OTOBUS", "TREN", "UCAK", "GEMI",
        "EV", "APARTMAN", "SITE", "BAHCE", "BALCON",
        "KEDI", "KOPEK", "KUS", "BALIK", "KAPLUMBAGA",
        "ELMA", "ARMUT", "MUZ", "PORTAKAL", "MANDALINA",
        "KAHVE", "CAY", "SU", "MEYVE", "SUT",
        "PIZZA", "HAMBURGER", "DONER", "LAHMACUN", "KEBAP"
    };

    private char[] letters;
    private List<String> foundWords;
    private int score;
    private int level;
    private Random random;
    private Scanner scanner;

    public WordPuzzle() {
        this.random = new Random();
        this.scanner = new Scanner(System.in);
        this.foundWords = new ArrayList<>();
        this.score = 0;
        this.level = 1;
        initializeGame();
    }

    private void initializeGame() {
        // Seviyeye göre harf sayısını belirle
        int letterCount = 7 + level;
        letters = new char[letterCount];
        
        // Harfleri rastgele seç
        for (int i = 0; i < letterCount; i++) {
            letters[i] = (char) ('A' + random.nextInt(26));
        }
        
        // En az bir sesli harf ekle
        char[] vowels = {'A', 'E', 'I', 'İ', 'O', 'Ö', 'U', 'Ü'};
        letters[random.nextInt(letterCount)] = vowels[random.nextInt(vowels.length)];
        
        foundWords.clear();
    }

    public void play() {
        System.out.println("Kelime Bulmacaya Hoş Geldiniz! - Seviye " + level);
        System.out.println("Verilen harflerden anlamlı kelimeler oluşturun.");
        System.out.println("Her kelime için puan kazanırsınız.");
        System.out.println("Kontroller: K (Kelime gir), S (Skoru göster), Q (Çıkış)");
        
        boolean continuePlaying = true;
        
        while (continuePlaying) {
            displayLetters();
            System.out.println("Skor: " + score + " | Bulunan kelimeler: " + foundWords.size());
            System.out.print("Seçim (K/S/Q): ");
            
            char choice = scanner.next().toUpperCase().charAt(0);
            
            switch (choice) {
                case 'Q':
                    System.out.println("Oyundan çıkılıyor...");
                    continuePlaying = false;
                    break;
                case 'S':
                    displayScore();
                    break;
                case 'K':
                    checkWord();
                    break;
                default:
                    System.out.println("Geçersiz seçim!");
            }
            
            if (checkLevelUp()) {
                level++;
                System.out.println("\nTEBRİKLER! Seviye " + level + "'e yükseldiniz!");
                initializeGame();
            }
        }
        
        System.out.println("\nOyun bitti! Final skoru: " + score);
        System.out.println("Ulaşılan seviye: " + level);
    }

    private void displayLetters() {
        System.out.println("\nMevcut harfler:");
        for (char letter : letters) {
            System.out.print(letter + " ");
        }
        System.out.println();
    }

    private void checkWord() {
        System.out.print("Kelimeyi girin: ");
        String word = scanner.next().toUpperCase();
        
        // Kelime kontrolü
        if (word.length() < 3) {
            System.out.println("Kelime en az 3 harf olmalıdır!");
            return;
        }
        
        if (foundWords.contains(word)) {
            System.out.println("Bu kelimeyi zaten buldunuz!");
            return;
        }
        
        // Harflerin kullanılabilirliğini kontrol et
        char[] tempLetters = Arrays.copyOf(letters, letters.length);
        boolean canFormWord = true;
        
        for (char c : word.toCharArray()) {
            boolean found = false;
            for (int i = 0; i < tempLetters.length; i++) {
                if (tempLetters[i] == c) {
                    tempLetters[i] = ' '; // Kullanılan harfi işaretle
                    found = true;
                    break;
                }
            }
            if (!found) {
                canFormWord = false;
                break;
            }
        }
        
        if (!canFormWord) {
            System.out.println("Bu kelimeyi oluşturamazsınız!");
            return;
        }
        
        // Kelime listesinde var mı kontrol et
        boolean isValidWord = false;
        for (String validWord : TURKISH_WORDS) {
            if (validWord.equals(word)) {
                isValidWord = true;
                break;
            }
        }
        
        if (isValidWord) {
            foundWords.add(word);
            int wordScore = calculateScore(word);
            score += wordScore;
            System.out.println("Tebrikler! " + wordScore + " puan kazandınız!");
        } else {
            System.out.println("Bu kelime geçerli değil!");
        }
    }

    private int calculateScore(String word) {
        int baseScore = word.length() * 10;
        int bonus = 0;
        
        // Uzun kelimeler için bonus
        if (word.length() >= 6) {
            bonus += 20;
        }
        
        // Nadir harfler için bonus
        for (char c : word.toCharArray()) {
            if ("ÇĞİÖŞÜ".indexOf(c) != -1) {
                bonus += 5;
            }
        }
        
        return baseScore + bonus;
    }

    private boolean checkLevelUp() {
        // Her seviye için gerekli minimum puan
        int requiredScore = level * 100;
        return score >= requiredScore;
    }

    private void displayScore() {
        System.out.println("\nBulunan kelimeler:");
        for (String word : foundWords) {
            System.out.println(word + " (" + calculateScore(word) + " puan)");
        }
        System.out.println("Toplam skor: " + score);
        System.out.println("Bir sonraki seviye için gereken puan: " + (level * 100));
    }
} 