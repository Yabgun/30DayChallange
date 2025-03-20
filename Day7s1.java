import java.io.*;
import java.util.*;

public class Day7s1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        try {
            int lineNumber = 1;
            
            // Scanner.hasNext() metodu EOF'a ulaşana kadar devam eder
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                
                // Satır numarası, bir boşluk ve satır içeriğini yazdır
                System.out.println(lineNumber + " " + line);
                
                lineNumber++;
            }
        } finally {
            scanner.close();
        }
    }
} 