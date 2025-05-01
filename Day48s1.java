import java.util.Scanner;
import java.util.regex.*;

public class Day48s1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Şifre Güvenlik Kontrolü");
        System.out.println("----------------------");
        
        System.out.print("Kontrol etmek istediğiniz şifreyi girin: ");
        String password = scanner.nextLine();
        
        int gucPuani = 0;
        StringBuilder oneriler = new StringBuilder();
        
        // Uzunluk kontrolü
        if (password.length() >= 8) {
            gucPuani += 2;
        } else {
            oneriler.append("- Şifreniz en az 8 karakter uzunluğunda olmalıdır.\n");
        }
        
        // Büyük harf kontrolü
        if (Pattern.compile("[A-Z]").matcher(password).find()) {
            gucPuani += 2;
        } else {
            oneriler.append("- Şifrenizde en az bir büyük harf bulunmalıdır.\n");
        }
        
        // Küçük harf kontrolü
        if (Pattern.compile("[a-z]").matcher(password).find()) {
            gucPuani += 2;
        } else {
            oneriler.append("- Şifrenizde en az bir küçük harf bulunmalıdır.\n");
        }
        
        // Rakam kontrolü
        if (Pattern.compile("[0-9]").matcher(password).find()) {
            gucPuani += 2;
        } else {
            oneriler.append("- Şifrenizde en az bir rakam bulunmalıdır.\n");
        }
        
        // Özel karakter kontrolü
        if (Pattern.compile("[!@#$%^&*(),.?\":{}|<>]").matcher(password).find()) {
            gucPuani += 2;
        } else {
            oneriler.append("- Şifrenizde en az bir özel karakter bulunmalıdır.\n");
        }
        
        // Sonuçları göster
        System.out.println("\nŞifre Güvenlik Analizi");
        System.out.println("----------------------");
        System.out.println("Güç Puanı: " + gucPuani + "/10");
        
        if (gucPuani >= 8) {
            System.out.println("Durum: Güçlü şifre");
        } else if (gucPuani >= 5) {
            System.out.println("Durum: Orta seviye şifre");
        } else {
            System.out.println("Durum: Zayıf şifre");
        }
        
        if (oneriler.length() > 0) {
            System.out.println("\nGüvenlik Önerileri:");
            System.out.println(oneriler.toString());
        }
        
        scanner.close();
    }
} 