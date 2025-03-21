import java.io.*;
import java.util.*;

public class Day8s1 {
    // Paralelkenarın boyutlarını static değişkenler olarak tanımla
    static int B;
    static int H;
    // Girdinin geçerli olup olmadığını tutan static boolean değişkeni
    static boolean flag = true;
    
    // Static initialization block
    static {
        Scanner scanner = new Scanner(System.in);
        try {
            // Genişlik (B) ve yükseklik (H) değerlerini oku
            B = scanner.nextInt();
            H = scanner.nextInt();
            
            // B veya H sıfır veya negatif ise exception fırlat
            if (B <= 0 || H <= 0) {
                flag = false;
                System.out.println("java.lang.Exception: Breadth and height must be positive");
            }
        } catch (Exception e) {
            System.out.println(e);
            flag = false;
        }
    }

    public static void main(String[] args) {
        // Eğer girdi geçerliyse paralelkenarın alanını hesapla ve yazdır
        if (flag) {
            int area = B * H;
            System.out.println(area);
        }
    }
}