import java.io.*;
import java.util.*;

public class Day6s1 {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        
        try {
            // İlk satırdan test sayısını oku
            int T = Integer.parseInt(scan.nextLine().trim());
            
            // Her test durumu için işlem yap
            for (int i = 0; i < T; i++) {
                String input = scan.nextLine().trim();
                
                try {
                    // Girişi long türüne çevirmeyi dene
                    long number = Long.parseLong(input);
                    
                    // Sayının hangi veri tiplerine sığabileceğini kontrol et
                    System.out.println(number + " can be fitted in:");
                    
                    // Byte kontrolü (en küçük)
                    if (number >= Byte.MIN_VALUE && number <= Byte.MAX_VALUE) {
                        System.out.println("* byte");
                    }
                    
                    // Short kontrolü (ikinci en küçük)
                    if (number >= Short.MIN_VALUE && number <= Short.MAX_VALUE) {
                        System.out.println("* short");
                    }
                    
                    // Integer kontrolü (üçüncü en küçük)
                    if (number >= Integer.MIN_VALUE && number <= Integer.MAX_VALUE) {
                        System.out.println("* int");
                    }
                    
                    // Long kontrolü (en büyük)
                    System.out.println("* long");
                    
                } catch (NumberFormatException e) {
                    // Eğer sayı long veri türüne bile sığmıyorsa
                    System.out.println(input + " can't be fitted anywhere.");
                }
            }
        } finally {
            scan.close();
        }
    }
} 