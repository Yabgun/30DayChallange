import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Day53s1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("=== Siber Güvenlik Port Tarayıcı ===");
        System.out.print("Taranacak IP adresini girin: ");
        String ipAddress = scanner.nextLine();
        
        System.out.print("Başlangıç portu: ");
        int startPort = scanner.nextInt();
        
        System.out.print("Bitiş portu: ");
        int endPort = scanner.nextInt();
        
        System.out.println("\nPort taraması başlıyor...");
        System.out.println("IP: " + ipAddress);
        System.out.println("Port aralığı: " + startPort + " - " + endPort);
        System.out.println("----------------------------------------");
        
        for (int port = startPort; port <= endPort; port++) {
            try {
                Socket socket = new Socket();
                socket.connect(new InetSocketAddress(ipAddress, port), 1000);
                System.out.println("Port " + port + " AÇIK");
                
                // Port servisini belirlemeye çalış
                try {
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    
                    // Banner grabbing
                    String banner = in.readLine();
                    if (banner != null) {
                        System.out.println("Servis bilgisi: " + banner);
                    }
                    
                    socket.close();
                } catch (Exception e) {
                    System.out.println("Servis bilgisi alınamadı");
                }
                
            } catch (Exception e) {
                // Port kapalı
            }
        }
        
        System.out.println("\nTarama tamamlandı!");
        System.out.println("Güvenlik Önerileri:");
        System.out.println("1. Sadece gerekli portları açık tutun");
        System.out.println("2. Güvenlik duvarı kullanın");
        System.out.println("3. Düzenli güvenlik güncellemeleri yapın");
        System.out.println("4. Güçlü şifreleme kullanın");
        
        scanner.close();
    }
} 