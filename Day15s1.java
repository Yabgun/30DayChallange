import java.util.Scanner;

/**
 * Basit Hesap Makinesi
 * Kullanıcıdan iki sayı ve bir işlem operatörü alarak temel matematik işlemlerini gerçekleştiren program.
 */
public class Day15s1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("*** BASİT HESAP MAKİNESİ ***");
        
        // İlk sayıyı al
        System.out.print("Birinci sayıyı girin: ");
        double sayi1 = scanner.nextDouble();
        
        // İkinci sayıyı al
        System.out.print("İkinci sayıyı girin: ");
        double sayi2 = scanner.nextDouble();
        
        // İşlem operatörünü al
        System.out.print("İşlem operatörünü girin (+, -, *, /): ");
        char operator = scanner.next().charAt(0);
        
        double sonuc = 0;
        
        // İşlemi gerçekleştir
        switch (operator) {
            case '+':
                sonuc = sayi1 + sayi2;
                break;
            case '-':
                sonuc = sayi1 - sayi2;
                break;
            case '*':
                sonuc = sayi1 * sayi2;
                break;
            case '/':
                if (sayi2 != 0) {
                    sonuc = sayi1 / sayi2;
                } else {
                    System.out.println("Hata: Sıfıra bölme hatası!");
                    scanner.close();
                    return;
                }
                break;
            default:
                System.out.println("Geçersiz operatör!");
                scanner.close();
                return;
        }
        
        // Sonucu göster
        System.out.printf("%.2f %c %.2f = %.2f%n", sayi1, operator, sayi2, sonuc);
        
        scanner.close();
    }
} 