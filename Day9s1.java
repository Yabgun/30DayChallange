import java.io.*;
import java.util.*;

public class Day10s1 {

    public static void main(String[] args) {
        try {
            // Standart girişten tamsayı oku
            Scanner scanner = new Scanner(System.in);
            int n = scanner.nextInt();
            scanner.close();
            
            // Tamsayıyı String'e dönüştür
            String s = Integer.toString(n);
            
            // Dönüşüm başarılı mı kontrol et
            // Eğer s boş değilse ve sayıya geri dönüştürülebiliyorsa başarılı
            if (s != null && Integer.parseInt(s) == n) {
                System.out.println("Good job");
            } else {
                System.out.println("Wrong answer");
            }
        } catch (Exception e) {
            System.out.println("Wrong answer");
        }
    }
} 