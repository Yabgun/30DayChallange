import java.util.Scanner;
import java.util.Random;
import java.util.regex.Pattern;

public class Day54s1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        while (true) {
            System.out.println("\n=== Siber Güvenlik Şifre Yöneticisi ===");
            System.out.println("1. Şifre Güvenlik Kontrolü");
            System.out.println("2. Güçlü Şifre Oluşturucu");
            System.out.println("3. Çıkış");
            System.out.print("Seçiminiz (1-3): ");
            
            int secim = scanner.nextInt();
            scanner.nextLine(); // Buffer temizleme
            
            switch (secim) {
                case 1:
                    sifreKontrol(scanner);
                    break;
                case 2:
                    sifreOlusturucu(scanner);
                    break;
                case 3:
                    System.out.println("Program sonlandırılıyor...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Geçersiz seçim!");
            }
        }
    }
    
    private static void sifreKontrol(Scanner scanner) {
        System.out.print("\nKontrol edilecek şifreyi girin: ");
        String sifre = scanner.nextLine();
        
        int puan = 0;
        StringBuilder oneriler = new StringBuilder();
        
        // Uzunluk kontrolü
        if (sifre.length() >= 8) {
            puan += 2;
        } else {
            oneriler.append("- Şifreniz en az 8 karakter olmalı\n");
        }
        
        // Büyük harf kontrolü
        if (Pattern.compile("[A-Z]").matcher(sifre).find()) {
            puan += 2;
        } else {
            oneriler.append("- En az bir büyük harf kullanın\n");
        }
        
        // Küçük harf kontrolü
        if (Pattern.compile("[a-z]").matcher(sifre).find()) {
            puan += 2;
        } else {
            oneriler.append("- En az bir küçük harf kullanın\n");
        }
        
        // Rakam kontrolü
        if (Pattern.compile("[0-9]").matcher(sifre).find()) {
            puan += 2;
        } else {
            oneriler.append("- En az bir rakam kullanın\n");
        }
        
        // Özel karakter kontrolü
        if (Pattern.compile("[!@#$%^&*(),.?\":{}|<>]").matcher(sifre).find()) {
            puan += 2;
        } else {
            oneriler.append("- En az bir özel karakter kullanın\n");
        }
        
        System.out.println("\nŞifre Güvenlik Analizi:");
        System.out.println("Güvenlik Puanı: " + puan + "/10");
        
        if (puan < 8) {
            System.out.println("\nGüvenlik Önerileri:");
            System.out.println(oneriler.toString());
        } else {
            System.out.println("Şifreniz güçlü görünüyor!");
        }
    }
    
    private static void sifreOlusturucu(Scanner scanner) {
        System.out.print("Şifre uzunluğunu girin (en az 8): ");
        int uzunluk = scanner.nextInt();
        
        if (uzunluk < 8) {
            System.out.println("Uzunluk en az 8 olmalıdır!");
            return;
        }
        
        String buyukHarfler = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String kucukHarfler = "abcdefghijklmnopqrstuvwxyz";
        String rakamlar = "0123456789";
        String ozelKarakterler = "!@#$%^&*(),.?\":{}|<>";
        
        StringBuilder sifre = new StringBuilder();
        Random random = new Random();
        
        // Her kategoriden en az bir karakter
        sifre.append(buyukHarfler.charAt(random.nextInt(buyukHarfler.length())));
        sifre.append(kucukHarfler.charAt(random.nextInt(kucukHarfler.length())));
        sifre.append(rakamlar.charAt(random.nextInt(rakamlar.length())));
        sifre.append(ozelKarakterler.charAt(random.nextInt(ozelKarakterler.length())));
        
        // Kalan karakterleri rastgele doldur
        String tumKarakterler = buyukHarfler + kucukHarfler + rakamlar + ozelKarakterler;
        for (int i = 4; i < uzunluk; i++) {
            sifre.append(tumKarakterler.charAt(random.nextInt(tumKarakterler.length())));
        }
        
        // Karakterleri karıştır
        char[] sifreArray = sifre.toString().toCharArray();
        for (int i = sifreArray.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            char temp = sifreArray[i];
            sifreArray[i] = sifreArray[j];
            sifreArray[j] = temp;
        }
        
        System.out.println("\nOluşturulan Güçlü Şifre: " + new String(sifreArray));
    }
} 