import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Day41s1 {
    public static void main(String[] args) {
        MemoryGame game = new MemoryGame();
        game.start();
    }
}

class MemoryGame {
    private List<String> wordBank;
    private List<String> currentGameWords;
    private Scanner scanner;
    private Random random;
    private int level;
    private int score;
    private int lives;

    public MemoryGame() {
        this.wordBank = new ArrayList<>();
        this.currentGameWords = new ArrayList<>();
        this.scanner = new Scanner(System.in);
        this.random = new Random();
        this.level = 1;
        this.score = 0;
        this.lives = 3;
        
        // Kelime bankası oluştur
        initializeWordBank();
    }

    private void initializeWordBank() {
        // Türkçe kelimeler ekleniyor
        wordBank.add("kalem");
        wordBank.add("kitap");
        wordBank.add("bilgisayar");
        wordBank.add("telefon");
        wordBank.add("araba");
        wordBank.add("bisiklet");
        wordBank.add("çikolata");
        wordBank.add("defter");
        wordBank.add("pencere");
        wordBank.add("kapı");
        wordBank.add("masa");
        wordBank.add("sandalye");
        wordBank.add("güneş");
        wordBank.add("yıldız");
        wordBank.add("gökyüzü");
        wordBank.add("bulut");
        wordBank.add("yağmur");
        wordBank.add("kar");
        wordBank.add("deniz");
        wordBank.add("okyanus");
    }

    public void start() {
        System.out.println("=== KELIME HAFIZA OYUNUNA HOŞ GELDINIZ ===");
        System.out.println("Bu oyunda, ekranda gösterilen kelimeleri doğru sırayla hatırlamanız gerekiyor.");
        System.out.println("Her seviye için %d can hakkınız var.".formatted(lives));
        
        boolean playing = true;
        while (playing) {
            System.out.println("\n=== SEVIYE %d ===".formatted(level));
            System.out.println("Puanınız: " + score);
            System.out.println("Kalan can: " + lives);
            
            playLevel();
            
            if (lives <= 0) {
                System.out.println("\nOyun bitti! Toplam puanınız: " + score);
                playing = false;
            } else {
                System.out.print("\nDevam etmek istiyor musunuz? (E/H): ");
                String answer = scanner.nextLine().trim().toUpperCase();
                playing = answer.equals("E");
                if (!playing) {
                    System.out.println("\nOyun sonlandı. Toplam puanınız: " + score);
                }
            }
        }
    }
    
    private void playLevel() {
        int wordsToRemember = level + 1; // Seviye arttıkça hatırlanması gereken kelime sayısı artıyor
        
        // Kelime listesi oluştur
        selectRandomWords(wordsToRemember);
        
        // Kelimeleri göster
        showWords();
        
        // Kullanıcıdan kelimeleri hatırlamasını iste
        if (checkUserMemory()) {
            System.out.println("\nTebrikler! Bütün kelimeleri doğru hatırladınız.");
            score += level * 10; // Seviye başına 10 puan
            level++; // Bir sonraki seviyeye geç
        } else {
            System.out.println("\nÜzgünüm, kelimeleri yanlış hatırladınız.");
            lives--; // Can azalt
        }
    }
    
    private void selectRandomWords(int count) {
        currentGameWords.clear();
        
        // Kelime bankasından rastgele kelimeler seç
        List<String> tempBank = new ArrayList<>(wordBank);
        
        for (int i = 0; i < count && !tempBank.isEmpty(); i++) {
            int randomIndex = random.nextInt(tempBank.size());
            currentGameWords.add(tempBank.remove(randomIndex));
        }
    }
    
    private void showWords() {
        System.out.println("\nAşağıdaki kelimeleri sırasıyla hatırlayın:");
        
        try {
            for (String word : currentGameWords) {
                System.out.println(word);
                TimeUnit.SECONDS.sleep(2); // Her kelime 2 saniye gösterilir
                clearScreen();
            }
        } catch (InterruptedException e) {
            System.out.println("Oyun durduruldu.");
        }
        
        System.out.println("\nŞimdi kelimeleri hatırlama zamanı!");
    }
    
    private boolean checkUserMemory() {
        System.out.println("Gördüğünüz kelimeleri sırasıyla yazın:");
        
        for (int i = 0; i < currentGameWords.size(); i++) {
            System.out.print((i+1) + ". kelime: ");
            String userWord = scanner.nextLine().trim().toLowerCase();
            
            if (!userWord.equals(currentGameWords.get(i))) {
                System.out.println("Yanlış! Doğru kelime '" + currentGameWords.get(i) + "' olmalıydı.");
                return false;
            }
        }
        
        return true;
    }
    
    private void clearScreen() {
        // Konsolu temizlemek için 50 boş satır yazdır
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }
} 