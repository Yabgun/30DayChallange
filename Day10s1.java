import java.io.*;
import java.util.*;

public class Day10s1 {

    public static void main(String[] args) {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT. Your class should be named Solution. */
        Scanner scanner = new Scanner(System.in);
        String A = scanner.nextLine();
        String B = scanner.nextLine();
        scanner.close();
        
        // İlk satır: A ve B'nin uzunlukları toplamı
        System.out.println(A.length() + B.length());
        
        // İkinci satır: A, B'den leksikografik olarak büyükse "Yes", değilse "No"
        if (A.compareTo(B) > 0) {
            System.out.println("Yes");
        } else {
            System.out.println("No");
        }
        
        // Üçüncü satır: Her iki stringin ilk harflerini büyük yapıp, boşlukla ayırarak yazdır
        String capitalizedA = A.substring(0, 1).toUpperCase() + A.substring(1);
        String capitalizedB = B.substring(0, 1).toUpperCase() + B.substring(1);
        System.out.println(capitalizedA + " " + capitalizedB);
    }
} 