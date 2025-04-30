import java.net.*;
import java.util.Scanner;

public class Day47s1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Port Tarayıcı - Siber Güvenlik Aracı");
        System.out.println("-----------------------------------");
        
        System.out.print("Hedef IP adresini girin: ");
        String ipAddress = scanner.nextLine();
        
        System.out.print("Başlangıç port numarası: ");
        int startPort = scanner.nextInt();
        
        System.out.print("Bitiş port numarası: ");
        int endPort = scanner.nextInt();
        
        System.out.println("\nPort taraması başlatılıyor...");
        System.out.println("Hedef: " + ipAddress);
        System.out.println("Port aralığı: " + startPort + " - " + endPort);
        System.out.println("-----------------------------------");
        
        for (int port = startPort; port <= endPort; port++) {
            try {
                Socket socket = new Socket();
                socket.connect(new InetSocketAddress(ipAddress, port), 200);
                System.out.println("Port " + port + " açık!");
                socket.close();
            } catch (Exception e) {
                // Port kapalı veya erişilemez
            }
        }
        
        System.out.println("\nTarama tamamlandı!");
        scanner.close();
    }
} 