import java.io.*;
import java.util.*;

public class Day2s2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int intValue = scanner.nextInt();
        double doubleValue = scanner.nextDouble();
        scanner.nextLine();
        String stringValue = scanner.nextLine();

        System.out.println("String: " + stringValue);
        System.out.println("Double: " + doubleValue);
        System.out.println("Int: " + intValue);

    scanner.close();
    }
}